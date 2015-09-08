package com.linhongzheng.weixin.entity.message.custom;

public abstract class BaseCustomMessage {
	private String touser; // 普通用户openid
	private String msgtype; // 消息类型

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}
