package com.linhongzheng.weixin.message;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.services.IBaiduService;
import com.linhongzheng.weixin.services.impl.BaiduServiceImpl;

public class BaiduApiTest extends TestCase {
	IBaiduService baiduService;

	protected void setUp() throws Exception {
		baiduService = new BaiduServiceImpl();
	}

	@Test
	public void testMusic() {

		Music music = baiduService.searchMusic("吻别", "");
		System.out.println("音乐名称：" + music.getTitle());
		System.out.println("音乐描述：" + music.getDescription());
		System.out.println("普通品质链接：" + music.getMusicUrl());
		System.out.println("高品质链接：" + music.getHQMusicUrl());
	}

	@Test
	public void testTranslate() {
		Assert.assertEquals("I have a dream", baiduService.translate("我有一个梦想"));
	}
}
