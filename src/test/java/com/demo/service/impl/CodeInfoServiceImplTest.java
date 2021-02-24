package com.demo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeInfoServiceImplTest {

	@Autowired
	private CodeInfoServiceImpl codeInfoService;
	@Test
	public void nextCodeByConfig() {
		String text001 = codeInfoService.nextCodeByConfig("test001");
		System.out.println(text001);
	}
}