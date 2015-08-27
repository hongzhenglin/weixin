package com.linhongzheng.weixin.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.IWeChatService;
import com.linhongzheng.weixin.utils.SignUtil;
import com.linhongzheng.weixin.utils.message.MessageUtil;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * Created by linhz on 2015/8/17.
 */
@Service("weChatService") 
public class WeChatServiceImpl extends AbstractWeChatService implements
		IWeChatService {

	@Autowired
	IMessageService messageService;

	// public WeChatServiceImpl() {
	// messageService = new MessageServiceImpl();
	// }
	@Override
	public String processRequestRaw(HttpServletRequest request)
			throws Exception {
		Map<String, String> requestMap = parseRawXml(request);

		return routeMessage(requestMap);

	}

	private Map<String, String> parseRawXml(HttpServletRequest request)
			throws IOException {
		// 验证消息
		Map<String, String> requestMap = new HashMap<String, String>();
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			// xml请求解析
			requestMap = MessageUtil.parseXml(request);
		}
		return requestMap;
	}

	@Override
	public String processRequestCrypt(HttpServletRequest request)
			throws Exception {
		Map<String, String> requestMap = parseCryptXml(request);
		String replyMsg = routeMessage(requestMap);
		return postHandler(request, replyMsg);
	}

	/**
	 * 加密返回的消息
	 * 
	 * @param request
	 * @param requestMap
	 * @return
	 * @throws AesException
	 */
	private String postHandler(HttpServletRequest request, String replyMsg)
			throws AesException {

		WXBizMsgCrypt pc = getWxCrypt();
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		return mingwen;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> parseCryptXml(HttpServletRequest request)
			throws Exception {
		Map<String, String> requestMap = new HashMap<String, String>();
		try (InputStream is = request.getInputStream();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));) {

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			WXBizMsgCrypt pc = getWxCrypt();

			String msgSignature = request.getParameter("msg_signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");

			Document doc = DocumentHelper.parseText(sb.toString());
			Element root = doc.getRootElement();
			MessageUtil.recursiveParseXml(requestMap, root);

			// 验证签名是否正确
			String msgEncrypt = requestMap.get("Encrypt");
			if (SignUtil.checkCryptSignature(msgSignature, timestamp, nonce,
					msgEncrypt)) {
				// 解密
				// 第三方收到公众号平台发送的解密后的消息
				String fromXML = pc.decryptMsg(msgSignature, timestamp, nonce,
						sb.toString());

				doc = DocumentHelper.parseText(fromXML);
				root = doc.getRootElement();
				MessageUtil.recursiveParseXml(requestMap, root);
				return requestMap;
			} else {
				return null;
			}
		}

	}

	/**
	 * 
	 * @param requestMap
	 * @return
	 */
	private String routeMessage(Map<String, String> requestMap) {
		String respMessage = null;
		// 消息类型
		String msgType = requestMap.get("MsgType");
		switch (MSG_TYPE.valueOf(msgType.toUpperCase())) {
		case TEXT: // 文本消息
			respMessage = messageService.handleTextMessage(requestMap);
			break;
		case IMAGE: // 图片消息
			respMessage = messageService.handleImageMessage(requestMap);
			break;
		case VOICE: // 音频消息
			respMessage = messageService.handleVoiceMessage(requestMap);
			break;
		case VIDEO: // 视频消息
			respMessage = messageService.handleVideoMessage(requestMap);
			break;
		case SHORTVIDEO: // 小视频消息
			respMessage = messageService.handleShortVideoMessage(requestMap);
			break;
		case LOCATION: // 地理位置消息
			respMessage = messageService.handleLocationMessage(requestMap);
			break;
		case LINK: // 链接消息
			respMessage = messageService.handleLinkMessage(requestMap);
			break;
		case EVENT: // 事件消息
			respMessage = messageService.handleEventMessage(requestMap);
			break;
		default:
			respMessage = messageService.handleDefaultResp(requestMap);
			break;
		}
		return respMessage;
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

}
