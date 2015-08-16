package com.linhongzheng.weixin.event;

public enum EVENT_TYPE {

	SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"), SCAN("SCAN"), LOCATION(
			"LOCATION"), CLICK("CLICK"), VIEW("VIEW");

	private String desc;

	EVENT_TYPE(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
