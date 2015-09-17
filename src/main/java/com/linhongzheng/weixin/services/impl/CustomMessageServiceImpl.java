package com.linhongzheng.weixin.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IAccessTokenService;
import com.linhongzheng.weixin.services.ICustomMessageService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

@Service("customMessageService")
public class CustomMessageServiceImpl extends AbstractWeChatService implements
		ICustomMessageService {
	private static final Logger log = LoggerFactory
			.getLogger(CustomMessageServiceImpl.class);
	@Autowired
	IAccessTokenService accessTokenService;

	@Override
	public void addCustomer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCustomer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delCustomer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void listCustomer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateHeadImage() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sendCustomerMessage(String jsonMsg) {
		String at = null;
		boolean result = false;
		try {
			at = accessTokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != at) {
			String requestUrl = URLConstants.CUSTOM.SEND_MESSAGE_URL.replace(
					"ACCESS_TOKEN", at);

			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					jsonMsg);
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("发送客服消息失败：errcode:{} errmsg:{}", errcode,
							jsonObject.getString("errmsg"));
				} else {
					result = true;
					log.info("发送客服消息成功：errcode:{} errmsg:{}", errcode,
							jsonObject.getString("errmsg"));
				}
			}
		}
		return result;
	}

	public IAccessTokenService getAccessTokenService() {
		return accessTokenService;
	}

	public void setAccessTokenService(IAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

}
