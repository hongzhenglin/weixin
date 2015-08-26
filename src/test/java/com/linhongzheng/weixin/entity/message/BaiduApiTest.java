package com.linhongzheng.weixin.entity.message;

import org.junit.Test;

import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.services.impl.BaiduMusicServiceImpl;

public class BaiduApiTest {

	@Test
	public void test() {

		Music music = new BaiduMusicServiceImpl().searchMusic("相信自己", "零点乐队");
		System.out.println("音乐名称：" + music.getTitle());
		System.out.println("音乐描述：" + music.getDescription());
		System.out.println("普通品质链接：" + music.getMusicUrl());
		System.out.println("高品质链接：" + music.getHQMusicUrl());
	}

}
