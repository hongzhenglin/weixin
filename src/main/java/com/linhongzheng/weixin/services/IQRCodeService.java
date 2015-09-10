package com.linhongzheng.weixin.services;

import com.linhongzheng.weixin.entity.WeiXinQRCode;

public interface IQRCodeService {

	public abstract WeiXinQRCode createTemporaryQRCode(int expireSeconds,
			int sceneId);

	public abstract WeiXinQRCode createPermanentQRCode(int sceneId);

	public abstract String getQRCode(String ticket, String savePath);

}
