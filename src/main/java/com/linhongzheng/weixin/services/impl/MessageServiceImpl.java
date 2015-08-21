package com.linhongzheng.weixin.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.linhongzheng.weixin.dao.BaseDAO;
import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.entity.message.event.EVENT_TYPE;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.ImageResponseMessage;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.entity.message.response.VoiceResponseMessage;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.DateUtil;
import com.linhongzheng.weixin.utils.message.MessageUtil;

/**
 * Created by linhz on 2015/8/17.
 */

public class MessageServiceImpl extends AbstractWeChatService implements
		IMessageService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageServiceImpl.class);
	@Inject
	IEventMessageService eventMessageService;

	@Inject
	IUserService userService;

	/**
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleTextMessage(Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		TextResponseMessage textResponseMessage = createTextMessage(
				fromUserName, toUserName);
		// 接收用户发送的文本消息内容
		String content = requestMap.get("Content");
		// TODO 可以根据数据库的配置做非全文匹配

		saveTextMessage(requestMap);

		if (content.equals("签到")) {
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
		return MessageUtil.messageToXml(textResponseMessage);
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
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleImageMessage(Map<String, String> requestMap) {

		// 默认回复此文本消息
		ImageResponseMessage imageResponseMessage = createImageMessage(requestMap);
		// Image image = createImage();
		// imageResponseMessage.setImage(image);
		return MessageUtil.messageToXml(imageResponseMessage);

	}

	@Override
	public String handleVoiceMessage(Map<String, String> requestMap) {
		VoiceResponseMessage voiceResponseMessage = createVoiceMessage(requestMap);

		// respMessage = "您发送的是音频消息！";
		// textMessage.setContent(respMessage);
		return MessageUtil.messageToXml(voiceResponseMessage);
	}

	private VoiceResponseMessage createVoiceMessage(
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

	@Override
	public String handleVideoMessage(Map<String, String> requestMap) {
		return null;
	}

	@Override
	public String handleLinkMessage(Map<String, String> requestMap) {
		/*
		 * createLink respMessage = "您发送的是地理位置消息！";
		 * textMessage.setContent(respMessage);
		 */
		// respMessage = MessageUtil.messageToXml(textMessage);
		return null;
	}

	@Override
	public String handleLocationMessage(Map<String, String> requestMap) {
		return null;
	}

	@Override
	public String handleShortVideoMessage(Map<String, String> requestMap) {
		return null;
	}

	@Override
	public String handleEventMessage(Map<String, String> requestMap) {
		String responStr = null;
		String eventType = requestMap.get("Event");
		// 订阅
		if (eventType.equals(EVENT_TYPE.SUBSCRIBE)) {

			responStr = eventMessageService.handleSubscribeEvent(requestMap);
		}
		// 取消订阅
		else if (eventType.equals(EVENT_TYPE.UNSUBSCRIBE)) {
			// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
			responStr = eventMessageService.handleUbsubscribeEvent(requestMap);
		}
		// 自定义菜单点击事件
		else if (eventType.equals(EVENT_TYPE.CLICK)) {
			// 事件KEY值，与创建自定义菜单时指定的KEY值对应
			String eventKey = requestMap.get("EventKey");

			/*
			 * if (eventKey.equals("11")) { respMessage = "天气预报菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage = MessageUtil.
			 * messageToXml(textMessage); } else if (eventKey.equals("12")) {
			 * respMessage = "公交查询菜单项被点击！"; textMessage.setContent(respMessage);
			 * respMessage = MessageUtil.textMessageToXml(textMessage); } else
			 * if (eventKey.equals("13")) { respMessage = "周边搜索菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("14")) { respMessage = "历史上的今天菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("21")) { respMessage = "歌曲点播菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("22")) { respMessage = "经典游戏菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("23")) { respMessage = "美女电台菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("24")) { respMessage = "人脸识别菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("25")) { respMessage = "聊天唠嗑菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("31")) { respMessage = "Q友圈菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("32")) { respMessage = "电影排行榜菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); } else if
			 * (eventKey.equals("33")) { respMessage = "幽默笑话菜单项被点击！";
			 * textMessage.setContent(respMessage); respMessage =
			 * MessageUtil.textMessageToXml(textMessage); }
			 */
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
			respMessage = MessageUtil.messageToXml(newsMessage);
		}

		return respMessage;
	}

	private NewsResponseMessage createNewsMessage(Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		NewsResponseMessage newsMessage = new NewsResponseMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MSG_TYPE.NEWS.toString().toLowerCase());
		return newsMessage;
	}

	/**
	 * @param requestMap
	 * @return
	 */
	private ImageResponseMessage createImageMessage(
			Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		ImageResponseMessage imageResponseMessage = new ImageResponseMessage();
		imageResponseMessage.setToUserName(fromUserName);
		imageResponseMessage.setFromUserName(toUserName);
		imageResponseMessage.setCreateTime(new Date().getTime());
		imageResponseMessage
				.setMsgType(MSG_TYPE.IMAGE.toString().toLowerCase());
		return imageResponseMessage;
	}

	/**
	 * @param requestMap
	 * @return
	 */
	@Override
	public String handleDefaultResp(Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		// 默认回复此文本消息
		TextResponseMessage textResponseMessage = createTextMessage(
				fromUserName, toUserName);

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

	private TextResponseMessage createTextMessage(String fromUserName,
			String toUserName) {
		TextResponseMessage textResponseMessage = new TextResponseMessage();
		textResponseMessage.setToUserName(fromUserName);
		textResponseMessage.setFromUserName(toUserName);
		textResponseMessage.setCreateTime(new Date().getTime());
		textResponseMessage.setMsgType(MSG_TYPE.TEXT.toString().toLowerCase());
		return textResponseMessage;
	}
}
