package com.linhongzheng.weixin.entity.message.event;

public class MenuEvent extends BaseEvent {
	
	//事件KEY值，与自定义菜单接口中KEY值对应,如果事件类型是VIEW，那么微信服务器不会发送自定义菜单事件，
	//只有CLICK事件类型才会发送。
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	} 	
	
}
