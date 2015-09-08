package com.linhongzheng.weixin.entity.message.custom;

public class Customer {
	private String kf_account; // 完整客服账号，格式为：账号前缀@公众号微信号
	private String nickname; // 客服昵称，最长6个汉字或12个英文字符
	private String password; // 客服账号登录密码，格式为密码明文的32位加密MD5值。
	// 该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码

	public Customer(){
		
	}
	
}
