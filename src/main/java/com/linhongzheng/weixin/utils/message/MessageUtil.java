package com.linhongzheng.weixin.utils.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.ImageResponseMessage;
import com.linhongzheng.weixin.entity.message.response.MusicResponseMessage;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.entity.message.response.VedioResponseMessage;
import com.linhongzheng.weixin.entity.message.response.VoiceResponseMessage;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.StringUtils;
import com.linhongzheng.weixin.utils.XStreamUtil;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	private static final String UPLOADNEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";
	private static final String MASS_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
	private static final String MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
	private static final String MASS_DELETE_URL = "https://api.weixin.qq.com//cgi-bin/message/mass/delete?access_token=";
	private static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	public static Map<String, String> parseXml(HttpServletRequest request)
			throws IOException {

		return parseInXml(request.getInputStream());

	}

	public static Map parseInXml(InputStream in) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(in);
			Element root = document.getRootElement();
			recursiveParseXml(map, root);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @param map
	 * @param root
	 */
	private static void recursiveParseXml(Map<String, String> map, Element root) {
		List<Element> elementList = root.elements();
		if (elementList.size() == 0) {
			map.put(root.getName(), root.getTextTrim());
		} else {
			for (Element e : elementList) {
				recursiveParseXml(map, e);
			}
		}
	}

	public static String messageToXml(TextResponseMessage textResponseMessage) {
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", TextResponseMessage.class);
		return xStream.toXML(textResponseMessage);
	}

	public static String messageToXml(ImageResponseMessage imageResponseMessage) {
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", ImageResponseMessage.class);
		return xStream.toXML(imageResponseMessage);
	}

	public static String messageToXml(VoiceResponseMessage voiceResponseMessage) {
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", VoiceResponseMessage.class);
		return xStream.toXML(voiceResponseMessage);
	}

	public static String messageToXml(VedioResponseMessage vedioResponseMessage) {
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", VedioResponseMessage.class);
		return xStream.toXML(vedioResponseMessage);
	}

	public static String messageToXml(MusicResponseMessage musicResponseMessage) {
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", MusicResponseMessage.class);
		return xStream.toXML(musicResponseMessage);
	}

	public static String messageToXml(NewsResponseMessage newsMessage) {
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", NewsResponseMessage.class);
		xStream.alias("item", Article.class);
		return xStream.toXML(newsMessage);
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 *
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}

	/**
	 * 发送客服消息
	 *
	 * @param accessToken
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String sendMsg(String accessToken, Map<String, String> message)
			throws Exception {
		String result = HttpUtil.post(MESSAGE_URL.concat(accessToken), message);
		return result;
	}

	/**
	 * 发送文本客服消息
	 *
	 * @param openId
	 * @param text
	 * @throws Exception
	 */
	/*
	 * public String sendText(String accessToken, String openId, String text)
	 * throws Exception { Map<String, Object> json = new HashMap<String,
	 * Object>(); Map<String, Object> textObj = new HashMap<String, Object>();
	 * textObj.put("content", text); json.put("touser", openId);
	 * json.put("msgtype", "text"); json.put("text", textObj); String result =
	 * sendMsg(accessToken, json); return result; }
	 */

	/**
	 * 发送图片消息
	 *
	 * @param accessToken
	 * @param openId
	 * @param media_id
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String SendImage(String accessToken, String openId, String
	 * media_id) throws Exception { Map<String, Object> json = new
	 * HashMap<String, Object>(); Map<String, Object> textObj = new
	 * HashMap<String, Object>(); textObj.put("media_id", media_id);
	 * json.put("touser", openId); json.put("msgtype", "image");
	 * json.put("image", textObj); String result = sendMsg(accessToken, json);
	 * return result; }
	 */

	/**
	 * 发送语言回复
	 *
	 * @param accessToken
	 * @param openId
	 * @param media_id
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String SendVoice(String accessToken, String openId, String
	 * media_id) throws Exception { Map<String, Object> json = new
	 * HashMap<String, Object>(); Map<String, Object> textObj = new
	 * HashMap<String, Object>(); textObj.put("media_id", media_id);
	 * json.put("touser", openId); json.put("msgtype", "voice");
	 * json.put("voice", textObj); String result = sendMsg(accessToken, json);
	 * return result; }
	 */

	/**
	 * 发送视频回复
	 *
	 * @param accessToken
	 * @param openId
	 * @param media_id
	 * @param title
	 * @param description
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String SendVideo(String accessToken, String openId, String
	 * media_id, String title, String description) throws Exception {
	 * Map<String, Object> json = new HashMap<String, String>(); Map<String,
	 * String> textObj = new HashMap<String, String>(); textObj.put("media_id",
	 * media_id); textObj.put("title", title); textObj.put("description",
	 * description);
	 * 
	 * json.put("touser", openId); json.put("msgtype", "video");
	 * json.put("video", textObj); String result = sendMsg(accessToken, json);
	 * return result; }
	 */

	/**
	 * 发送音乐回复
	 *
	 * @param accessToken
	 * @param openId
	 * @param musicurl
	 * @param hqmusicurl
	 * @param thumb_media_id
	 * @param title
	 * @param description
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String SendMusic(String accessToken, String openId, String
	 * musicurl, String hqmusicurl, String thumb_media_id, String title, String
	 * description) throws Exception { Map<String, Object> json = new
	 * HashMap<String, Object>(); Map<String, Object> textObj = new
	 * HashMap<String, Object>(); textObj.put("musicurl", musicurl);
	 * textObj.put("hqmusicurl", hqmusicurl); textObj.put("thumb_media_id",
	 * thumb_media_id); textObj.put("title", title); textObj.put("description",
	 * description);
	 * 
	 * json.put("touser", openId); json.put("msgtype", "music");
	 * json.put("music", textObj); String result = sendMsg(accessToken, json);
	 * return result; }
	 */

	/**
	 * 发送图文回复
	 *
	 * @param accessToken
	 * @param openId
	 * @param articles
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String SendNews(String accessToken, String openId, List<Article>
	 * articles) throws Exception { Map<String, Object> json = new
	 * HashMap<String, Object>(); json.put("touser", openId);
	 * json.put("msgtype", "news"); // json.put("voice", articles); String
	 * result = sendMsg(accessToken, json); return result; }
	 */
	/**
	 * 上传图文消息素材
	 *
	 * @param accessToken
	 * @param articles
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IOException
	 */
	/*
	 * public JSONObject uploadnews(String accessToken, List<Article> articles)
	 * throws IOException, ExecutionException, InterruptedException {
	 * Map<String, Object> json = new HashMap<String, Object>();
	 * json.put("articles", articles); String result =
	 * HttpUtil.post(UPLOADNEWS_URL.concat(accessToken),
	 * JSONObject.toJSONString(json)); if (StringUtils.isNotEmpty(result)) {
	 * return JSONObject.parseObject(result); } return null; }
	 */

	/**
	 * 群发消息
	 * 
	 * @param accessToken
	 *            token
	 * @param type
	 *            群发消息类型
	 * @param content
	 *            内容
	 * @param title
	 *            类型是video是有效
	 * @param description
	 *            类型是video是有效
	 * @param groupId
	 *            发送目标对象的群组id
	 * @param openids
	 *            发送目标对象的openid类表
	 * @param toAll
	 *            是否发送给全部人
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	/*
	 * public JSONObject massSendall(String accessToken, SendAllMsgTypes type,
	 * String content, String title, String description, String groupId,
	 * String[] openids, boolean toAll) throws InterruptedException,
	 * ExecutionException, IOException { Map<String, Object> json = new
	 * HashMap<String, Object>(); Map<String, Object> filter = new
	 * HashMap<String, Object>(); Map<String, Object> body = new HashMap<String,
	 * Object>();
	 * 
	 * filter.put("is_to_all", false); json.put("msgtype", type.getType()); if
	 * (toAll) { filter.put("is_to_all", true); } else if
	 * (StringUtils.isNotEmpty(groupId)) { filter.put("group_id", groupId); }
	 * else if (openids != null && openids.length > 0) { json.put("touser",
	 * openids); }
	 * 
	 * switch (type) { case TEXT: body.put("content", content); json.put("text",
	 * body); break; case IMAGE: body.put("media_id", content);
	 * json.put("image", body); break; case VOICE: body.put("media_id",
	 * content); json.put("voice", body); break; case MPVIDEO:
	 * body.put("media_id", content); json.put("mpvideo", body); break; case
	 * MPNEWS: body.put("media_id", content); json.put("mpnews", body); break;
	 * case VIDEO: body.put("media_id", content); body.put("title", title);
	 * body.put("description", description); json.put("video", body); break; }
	 * String result = HttpUtil.post(MASS_SENDALL_URL.concat(accessToken),
	 * JSONObject.toJSONString(json)); if (StringUtils.isNotEmpty(result)) {
	 * return JSONObject.parseObject(result); } return null; }
	 */

	/**
	 * 删除群发
	 *
	 * @param accessToken
	 * @param msgid
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IOException
	 */
	public JSONObject massSend(String accessToken, String msgid)
			throws IOException, ExecutionException, InterruptedException {
		Map<String, String> json = new HashMap<String, String>();
		json.put("msgid", msgid);
		String result = HttpUtil
				.post(MASS_DELETE_URL.concat(accessToken), json);
		if (StringUtils.isNotEmpty(result)) {
			return JSONObject.parseObject(result);
		}
		return null;
	}

	/**
	 * @param in
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private static final String inputStream2String(InputStream in)
			throws UnsupportedEncodingException, IOException {
		if (in == null)
			return "";

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}
	/**
	 * 发送模板消息
	 *
	 * @param accessToken
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	/*
	 * public JSONObject templateSend(String accessToken, TemplateData data)
	 * throws IOException, ExecutionException, InterruptedException { String
	 * result = HttpUtil.post(TEMPLATE_SEND_URL.concat(accessToken),
	 * JSONObject.toJSONString(data)); if (StringUtils.isNotEmpty(result)) {
	 * return JSONObject.parseObject(result); } return null; }
	 */
}
