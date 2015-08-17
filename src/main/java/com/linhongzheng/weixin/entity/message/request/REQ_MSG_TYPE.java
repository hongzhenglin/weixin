package com.linhongzheng.weixin.entity.message.request;

public enum REQ_MSG_TYPE {

	TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), SHORTVIDEO(
			"shortvideo"), LOCATION("location"), LINK("link"), EVENT("event");

	private String desc;

	REQ_MSG_TYPE(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	public String toString(){
		return desc;
	}

}
