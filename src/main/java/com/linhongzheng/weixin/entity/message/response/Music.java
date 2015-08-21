package com.linhongzheng.weixin.entity.message.response;

public class Music {
	// 否音乐标题
	private String Title; 
	
	 // 否音乐描述
	private String Description;
	
	// 否音乐链接
	private String MusicURL; 
	
	// 否高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String HQMusicUrl; 
	
	// 是缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
	//不是必须参数，给用户回复音乐消息时可以不包含该参数
	private String ThumbMediaId; 

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
