package com.linhongzheng.weixin.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.dao.BaseDAO;
import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.entity.message.event.EVENT_TYPE;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.entity.message.response.MusicResponseMessage;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.entity.user.WeiXinGroup;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.ITokenService;
import com.linhongzheng.weixin.services.IBaiduService;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.ITodayInHistoryService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.DateUtil;
import com.linhongzheng.weixin.utils.StringUtil;
import com.linhongzheng.weixin.utils.URLConstants;
import com.linhongzheng.weixin.utils.face.FacePlusPlusUtil;
import com.linhongzheng.weixin.utils.message.MessageUtil;

/**
 * Created by linhz on 2015/8/17.
 */
@Service("messageService")
public class MessageServiceImpl extends AbstractWeChatService implements
		IMessageService {
	private static final Logger log = LoggerFactory
			.getLogger(MessageServiceImpl.class);
	@Autowired
	IEventMessageService eventMessageService;

	@Autowired
	IUserService userService;

	@Autowired
	IBaiduService baiduService;

	@Autowired
	ITodayInHistoryService todayInHistoryService;

	@Autowired
	ITokenService tokenService;

	/**
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleDefaultResp(Map<String, String> requestMap) {

		// 默认回复此文本消息
		TextResponseMessage textResponseMessage = createTextMessage(requestMap);

		// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
		StringBuffer contentMsg = new StringBuffer();
		contentMsg.append(
				"欢迎访问<a href=\"http://chatcourse.duapp.com\">个人主页</a>").append(
				"\n");
		contentMsg.append("您好，我是机器人小Q，请回复数字选择服务：").append("\n\n");
		contentMsg.append("1 天气预报").append("\n");
		contentMsg.append("2 公交查询").append("\n");
		contentMsg.append("3 周边搜索").append("\n");
		contentMsg.append("4 歌曲点播").append("\n");
		contentMsg.append("5 经典游戏").append("\n");
		contentMsg.append("6 美女电台").append("\n");
		contentMsg.append("7 人脸识别").append("\n");
		contentMsg.append("8 聊天唠嗑").append("\n");
		contentMsg.append("9 电影排行榜").append("\n");
		contentMsg.append("10 Q友圈").append("\n\n");
		contentMsg
				.append("点击查看 <a href=\"http://chatcourse.duapp.com\">帮助手册</a>");

		textResponseMessage.setContent(contentMsg.toString());
		// 将文本消息对象转换成xml字符串
		return MessageUtil.messageToXml(textResponseMessage);

	}

	/**
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleTextMessage(Map<String, String> requestMap) {

		// 返回给微信服务器的xml消息
		String respXml = null;

		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");

		TextResponseMessage textResponseMessage = createTextMessage(requestMap);
		// 接收用户发送的文本消息内容
		String content = requestMap.get("Content").trim();
		// TODO 可以根据数据库的配置做非全文匹配
		log.info("文本消息内容：" + content);
		saveTextMessage(requestMap);

		if (content.equals("签到")) {
			sign(fromUserName, textResponseMessage);
		} else if (content.startsWith("天气") || content.endsWith("天气")) {
			return queryWeather(requestMap, content);
		} else if (content.equals("2048")) {
			return youxi(requestMap);

		} else if (content.equals("自行车")) {
			textResponseMessage.setContent("这辆" + MessageUtil.emoji(0x1F6B2)
					+ "怎么样，好看吗？");
		} else if (content.equals("历史上的今天")) {
			textResponseMessage.setContent(todayInHistoryService
					.getTodayInHistoryInfo());
		} else if (content.startsWith("翻译")) {
			String keyWord = content.replaceAll("^翻译", "").trim();
			if ("".equals(keyWord)) {
				textResponseMessage
						.setContent(baiduService.getTranslateUsage());
			} else {
				textResponseMessage.setContent(baiduService.translate(keyWord));
			}

		} else if (content.equals("模板消息")) {
			String data = createTemplateMessage(requestMap);
			sendTemplateMEssage(data);
			return null;
		} else if (content.startsWith("歌曲")) {
			String respContent = baiduMusic(requestMap, content);
			textResponseMessage.setContent(respContent);
		} else if (content.startsWith("人工服务")) { // 多客服，该公众号用户后续所有的消息交互，都会与同一个客服使用同一个会话，直到会话断开
			return MessageUtil.messageToXml(createCustomMessage(requestMap));
		}

		respXml = MessageUtil.messageToXml(textResponseMessage);
		return respXml;
	}

	private String baiduMusic(Map<String, String> requestMap, String content) {
		// 文本消息内容
		String respContent = null;
		// 如果以“歌曲”2个字开头

		log.info("百度歌曲搜索API");
		// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
		String keyWord = content.replaceAll("^歌曲[\\+ ~!#@%^-_=]?", "");
		// 如果歌曲名称为空
		if ("".equals(keyWord)) {
			respContent = baiduService.getMusicUsage();
		} else {
			String[] kwArr = keyWord.split("@");
			// 歌曲名称
			String musicTitle = kwArr[0];
			// 演唱者默认为空
			String musicAuthor = "";
			if (2 == kwArr.length)
				musicAuthor = kwArr[1];
			log.info("百度歌曲搜索,歌曲名称" + musicTitle + "  演唱者：" + musicAuthor);
			// 搜索音乐
			Music music = baiduService.searchMusic(musicTitle, musicAuthor);
			log.info("百度歌曲搜索结果：" + music.toString());
			// 未搜索到音乐
			if (null == music) {
				respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
			} else {
				MusicResponseMessage musicMessage = createMusicMessage(
						requestMap, music);
				respContent = MessageUtil.messageToXml(musicMessage);
				log.info("百度歌曲搜索返回," + respContent);
			}
		}
		// 未搜索到音乐时返回使用指南
		if (null == respContent) {
			respContent = baiduService.getMusicUsage();
		}
		return respContent;
	}

	/**
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleImageMessage(Map<String, String> requestMap) {
		// 默认回复此文本消息

		TextResponseMessage textResponseMessage = createTextMessage(requestMap);
		String imageUrl = requestMap.get("PicUrl");
		String faceJson = FacePlusPlusUtil.detectFace(imageUrl);
		String content = FacePlusPlusUtil.parseFaceJson(faceJson);

		textResponseMessage.setContent(content);
		return MessageUtil.messageToXml(textResponseMessage);

	}

	@Override
	public String handleVoiceMessage(Map<String, String> requestMap) {

		TextResponseMessage textResponseMessage = createTextMessage(requestMap);

		textResponseMessage.setContent("您发送的是音频内容是："
				+ requestMap.get("Recognition"));
		return MessageUtil.messageToXml(textResponseMessage);
	}

	@Override
	public String handleVideoMessage(Map<String, String> requestMap) {
		return null;
	}

	@Override
	public String handleLinkMessage(Map<String, String> requestMap) {

		return null;
	}

	@Override
	public String handleLocationMessage(Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		TextResponseMessage textResponseMessage = createTextMessage(
				fromUserName, toUserName);
		String content = "您目前所在的地理位置是：" + requestMap.get("Label");
		textResponseMessage.setContent(content);
		log.info(content);
		return MessageUtil.messageToXml(textResponseMessage);
	}

	@Override
	public String handleShortVideoMessage(Map<String, String> requestMap) {
		return null;
	}

	@Override
	public String handleEventMessage(Map<String, String> requestMap) {
		String responStr = null;
		String event = requestMap.get("Event");
		// 订阅
		if (event.equals(EVENT_TYPE.subscribe.toString())) {
			responStr = eventMessageService.handleSubscribeEvent(requestMap);
		}

		else if (event.equals(EVENT_TYPE.unsubscribe.toString())) {// 取消订阅
			// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
			responStr = eventMessageService.handleUbsubscribeEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.SCAN.toString())) { // 用户已关注时的事件推送
			responStr = eventMessageService.handleScanEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.LOCATION.toString())) { // 上报地理位置事件
			responStr = eventMessageService.handleLocationEvent(requestMap);
		}
		// 自定义菜单点击事件
		else if (event.equals(EVENT_TYPE.CLICK.toString())) {
			responStr = eventMessageService.handleClickEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.VIEW.toString())) {
			responStr = eventMessageService.handleViewEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.scancode_push.toString())) {
			responStr = eventMessageService.handleScancodePushEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.scancode_waitmsg.toString())) {
			responStr = eventMessageService
					.handleScancodeWaitmsgEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.pic_sysphoto.toString())) {
			responStr = eventMessageService.handlePicSysphotoEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.pic_photo_or_album.toString())) {
			responStr = eventMessageService
					.handlePicPhotoOrAlbumEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.pic_weixin.toString())) {
			responStr = eventMessageService.handlePicWeixinEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.location_select.toString())) {
			responStr = eventMessageService
					.handleLocationSelectEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.TEMPLATESENDJOBFINISH.toString())) {
			responStr = eventMessageService
					.handleTemplateSendJobFinishEvent(requestMap);
		} else if (event.equals(EVENT_TYPE.MASSSENDJOBFINISH.toString())) {
			responStr = eventMessageService
					.handleMassSendJobFinishEvent(requestMap);
		}
		return responStr;
	}

	/**
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleNewsMessage(Map<String, String> requestMap) {
		String respMessage = null;
		String content = requestMap.get("Content");

		NewsResponseMessage newsMessage = createNewsMessage(requestMap);
		List<Article> articleList = new ArrayList<Article>();
		// 单图文消息
		if ("1".equals(content)) {
			Article article = new Article();
			article.setTitle("微信公众帐号开发教程Java版");
			article.setDescription("方便PICC信息技术人员以及公司其他用户交流运维经验、提供运维技术支持、提高运维服务相应速度和服务质量。");
			article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
			article.setUrl("http://blog.csdn.net/lyq8479");
			articleList.add(article);
			// 设置图文消息个数
			newsMessage.setArticleCount(articleList.size());
			// 设置图文消息包含的图文集合
			newsMessage.setArticles(articleList);
			// 将图文消息对象转换成xml字符串

		}// 单图文消息---不含图片
		else if ("2".equals(content)) {
			Article article = new Article();
			article.setTitle("微信公众帐号开发教程Java版");
			// 图文消息中可以使用QQ表情、符号表情
			article.setDescription("柳峰，80后，" + MessageUtil.emoji(0x1F6B9)
					+ "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n"
					+ "目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n"
					+ "后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
			// 将图片置为空
			article.setPicUrl("");
			article.setUrl("http://blog.csdn.net/lyq8479");
			articleList.add(article);
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);

		}
		respMessage = MessageUtil.messageToXml(newsMessage);
		return respMessage;
	}

	private String queryWeather(Map<String, String> requestMap, String content) {
		String cityName = content.trim().replace("天气", "");
		NewsResponseMessage newsMessage = createNewsMessage(requestMap);
		baiduService.queryWeather(cityName, newsMessage);
		return MessageUtil.messageToXml(newsMessage);

	}

	private String youxi(Map<String, String> requestMap) {
		ConfigUtil configUtil = new ConfigUtil("host.properties");
		String hostUrl = configUtil.getValue("LOCALHOST_URL");
		List<Article> articleList = new ArrayList<Article>();
		NewsResponseMessage newsMessage = createNewsMessage(requestMap);
		Article article = new Article();

		article.setTitle("挑战2048");
		article.setDescription("这么好玩的游戏，快来挑战您的智商吧！");
		article.setPicUrl(hostUrl + "/image/2048/demo.jpg");
		article.setUrl(hostUrl + "/2048/index.html");

		articleList.add(article);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return MessageUtil.messageToXml(newsMessage);
	}

	private void sign(String fromUserName,
			TextResponseMessage textResponseMessage) {
		if (userService.isTodaySign(fromUserName)) {
			textResponseMessage.setContent("今天已经签到过，不用再签到");
		} else {
			String monday = DateUtil.getMondayOfWeek();
			if (userService.isWeekySign(fromUserName, monday)) {
				userService.saveWeiXinSign(fromUserName, 12);
				userService.updateUserPoints(fromUserName, 12);
				textResponseMessage.setContent("恭喜您本周第7次签到成功，额外送您10个积分");
			} else {
				userService.saveWeiXinSign(fromUserName, 2);
				userService.updateUserPoints(fromUserName, 2);
				textResponseMessage.setContent("恭喜您已签到成功，送您2个积分");
			}
		}
	}

	private void saveTextMessage(Map<String, String> requestMap) {

		String openId = requestMap.get("FromUserName");
		String content = requestMap.get("Content");
		String remark = "SAE MySQL Test";
		String insertSql = "INSERT INTO tb_message_text(open_id, content, create_time,remark) VALUES (?, ?, ?,?)";
		QueryRunner qr = new QueryRunner();

		new BaseDAO<Integer>().update(insertSql, openId, content, new Date(),
				remark);

	}

	/**
	 * 可以改成从数据库或者模板的值
	 * 
	 * @param requestMap
	 * @return
	 */
	public String createTemplateMessage(Map<String, String> requestMap) {
		Map<String, String> templateMap = new HashMap<String, String>();
		templateMap.put("templateId",
				"_EF4B1GzEWVPBL49fcYRsu1KcqwgSgBGrdIfWrntGxw");
		templateMap.put("toUser", requestMap.get("FromUserName"));
		templateMap.put("color", "#FF0000");
		templateMap
				.put("url", "http://1.linhzweixintest.sinaapp.com/index.jsp");
		templateMap.put("first", "今日签到信息");
		templateMap.put("keyword1", "高级工程师");
		templateMap.put("keyword2", "侨景大厦");
		templateMap.put("keyword3", "2015-09-11 8:00:00");
		templateMap.put("keyword4", "2015-09-11 12:00:00");
		templateMap.put("remark", "如果有問題，请联系HR！");

		return MessageUtil.encapTemplateMEssage(templateMap);

	}

	@Override
	public void sendTemplateMEssage(String data) {
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != at) {
			String requestUrl = URLConstants.TEMPLATE.TEMPLATE_SEND_URL
					.replace("ACCESS_TOKEN", at);

			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST", data);
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("发送模板消息失败：" + jsonObject.getString("errmsg"));
				}
			}
		}
	}

	public IEventMessageService getEventMessageService() {
		return eventMessageService;
	}

	public void setEventMessageService(IEventMessageService eventMessageService) {
		this.eventMessageService = eventMessageService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ITodayInHistoryService getTodayInHistoryService() {
		return todayInHistoryService;
	}

	public void setTodayInHistoryService(
			ITodayInHistoryService todayInHistoryService) {
		this.todayInHistoryService = todayInHistoryService;
	}

	public IBaiduService getBaiduService() {
		return baiduService;
	}

	public void setBaiduService(IBaiduService baiduService) {
		this.baiduService = baiduService;
	}

	public ITokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(ITokenService tokenService) {
		this.tokenService = tokenService;
	}

	public static void main(String[] args) {
		String content = "歌曲@吻别@张学友";
		System.out.println(content.replaceAll("^歌曲[\\+ ~!#@%^-_=]?", ""));
	}

}
