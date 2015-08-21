package com.linhongzheng.weixin.services.impl;

import com.google.inject.Inject;
import com.linhongzheng.weixin.entity.message.request.TextRequestMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.message.MessageUtil;

import java.util.Map;

/**
 * Created by linhz on 2015/8/18.
 */
public class EventMessageServiceImpl implements IEventMessageService {

	@Inject
	IUserService userService;

	@Override
	public String handleSubscribeEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = new TextResponseMessage();
		String respMessage = "谢谢您的关注！";
		textMessage.setContent(respMessage);
		userService.saveWeiXinUser(requestMap.get("FromUserName"));
		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleUbsubscribeEvent(Map<String, String> requestMap) {
		return null;

	}

	@Override
	public String handleClientEvent(Map<String, String> requestMap) {
		return null;
	}
}
