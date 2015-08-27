package com.linhongzheng.weixin.entity.message;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.linhongzheng.weixin.services.IMessageService;

public class WeChatServiceTestImpl extends TestCase {
 	 
	IMessageService messageService;

	@Test
	public void processRequest() {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FromUserName", "philiphongzheng");
		requestMap.put("ToUserName", "philipLin");
		requestMap.put("Content", "签到");
		System.out.println(messageService.handleTextMessage(requestMap));
	}

}
