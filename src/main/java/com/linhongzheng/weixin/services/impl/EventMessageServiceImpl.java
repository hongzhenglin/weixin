package com.linhongzheng.weixin.services.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.message.MessageUtil;

/**
 * Created by linhz on 2015/8/18.
 */
@Service("eventMessageService")
public class EventMessageServiceImpl extends AbstractWeChatService implements
		IEventMessageService {
	@Autowired
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

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
