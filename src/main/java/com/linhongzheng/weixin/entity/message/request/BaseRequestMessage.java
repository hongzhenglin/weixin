package com.linhongzheng.weixin.entity.message.request;

import com.linhongzheng.weixin.entity.message.BaseMessage;

public class BaseRequestMessage extends BaseMessage {
	private String MsgId; // 消息id，用于排重
	private String MsgType; // 消息类型(text/image/voice/video/shortvideo/location/link)

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}
