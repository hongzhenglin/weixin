package com.linhongzheng.weixin.message.response;

public class Music {
	private String Title; // 否音乐标题
	private String Description; // 否音乐描述
	private String MusicURL; // 否音乐链接
	private String HQMusicUrl; // 否高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String ThumbMediaId; // 是缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id

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

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
