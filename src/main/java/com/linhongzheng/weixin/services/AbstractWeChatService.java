package com.linhongzheng.weixin.services;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.entity.message.response.MusicResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.HttpUtil;
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
	 * 支付反馈
	 *
	 * @param openid
	 * @param feedbackid
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static boolean payfeedback(String openid, String feedbackid)
	 * throws Exception { Map<String, String> map = new HashMap<String,
	 * String>(); String accessToken = getAccessToken(); map.put("access_token",
	 * accessToken); map.put("openid", openid); map.put("feedbackid",
	 * feedbackid); String jsonStr = HttpKit.get(PAYFEEDBACK_URL, map);
	 * Map<String, Object> jsonMap = JSONObject.parseObject(jsonStr); return
	 * "0".equals(jsonMap.get("errcode").toString()); }
	 */

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
	 * 根据接收到用户消息进行处理
	 *
	 * @param responseInputString
	 *            微信发送过来的xml消息体
	 * @return
	 */
	/*
	 * public static String processing(String responseInputString) { InMessage
	 * inMessage = parsingInMessage(responseInputString); OutMessage oms = null;
	 * // 加载处理器 if (messageProcessingHandlerClazz == null) { //
	 * 获取自定消息处理器，如果自定义处理器则使用默认处理器。 String handler =
	 * ConfKit.get("MessageProcessingHandlerImpl"); handler = handler == null ?
	 * DEFAULT_HANDLER : handler; try { messageProcessingHandlerClazz =
	 * Thread.currentThread().getContextClassLoader().loadClass(handler); }
	 * catch (Exception e) { throw new
	 * RuntimeException("messageProcessingHandler Load Error！"); } } String xml
	 * = ""; try { MessageProcessingHandler messageProcessingHandler =
	 * (MessageProcessingHandler) messageProcessingHandlerClazz.newInstance();
	 * //取得消息类型 String type = inMessage.getMsgType(); Method getOutMessage =
	 * messageProcessingHandler.getClass().getMethod("getOutMessage"); Method
	 * alMt = messageProcessingHandler.getClass().getMethod("allType",
	 * InMessage.class); Method mt =
	 * messageProcessingHandler.getClass().getMethod(type + "TypeMsg",
	 * InMessage.class);
	 * 
	 * alMt.invoke(messageProcessingHandler, inMessage);
	 * 
	 * if (mt != null) { mt.invoke(messageProcessingHandler, inMessage); }
	 * 
	 * Object obj = getOutMessage.invoke(messageProcessingHandler); if (obj !=
	 * null) { oms = (OutMessage) obj; } //调用事后处理 try { Method aftMt =
	 * messageProcessingHandler.getClass().getMethod("afterProcess",
	 * InMessage.class, OutMessage.class);
	 * aftMt.invoke(messageProcessingHandler, inMessage, oms); } catch
	 * (Exception e) { }
	 * 
	 * obj = getOutMessage.invoke(messageProcessingHandler); if (obj != null) {
	 * oms = (OutMessage) obj; setMsgInfo(oms, inMessage); } } catch (Exception
	 * e) { throw new RuntimeException(e); } if (oms != null) { //
	 * 把发送发送对象转换为xml输出 XStream xs = XStreamFactory.init(true); xs.alias("xml",
	 * oms.getClass()); xs.alias("item", Articles.class); xml = xs.toXML(oms); }
	 * return xml; }
	 */
	/**
	 * 设置发送消息体
	 *
	 * @param oms
	 * @param msg
	 * @throws Exception
	 */
	/*
	 * private static void setMsgInfo(OutMessage oms, InMessage msg) throws
	 * Exception { if (oms != null) { Class<?> outMsg =
	 * oms.getClass().getSuperclass(); Field CreateTime =
	 * outMsg.getDeclaredField("CreateTime"); Field ToUserName =
	 * outMsg.getDeclaredField("ToUserName"); Field FromUserName =
	 * outMsg.getDeclaredField("FromUserName");
	 * 
	 * ToUserName.setAccessible(true); CreateTime.setAccessible(true);
	 * FromUserName.setAccessible(true);
	 * 
	 * CreateTime.set(oms, new Date().getTime()); ToUserName.set(oms,
	 * msg.getFromUserName()); FromUserName.set(oms, msg.getToUserName()); } }
	 */

	/**
	 * 消息体转换
	 *
	 * @param responseInputString
	 * @return
	 */
	/*
	 * private static InMessage parsingInMessage(String responseInputString) {
	 * //转换微信post过来的xml内容 XStream xs = XStreamFactory.init(false);
	 * xs.ignoreUnknownElements(); xs.alias("xml", InMessage.class); InMessage
	 * msg = (InMessage) xs.fromXML(responseInputString); return msg; }
	 */

	/**
	 * 获取媒体资源
	 *
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 * @throws java.util.concurrent.ExecutionException
	 */
	/*
	 * public static Attachment getMedia(String accessToken, String mediaId)
	 * throws IOException, ExecutionException, InterruptedException { String url
	 * = GET_MEDIA_URL + accessToken + "&media_id=" + mediaId; return
	 * HttpKit.download(url); }
	 */
	/**
	 * 上传素材文件
	 *
	 * @param type
	 * @param file
	 * @return
	 * @throws java.security.KeyManagementException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.NoSuchProviderException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static Map<String, Object> uploadMedia(String accessToken,
			String type, File file) throws Exception {
		String url = URLConstants.UPLOAD_MEDIA_URL + accessToken + "&type="
				+ type;
		List<File> fileList = new ArrayList<File>();
		fileList.add(file);
		String jsonStr = HttpUtil.upload(url, fileList, null);
		return JSON.parseObject(jsonStr, Map.class);
	}

	/**
	 * 获得jsapi_ticket（有效期7200秒)
	 *
	 * @param accessToken
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 */
	public static JSONObject getTicket(String accessToken) throws Exception,
			NoSuchProviderException {
		String jsonStr = HttpUtil.get(URLConstants.JSAPI_TICKET
				.concat(accessToken));
		return JSONObject.parseObject(jsonStr);
	}

	/**
	 * 生成jsApi的签名信息
	 *
	 * @param jsapiTicket
	 * @param url
	 * @return
	 */
	/*
	 * public static Map<String, String> jsApiSign(String jsapiTicket, String
	 * url) { return JsApiSign.sign(jsapiTicket, url); }
	 */
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

}
