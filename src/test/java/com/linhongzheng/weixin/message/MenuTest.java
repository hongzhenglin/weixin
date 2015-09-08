package com.linhongzheng.weixin.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.services.IMenuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MenuTest extends AbstractJUnit4SpringContextTests {

	@Resource
	IMenuService menuService;

	@Test
	public void testCreateMenu() {
		menuService.creatMenu();
		// {"errcode":48001,"errmsg":"api unauthorized"}
	}

}
