package com.linhongzheng.weixin.entity.message.response;

import com.linhongzheng.weixin.entity.message.BaseMessage;

public class BaseResponseMessage extends BaseMessage {
	private String MsgType; // 消息类型(text/image/voice/video/music/news)

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
