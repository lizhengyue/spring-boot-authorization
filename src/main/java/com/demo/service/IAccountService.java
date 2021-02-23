package com.demo.service;

import com.demo.dto.LoginDto;
import com.demo.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author lzy
 * @since 2021-02-10
 */
public interface IAccountService extends IService<Account> {
	LoginDto login(String username, String password);
}
