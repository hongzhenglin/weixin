package com.linhongzheng.weixin.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.message.MessageUtil;

/**
 * Created by linhz on 2015/8/18.
 */
@Service("eventMessageService")
public class EventMessageServiceImpl extends AbstractWeChatService implements
		IEventMessageService {
	@Autowired
	IUserService userService;

	@Override
	public String handleSubscribeEvent(Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		
		userService.saveWeiXinUser(fromUserName);
		
		TextResponseMessage textMessage = createTextMessage(fromUserName,
				toUserName);
		StringBuilder sb = new StringBuilder();
		sb.append("谢谢您的关注！当前不在关注时间 :)。本公众号提供下面几个功能：\n\n")
				.append("1. 歌曲点播,输入：歌曲歌曲名称，例如歌曲吻别\n")
				.append("2.天气预报,输入：天气城市名，例如天气广州或者广州天气\n")
				.append("3.游戏，输入：2048\n").append("4.签到功能，输入：签到\n")
				.append("5.人脸识别，上传小于2M的照片.\n")
				.append("6.欢迎加入微社区交流");
		textMessage.setContent(sb.toString());
		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleUbsubscribeEvent(Map<String, String> requestMap) {
		return null;

	}

	@Override
	public String handleClickEvent(Map<String, String> requestMap) {

		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		TextResponseMessage textMessage = createTextMessage(fromUserName,
				toUserName);
		String respMessage = null;
		// 事件KEY值，与创建自定义菜单时指定的KEY值对应
		String eventKey = requestMap.get("EventKey");

		if (eventKey.equals("V1001_TODAY_MUSIC")) {
			respMessage = "今日歌曲菜单项被点击！";
			textMessage.setContent(respMessage);
			respMessage = MessageUtil.messageToXml(textMessage);
		} else if (eventKey.equals("V1001_HELLO_WORLD")) {
			respMessage = "Hello world菜单项被点击！";
			textMessage.setContent(respMessage);
			respMessage = MessageUtil.messageToXml(textMessage);
		} else if (eventKey.equals("V1001_GOOD")) {
			respMessage = "赞一下我们菜单项被点击！";
			textMessage.setContent(respMessage);
			respMessage = MessageUtil.messageToXml(textMessage);
		} else if (eventKey.equals("oschina")) {
			Article article = new Article();
			article.setTitle("开源中国");
			article.setDescription("开源中国成立于2008年8月");
			article.setPicUrl("");
			article.setUrl("http://m.oschina.net");
			List<Article> articleList = new ArrayList<Article>();
			articleList.add(article);

			// 创建图文消息
			NewsResponseMessage newsMessage = new NewsResponseMessage();
			newsMessage.setToUserName(toUserName);
			newsMessage.setFromUserName(fromUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MSG_TYPE.NEWS.toString());
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.messageToXml(newsMessage);
		} else if (eventKey.equals("iteye")) {
			textMessage
					.setContent("ITeye即创办于2003年8月的JavaEye,从最初的讨论Java技术为主的技术论坛,"
							+ "已经逐渐发展成为涵盖整个软件开发领域的综合性网站。\n\nhttp://www.iteye.com");
			respMessage = MessageUtil.messageToXml(textMessage);
		}

		return respMessage;
	}

	@Override
	public String handleScanEvent(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String handleLocationEvent(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String handleViewEvent(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
