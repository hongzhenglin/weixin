package com.linhongzheng.weixin.message;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.services.ICustomMessageService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.utils.message.CustomMessageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MessageTest extends AbstractJUnit4SpringContextTests {
	@Resource
	IMessageService messageService;
	@Resource
	ICustomMessageService customMessageService;
	private static final String openId = "o8cS6uEhPb-OVPjNVDCfx1KPw5nE";

	@Test
	public void testSendTemplateMessage() {

		Map requestMap = new HashMap();
		requestMap.put("FromUserName", openId);

		String data = messageService.createTemplateMessage(requestMap);
		messageService.sendTemplateMEssage(data);
	}

	@Test
	public void testCustomMessge() {
		String jsonMsg = CustomMessageUtil.makeTextCustomMessage(openId,
				"客服接口测试");

		customMessageService.sendCustomerMessage(jsonMsg);
	}
}
