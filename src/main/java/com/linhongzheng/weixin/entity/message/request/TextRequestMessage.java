package com.linhongzheng.weixin.entity.message.request;

public class TextRequestMessage extends BaseRequestMessage {

	private String Content; // 文本消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
