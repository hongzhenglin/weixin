package com.linhongzheng.weixin.entity.message.response;

public class Video {
	private String MediaId; // 是通过素材管理接口上传多媒体文件，得到的id
	private String Title; // 否视频消息的标题
	private String Description; // 视频消息的描述

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
