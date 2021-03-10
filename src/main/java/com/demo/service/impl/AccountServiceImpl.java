package com.demo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.MD5;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.dto.LoginDto;
import com.demo.entity.Account;
import com.demo.mapper.AccountMapper;
import com.demo.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2021-02-10
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

	@Override
	public LoginDto login(String username, String password) {
		LoginDto loginDto = new LoginDto();
		loginDto.setPath("redirect:/");
		Account one = lambdaQuery().eq(Account::getUsername, username).one();
		if (ObjectUtil.isEmpty(one)){
				loginDto.setError("用户名错误");
				return loginDto;
		}
		MD5 md5 = new MD5(one.getSalt().getBytes());
		String s = md5.digestHex(password);
		if(!s.equals(one.getPassword())){
			loginDto.setError("密码错误");
			return loginDto;
		}
		loginDto.setPath("login/main");
		loginDto.setAccount(one);
		loginDto.setToken(getToken(one));
		return loginDto;
	}

	@Override
	public String getToken(Account user) {
		String token = "";
		token= JWT.create().withAudience(user.getAccountId().toString())
				.sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}
}
