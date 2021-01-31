package com.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.UserMapper;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private  UserMapper userMapper;
    public  List<User> test(){
            QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("id","1088248166370832385");
            return list(objectQueryWrapper);
        }

        public List<User> getList(){
        String a  ="lzy";
            return  userMapper.selectAll(a);
        }
}
