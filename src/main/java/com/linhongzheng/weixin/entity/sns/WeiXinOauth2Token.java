package com.linhongzheng.weixin.entity.sns;

public class WeiXinOauth2Token {
	private String accessToken; // 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	private int expiresIn; // access_token接口调用凭证超时时间，单位（秒）
	private String refreshToken; // 用户刷新access_token
	private String openid; // 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
	private String scope; // 用户授权的作用域，使用逗号（,）分隔
	private String unionid; // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
