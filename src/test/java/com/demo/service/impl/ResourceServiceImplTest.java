package com.demo.service.impl;

import com.demo.utils.RedisUtils;
import com.demo.vo.ResourceVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceImplTest {

	@Autowired
	private ResourceServiceImpl resourceService;

	@Autowired(required=false)
	private RedisUtils redisUtil;

	@Test
	public void listResourceByRoleId() {
		List<ResourceVo> resourceVos = resourceService.listResourceByRoleId(1L);
		System.out.println(resourceVos);
	}
	@Test
	public void getLzy(){
		redisUtil.set("lzy1","44444");
	}


	@Test
	public void getLzy1(){
		Object lzy1 = redisUtil.get("lzy1");
		System.out.println(lzy1);
	}
}