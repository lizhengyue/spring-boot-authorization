package com.demo.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.dao.UserMapper;
import com.demo.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private  UserServiceImpl userServiceImpl;
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test(){
        //集合如果查询的是空 返回的是空数组 []
        List<User> test = userServiceImpl.test();
        if (test.isEmpty()){
            System.out.println("123231231");
        }

        //如果是对象没有查到 返回的是空 null类型
        User one = userServiceImpl.getOne();
        if (ObjectUtil.isEmpty(one)) {
            System.out.println("1111111111");
        }
    }

    @Test
    public void test1(){
        userServiceImpl.getList();
    }


    @Test
    public void deleteById(){
        int i = userMapper.deleteById(1087982257332887553L);
        System.out.println(i);
    }

    @Test
    public void test2(){
        userServiceImpl.list();
    }

    @Test
    public void insert(){
        User u = new User();
        u.setAge(24);
        u.setName("李正悦");
        u.setEmail("123");
        boolean save = userServiceImpl.save(u);
        System.out.println(save);
    }

    @Test
    public void upate(){
        User u = new User();
        u.setAge(24);
        u.setName("李正悦");
        u.setEmail("12345");
     //   String dateStr = "2017-03-01 22:33:23";
      //  Date date = DateUtil.parse(dateStr);
      //  u.setUpdateTime(date);
        u.setId(1355758713885487106L);
        boolean save = userServiceImpl.updateById(u);
        System.out.println(save);
    }

    @Test
    public void getList() {
    }

    @Test
    public void selectUserPage() {
        Page<User> page = new Page<>(1, 3);
        userServiceImpl.selectUserPage(page);
    }







    @Test
    public void getOne(){

        User one = userServiceImpl.getOne();
        if (ObjectUtil.isEmpty(one)) {
            System.out.println("1111111111");
        }
    }


}