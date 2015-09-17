package com.linhongzheng.weixin.services;

import java.util.Map;

/**
 * Created by linhz on 2015/8/18.
 */

public interface IEventMessageService {

	public abstract String handleSubscribeEvent(Map<String, String> requestMap);

	public abstract String handleUbsubscribeEvent(Map<String, String> requestMap);

	public abstract String handleClickEvent(Map<String, String> requestMap);

	public abstract String handleViewEvent(Map<String, String> requestMap);

	public abstract String handleScanEvent(Map<String, String> requestMap);

	// 处理上报地理位置事件
	public abstract String handleLocationEvent(Map<String, String> requestMap);

	public abstract String handleScancodePushEvent(
			Map<String, String> requestMap);

	public abstract String handleScancodeWaitmsgEvent(
			Map<String, String> requestMap);

	public abstract String handlePicSysphotoEvent(Map<String, String> requestMap);

	public abstract String handlePicPhotoOrAlbumEvent(
			Map<String, String> requestMap);

	public abstract String handlePicWeixinEvent(Map<String, String> requestMap);

	public abstract String handleLocationSelectEvent(
			Map<String, String> requestMap);

	// 模板消息发送成功后的推送事件
	public abstract String handleTemplateSendJobFinishEvent(
			Map<String, String> requestMap);

	// 群发消息发送成功后的推送事件
	public abstract String handleMassSendJobFinishEvent(
			Map<String, String> requestMap);
}
