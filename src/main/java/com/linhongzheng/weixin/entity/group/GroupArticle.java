package com.linhongzheng.weixin.entity.group;

public class GroupArticle {
	private String thumbMediaId; // 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
	private String author; // 图文消息的作者
	private String title; // 图文消息的标题
	private String contentSourceUrl; // 在图文消息页面点击“阅读原文”后的页面
	private String content; // 图文消息页面的内容，支持HTML标签
	private String digest; // 图文消息的描述
	private String showCoverPic; // 是否显示封面，1为显示，0为不显示

	
	public GroupArticle(String thumbMediaId, String author, String title,
			String contentSourceUrl, String content, String digest,
			String showCoverPic) {
		super();
		this.thumbMediaId = thumbMediaId;
		this.author = author;
		this.title = title;
		this.contentSourceUrl = contentSourceUrl;
		this.content = content;
		this.digest = digest;
		this.showCoverPic = showCoverPic;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic = showCoverPic;
	}

	@Override
	public String toString() {
		return "GroupArticle [thumbMediaId=" + thumbMediaId + ", author="
				+ author + ", title=" + title + ", contentSourceUrl="
				+ contentSourceUrl + ", content=" + content + ", digest="
				+ digest + ", showCoverPic=" + showCoverPic + "]";
	}

}
