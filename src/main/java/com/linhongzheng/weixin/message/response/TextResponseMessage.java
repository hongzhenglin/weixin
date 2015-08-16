package com.linhongzheng.weixin.message.response;

public class TextResponseMessage extends BaseResponseMessage {
	private String Content; // 文本消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
