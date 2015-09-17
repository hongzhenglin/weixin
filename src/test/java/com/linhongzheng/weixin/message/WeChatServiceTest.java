package com.linhongzheng.weixin.message;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.IWeChatService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class WeChatServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource
	IMessageService messageService;
	@Resource
	IWeChatService weChatService;

	//@Test
	public void processRequest() {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FromUserName", "philiphongzheng");
		requestMap.put("ToUserName", "philipLin");
		requestMap.put("Content", "签到");
		System.out.println(messageService.handleTextMessage(requestMap));
	}

	@Test
	public void testQueryServerIp() {
		System.out.println("微信服务器IP:"+weChatService.getServerIP(null).toString());
	}
}
