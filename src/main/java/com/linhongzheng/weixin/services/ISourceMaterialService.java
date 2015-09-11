package com.linhongzheng.weixin.services;

import com.linhongzheng.weixin.entity.material.WeiXinMedia;

public interface ISourceMaterialService {

	//上传临时素材
	public WeiXinMedia uploadMedia(String type, String mediaFileUrl);

	//获取临时素材
	public String getMedia(String mediaId, String savePath);
	
	
}
