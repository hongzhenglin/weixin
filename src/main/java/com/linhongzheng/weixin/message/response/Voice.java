package com.linhongzheng.weixin.message.response;

public class Voice {
	private String MediaId; // 通过素材管理接口上传多媒体文件，得到的id

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
