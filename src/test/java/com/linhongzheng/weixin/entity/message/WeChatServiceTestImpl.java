package com.linhongzheng.weixin.entity.message;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.IWeChatService;

public class WeChatServiceTestImpl implements IWeChatService {
	@Inject
	IMessageService messageService;

	public String processRequest() {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FromUserName", "philiphongzheng");
		requestMap.put("ToUserName", "philipLibn");
		requestMap.put("Content", "测试");
		return messageService.handleTextMessage(requestMap);
	}

	@Override
	public String processRequestRaw(HttpServletRequest request) {

		return null;

	}

	@Override
	public String processRequestCrypt(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

 

}
