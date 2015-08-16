package com.linhongzheng.weixin.message.response;

public enum RESP_MSG_TYPE {
	TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), MUSIC("music"), NEWS(
			"news");

	private String desc;

	RESP_MSG_TYPE(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

}
