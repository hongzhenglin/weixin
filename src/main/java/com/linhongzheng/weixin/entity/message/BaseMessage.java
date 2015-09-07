package com.linhongzheng.weixin.entity.message;

public abstract class BaseMessage {
	private String ToUserName; // 请求消息是：开发者微信号；返回消息是：接收方帐号（收到的OpenID）
	private String FromUserName; // 请求消息是:发送方帐号（一个OpenID）；返回消息是：开发者微信号
	private long CreateTime; // 消息创建时间 （整型）,单位秒

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

}
