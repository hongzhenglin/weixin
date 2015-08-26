package com.linhongzheng.weixin.entity.message;

import junit.framework.TestCase;

import org.junit.Test;

import com.linhongzheng.weixin.services.impl.MenuServiceImpl;

public class MenuTest extends TestCase {
	@Test
	public void testCreateMenu() {
		new MenuServiceImpl().creatMenu();
		// {"errcode":48001,"errmsg":"api unauthorized"}
	}

}
