package com.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.UserMapper;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


	@Autowired
	private UserMapper userMapper;

	public List<User> test() {
		QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
		objectQueryWrapper.eq("id", "1088248166370832385");
		return list(objectQueryWrapper);
	}

	public List<User> getList() {
		String a = "lzy";
		return userMapper.selectAll(a);
	}


	public IPage<User> selectUserPage(Page<User> page) {
		// 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
		// page.setOptimizeCountSql(false);
		// 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
		// 要点!! 分页返回的对象与传入的对象是同一个
		return userMapper.selectPageVo(page);
	}


	public User getOne(){
        QueryWrapper<User> apper  = new QueryWrapper<>();
        apper.eq("id","43413241234123413");
        return getOne(apper);
    }


}
