package com.linhongzheng.weixin.entity.message.event;

public class QRCodeEvent extends BaseEvent {
	/**
	 * 事件KEY值，用户未关注时:qrscene_为前缀，后面为二维码的参数值;
	 * 用户已关注时:事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 */
	private String EventKey;

	private String Ticket; //二维码的ticket，可用来换取二维码图片

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}


}
