package com.linhongzheng.weixin.entity;

public class WeiXinQRCode {
	// 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	private String ticket;
	// 二维码的有效时间，以秒为单位。最大不超过1800
	private int expireSeconds;
	// 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	private String url;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "WeiXinQRCode [ticket=" + ticket + ", expireSeconds="
				+ expireSeconds + ", url=" + url + "]";
	}

}
