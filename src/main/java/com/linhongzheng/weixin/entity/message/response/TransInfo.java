package com.linhongzheng.weixin.entity.message.response;

public class TransInfo {
	private String KfAccount; //指定会话接入的客服账号

	public TransInfo(String kfAccount) {
		super();
		KfAccount = kfAccount;
	}

	public String getKfAccount() {
		return KfAccount;
	}

	public void setKfAccount(String kfAccount) {
		KfAccount = kfAccount;
	}
	
}
