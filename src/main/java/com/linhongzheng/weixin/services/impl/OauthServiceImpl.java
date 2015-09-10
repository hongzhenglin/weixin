package com.linhongzheng.weixin.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.sns.SNSUserInfo;
import com.linhongzheng.weixin.entity.sns.WeiXinOauth2Token;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IOauthService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

@Service("oauthService")
public class OauthServiceImpl extends AbstractWeChatService implements
		IOauthService {
	private static Logger log = LoggerFactory.getLogger(OauthServiceImpl.class);

	/**
	 * @param code
	 */
	@Override
	public WeiXinOauth2Token getOauth2AccessToken(String code) {
		return getOauth2AccessToken(super.appId, super.appSecret, code);
	}

	/**
	 * 获取网页oauth2.0 的Access_Token
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 */
	@Override
	public WeiXinOauth2Token getOauth2AccessToken(String appId,
			String appSecret, String code) {
		WeiXinOauth2Token wat = null;
		String requestUrl = URLConstants.OAUTH.OAUTH2_URL
				.replace("APPID", appId).replace("APPSECRET", appSecret)
				.replace("CODE", code);
		String jsonResp = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonResp != null) {
			JSONObject jsonObject = JSON.parseObject(jsonResp);
			int errcode = jsonObject.getIntValue("errcode");
			if (errcode != 0) {
				log.error("获取网页oauth2.0 的Access_Token失败："
						+ jsonObject.getString("errmsg"));
			} else {
				wat = createToken(jsonObject);
			}
		}
		return wat;
	}

	/**
	 * @param appId
	 * @param refreshToken
	 */
	@Override
	public WeiXinOauth2Token refreshOauth2AccessToken(String appId,
			String refreshToken) {
		WeiXinOauth2Token wat = null;
		String requestUrl = URLConstants.OAUTH.REFRESH_TOKEN_URL.replace(
				"APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		String jsonResp = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonResp != null) {
			JSONObject jsonObject = JSON.parseObject(jsonResp);
			int errcode = jsonObject.getIntValue("errcode");
			if (errcode != 0) {
				log.error("刷新网页oauth2.0 的Access_Token失败："
						+ jsonObject.getString("errmsg"));
			} else {
				wat = createToken(jsonObject);
			}
		}
		return wat;
	}

	/**
	 * @param accessToken
	 * @param openId
	 */
	@Override
	public SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		String requestUrl = URLConstants.OAUTH.USERINFO_TOKEN_URL.replace(
				"ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		String jsonResp = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonResp != null) {
			JSONObject jsonObject = JSON.parseObject(jsonResp);
			int errcode = jsonObject.getIntValue("errcode");
			if (errcode != 0) {
				log.error("刷新网页oauth2.0 的Access_Token失败："
						+ jsonObject.getString("errmsg"));
			} else {
				snsUserInfo = creatSnsUserInfo(jsonObject);

			}
		}
		return snsUserInfo;
	}

	private WeiXinOauth2Token createToken(JSONObject jsonObject) {
		WeiXinOauth2Token wat;
		wat = new WeiXinOauth2Token();
		wat.setAccessToken(jsonObject.getString("access_token"));
		wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
		wat.setOpenid(jsonObject.getString("openid"));
		wat.setRefreshToken(jsonObject.getString("refresh_token"));
		wat.setScope(jsonObject.getString("scope"));
		wat.setUnionid(jsonObject.getString("unionid"));
		return wat;
	}

	private SNSUserInfo creatSnsUserInfo(JSONObject jsonObject) {
		SNSUserInfo snsUserInfo;
		snsUserInfo = new SNSUserInfo();
		snsUserInfo.setOpenid(jsonObject.getString("openid"));
		snsUserInfo.setNickname(jsonObject.getString("nickname"));
		snsUserInfo.setSex(jsonObject.getIntValue("sex"));
		snsUserInfo.setCountry(jsonObject.getString("country"));
		snsUserInfo.setProvince(jsonObject.getString("province"));
		snsUserInfo.setCity(jsonObject.getString("city"));
		snsUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
		snsUserInfo.setPrivilege(JSONArray.parseArray(
				jsonObject.getString("privilege"), String.class));
		return snsUserInfo;
	}

}
