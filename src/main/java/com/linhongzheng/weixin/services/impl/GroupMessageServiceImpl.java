package com.linhongzheng.weixin.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.entity.group.GroupArticle;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IAccessTokenService;
import com.linhongzheng.weixin.services.IGroupMessageService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

@Service("groupMessageService")
public class GroupMessageServiceImpl extends AbstractWeChatService implements
		IGroupMessageService {

	@Autowired
	IAccessTokenService accessTokenService;

	@Override
	public void sendMessageByGroupid(String jsonData) {

		String at = null;
		try {
			at = accessTokenService.getAccessToken();

			String requestUrl = URLConstants.GROUP.GROUP_MESSAGE_SEND_URL
					.replace("ACCESS_TOKEN", at);
			System.out.println("按组群发返回结果："
					+ CommonUtil.httpsRequest(requestUrl, "POST", jsonData));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMessageByOpenids(String jsonData) {
		String at = null;
		try {
			at = accessTokenService.getAccessToken();
			String requestUrl = URLConstants.GROUP.OPENID_MESSAGE_SEND_URL
					.replace("ACCESS_TOKEN", at);
			System.out.println("按openId群发返回结果："
					+ CommonUtil.httpsRequest(requestUrl, "POST", jsonData));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteMessage(String msgId) {

		String at = null;
		try {
			at = accessTokenService.getAccessToken();
			String requestUrl = URLConstants.GROUP.DELETE_MESSAGE_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonData = String.format("{\"msg_id\": \"%s\"}", msgId);
			System.out.println("删除消息返回结果："
					+ CommonUtil.httpsRequest(requestUrl, "POST", jsonData));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String queryMessageSendStatus(String msgId) {
		String at = null;
		String resp = null;
		try {
			at = accessTokenService.getAccessToken();
			String requestUrl = URLConstants.GROUP.MESSAGE_STATUS_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonData = String.format("{\"msg_id\": \"%s\"}", msgId);
			resp = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}

	public IAccessTokenService getAccessTokenService() {
		return accessTokenService;
	}

	public void setAccessTokenService(IAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	@Override
	public void uploadImage(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadNews(List<GroupArticle> articles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preview(String jsonData) {
		// TODO Auto-generated method stub
		
	}

}
