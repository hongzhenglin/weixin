package com.linhongzheng.weixin.entity.material;

public class WeiXinMedia {
	private String type; // 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	private String mediaId; // 媒体文件上传后，获取时的唯一标识
	private int createdAt; // 媒体文件上传时间戳

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "WeiXinMedia [type=" + type + ", mediaId=" + mediaId
				+ ", createdAt=" + createdAt + "]";
	}

}
