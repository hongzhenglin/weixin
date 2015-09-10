package com.linhongzheng.weixin.services;

import com.linhongzheng.weixin.entity.sns.SNSUserInfo;
import com.linhongzheng.weixin.entity.sns.WeiXinOauth2Token;

public interface IOauthService {
	
	public abstract WeiXinOauth2Token getOauth2AccessToken(String code);

	// 获取网页oauth2.0 的Access_Token
	public abstract WeiXinOauth2Token getOauth2AccessToken(String appId,
			String appSecret, String code);

	// 刷新网页oauth2.0 的Access_Token
	public WeiXinOauth2Token refreshOauth2AccessToken(String appId,
			String refreshToken);

	public SNSUserInfo getSNSUserInfo(String accessToken, String openId);
}
