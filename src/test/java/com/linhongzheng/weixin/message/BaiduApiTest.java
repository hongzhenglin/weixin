package com.linhongzheng.weixin.message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.services.IBaiduService;
import com.linhongzheng.weixin.services.impl.BaiduServiceImpl;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.URLConstants;

public class BaiduApiTest extends TestCase {
	IBaiduService baiduService;

	protected void setUp() throws Exception {
		baiduService = new BaiduServiceImpl();
	}

	//@Test
	public void testMusic() {

		Music music = baiduService.searchMusic("吻别", "");
		System.out.println("音乐名称：" + music.getTitle());
		System.out.println("音乐描述：" + music.getDescription());
		System.out.println("普通品质链接：" + music.getMusicUrl());
		System.out.println("高品质链接：" + music.getHQMusicUrl());
	}

	//@Test
	public void testTranslate() {
		Assert.assertEquals("I have a dream", baiduService.translate("我有一个梦想"));
	}

	@Test
	public void testGeoConvert() {
		String url = URLConstants.BAIDU.BAIDU_GEO_CONVERT_URL.replace("COORDS",
				"114.21892734521,29.575429778924;114.21892734521,29.575429778924").replace("AK",
				"7vytB9M6PdR4xms3bTfBv7b0");
		try {
			System.out.println("百度地图坐标："+HttpUtil.get(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
