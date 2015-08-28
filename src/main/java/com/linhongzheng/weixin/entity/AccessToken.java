package com.linhongzheng.weixin.entity;

public class AccessToken {

	private String access_token;
	private String expire_in;
	private String errcode;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpire_in() {
		return expire_in;
	}
	public void setExpire_in(String expire_in) {
		this.expire_in = expire_in;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	@Override
	public String toString() {
		return "AccessToken [access_token=" + access_token + ", expire_in="
				+ expire_in + ", errcode=" + errcode + "]";
	}
	
}
