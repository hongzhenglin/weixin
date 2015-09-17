package com.linhongzheng.weixin.services;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.entity.message.response.CustomResponseMessage;
import com.linhongzheng.weixin.entity.message.response.ImageResponseMessage;
import com.linhongzheng.weixin.entity.message.response.KfAccoutCustomResponseMessage;
import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.entity.message.response.MusicResponseMessage;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TransInfo;
import com.linhongzheng.weixin.entity.message.response.VoiceResponseMessage;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.SignUtil;
import com.linhongzheng.weixin.utils.URLConstants;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * Created by linhz on 2015/8/17.
 */
public abstract class AbstractWeChatService {

	protected static Class<?> messageProcessingHandlerClazz = null;
	protected String appId = null;
	protected String appSecret = null;

	public AbstractWeChatService() {
		ConfigUtil configUtil = new ConfigUtil();
		appId = configUtil.getValue("AppId");
		appSecret = configUtil.getValue("AppSecret");
	}

	/**
	 * 签名检查
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static Boolean checkSignature(String token, String signature,
			String timestamp, String nonce) {
		return SignUtil.checkSignature(signature, timestamp, nonce);
	}

	/**
	 * 
	 * @return
	 * @throws AesException
	 */
	protected WXBizMsgCrypt getWxCrypt() throws AesException {
		WXBizMsgCrypt pc = new WXBizMsgCrypt(SignUtil.Token,
				SignUtil.EncodingAesKey, appId);
		return pc;
	}

	/**
	 * 判断是否来自微信, 5.0 之后的支持微信支付
	 *
	 * @param request
	 * @return
	 */
	protected boolean isWeiXin(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(userAgent)) {
			Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
			Matcher m = p.matcher(userAgent);
			String version = null;
			if (m.find()) {
				version = m.group(1);
			}
			return (null != version && NumberUtils.toInt(version) >= 5);
		}
		return false;
	}

	protected TextResponseMessage createTextMessage(String fromUserName,
			String toUserName) {
		TextResponseMessage textResponseMessage = new TextResponseMessage();
		textResponseMessage.setToUserName(fromUserName);
		textResponseMessage.setFromUserName(toUserName);
		textResponseMessage.setCreateTime(new Date().getTime() / 1000);
		textResponseMessage.setMsgType(MSG_TYPE.TEXT.toString().toLowerCase());
		return textResponseMessage;
	}

	protected TextResponseMessage createTextMessage(
			Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");

		TextResponseMessage textMessage = createTextMessage(fromUserName,
				toUserName);
		return textMessage;
	}

	protected CustomResponseMessage createCustomMessage(
			Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		CustomResponseMessage customResponseMessage = new CustomResponseMessage();
		customResponseMessage.setToUserName(fromUserName);
		customResponseMessage.setFromUserName(toUserName);
		customResponseMessage.setCreateTime(new Date().getTime() / 1000);
		customResponseMessage.setMsgType(MSG_TYPE.TRANSFER_CUSTOMER_SERVICE
				.toString().toLowerCase());
		return customResponseMessage;
	}

	protected KfAccoutCustomResponseMessage createKfAccountCustomMessage(
			Map<String, String> requestMap, String kfAccount) {
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		KfAccoutCustomResponseMessage customResponseMessage = new KfAccoutCustomResponseMessage();
		customResponseMessage.setToUserName(fromUserName);
		customResponseMessage.setFromUserName(toUserName);
		customResponseMessage.setCreateTime(new Date().getTime() / 1000);
		customResponseMessage.setMsgType(MSG_TYPE.TRANSFER_CUSTOMER_SERVICE
				.toString().toLowerCase());
		customResponseMessage.setTransInfo(new TransInfo(kfAccount));
		return customResponseMessage;
	}

	/**
	 * @param requestMap
	 * @return
	 */
	protected ImageResponseMessage createImageMessage(
			Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		ImageResponseMessage imageResponseMessage = new ImageResponseMessage();
		imageResponseMessage.setToUserName(fromUserName);
		imageResponseMessage.setFromUserName(toUserName);
		imageResponseMessage.setCreateTime(new Date().getTime() / 1000);
		imageResponseMessage
				.setMsgType(MSG_TYPE.IMAGE.toString().toLowerCase());
		return imageResponseMessage;
	}

	protected MusicResponseMessage createMusicMessage(
			Map<String, String> requestMap, Music music) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		// 音乐消息
		MusicResponseMessage musicMessage = new MusicResponseMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MSG_TYPE.MUSIC.toString().toLowerCase());
		musicMessage.setMusic(music);
		return musicMessage;
	}

	protected NewsResponseMessage createNewsMessage(
			Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		NewsResponseMessage newsMessage = new NewsResponseMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime() / 1000);
		newsMessage.setMsgType(MSG_TYPE.NEWS.toString().toLowerCase());
		return newsMessage;
	}

	protected VoiceResponseMessage createVoiceMessage(
			Map<String, String> requestMap) {
		VoiceResponseMessage voiceResponseMessage = new VoiceResponseMessage();
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		String respMessage = "您发送的是音频消息！";
		// voiceResponseMessage.s(respMessage);
		return voiceResponseMessage;
	}

	public static void main(String[] args) {
		ConfigUtil configUtil = new ConfigUtil();
		String appId = configUtil.getValue("AppId");
		String oauthUrl = "http://linhzweixintest.sinaapp.com/oauthServlet";
		System.out.println(URLConstants.OAUTH.OAUTH2_CODE_URL
				.replace("REDIRECT_URI", oauthUrl).replace("APPID", appId)
				.replace("SCOPE", "snsapi_userinfo").toString());
	}
}
