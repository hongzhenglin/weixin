package com.linhongzheng.weixin.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TextResponseMessage;
import com.linhongzheng.weixin.entity.user.WeiXinUserInfo;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.StringUtil;
import com.linhongzheng.weixin.utils.message.MessageUtil;

/**
 * Created by linhz on 2015/8/18.
 */
@Service("eventMessageService")
public class EventMessageServiceImpl extends AbstractWeChatService implements
		IEventMessageService {

	private static Logger log = LoggerFactory
			.getLogger(EventMessageServiceImpl.class);

	@Autowired
	IUserService userService;

	@Override
	public String handleSubscribeEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		WeiXinUserInfo weiXinUserInfo = userService.getUserInfo(fromUserName);
		String nickName = weiXinUserInfo.getNickname();
		log.info("用户" + nickName + "开始关注。");
		userService.saveWeiXinUser(fromUserName);

		StringBuilder sb = new StringBuilder();
		sb.append("谢谢" + nickName + "的关注！当前不在关注时间 /微笑 /::)。本公众号提供下面几个功能：\n\n")
				.append("1. 歌曲点播,输入：歌曲歌曲名称，例如歌曲吻别\n")
				.append("2.天气预报,输入：天气城市名，例如天气广州或者广州天气\n")
				.append("3.游戏，输入：2048\n").append("4.签到功能，输入：签到\n")
				.append("5.人脸识别，上传小于2M的照片\n").append("6.欢迎加入微社区交流  /::D");
		textMessage.setContent(sb.toString());

		String eventKey = requestMap.get("EventKey");
		// 用户未关注时，进行关注后的事件推送

		if (StringUtil.isNotEmpty(eventKey)) {
			log.info("用户开始关注，并发送了推送事件" + eventKey + "开始关注。二維碼的ticket为"
					+ requestMap.get("ticket"));
		}
		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleUbsubscribeEvent(Map<String, String> requestMap) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		userService.updateWeiXinUserStatus(fromUserName, 0);
		log.info("用户" + fromUserName + "取消关注。");
		return null;

	}

	@Override
	public String handleClickEvent(Map<String, String> requestMap) {

		TextResponseMessage textMessage = createTextMessage(requestMap);
		String respMessage = null;
		// 事件KEY值，与创建自定义菜单时指定的KEY值对应
		String eventKey = requestMap.get("EventKey");

		if (eventKey.equals("oschina")) {
			Article article = new Article();
			article.setTitle("开源中国");
			article.setDescription("开源中国成立于2008年8月");
			article.setPicUrl("");
			article.setUrl("http://m.oschina.net");
			List<Article> articleList = new ArrayList<Article>();
			articleList.add(article);

			// 创建图文消息
			NewsResponseMessage newsMessage = createNewsMessage(requestMap);
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.messageToXml(newsMessage);
		} else if (eventKey.equals("iteye")) {
			textMessage
					.setContent("ITeye即创办于2003年8月的JavaEye,从最初的讨论Java技术为主的技术论坛,"
							+ "已经逐渐发展成为涵盖整个软件开发领域的综合性网站。\n\nhttp://www.iteye.com");
			respMessage = MessageUtil.messageToXml(textMessage);
		} else if (eventKey.indexOf("oauth2") > 0) {
			textMessage
					.setContent("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx16c644d1873b9073&redirect_uri=http%3A%2F%2Flinhzweixintest.sinaapp.com%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
			respMessage = MessageUtil.messageToXml(textMessage);
		}

		return respMessage;
	}

	@Override
	public String handleViewEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		String respMessage = null;
		// 事件KEY值，与创建自定义菜单时指定的KEY值对应
		String eventKey = requestMap.get("EventKey");

		if (eventKey.equals("http://m.taobao.com")) {
			Article article = new Article();
			article.setTitle("淘宝");
			article.setDescription("淘宝成立于2002年");
			article.setPicUrl("");
			article.setUrl("http://m.taobao.com");
			List<Article> articleList = new ArrayList<Article>();
			articleList.add(article);
			// 创建图文消息
			NewsResponseMessage newsMessage = createNewsMessage(requestMap);
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.messageToXml(newsMessage);
		} else if (eventKey.equals("http://m.vipshop.com")) {
			textMessage.setContent("唯品会是一家专注于特卖的垂直网站。\n\nhttp://m.vipshop.com");
			respMessage = MessageUtil.messageToXml(textMessage);
		}

		return respMessage;
	}

	@Override
	public String handleScancodePushEvent(Map<String, String> requestMap) {

		String eventKey = requestMap.get("EventKey");
		String scanResult = requestMap.get("ScanResult");

		TextResponseMessage textMessage = createTextMessage(requestMap);
		if (eventKey.equals("KEY_11")) {
			textMessage.setContent("您点击了[扫描推事件]按钮。\n\n您扫描的结果是" + scanResult);
		}
		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleScancodeWaitmsgEvent(Map<String, String> requestMap) {

		String eventKey = requestMap.get("EventKey");
		String scanResult = requestMap.get("ScanResult");

		TextResponseMessage textMessage = createTextMessage(requestMap);
		if (eventKey.equals("KEY_12")) {
			textMessage.setContent("您点击了[扫描带提示]按钮。\n\n您扫描的结果是" + scanResult);
		}

		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handlePicSysphotoEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		String eventKey = requestMap.get("EventKey");
		String count = requestMap.get("Count");

		if (eventKey.equals("KEY_21")) {
			textMessage.setContent("您点击了[系统拍照发图]按钮。\n\n您上传了" + count + "张照片。");
		}

		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handlePicPhotoOrAlbumEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		String eventKey = requestMap.get("EventKey");
		String count = requestMap.get("Count");

		if (eventKey.equals("KEY_22")) {
			textMessage
					.setContent("您点击了[拍照或者相册发图]按钮。\n\n您上传了" + count + "张照片。");
		}

		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handlePicWeixinEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		String eventKey = requestMap.get("EventKey");
		String count = requestMap.get("Count");

		if (eventKey.equals("KEY_23")) {
			textMessage.setContent("您点击了[微信相册发图]按钮。\n\n您上传了" + count + "张照片。");
		}

		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleLocationSelectEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		String eventKey = requestMap.get("EventKey");

		String label = requestMap.get("Lable");
		if (eventKey.equals("KEY_31")) {
			textMessage.setContent("您点击了[发送位置]按钮。\n\n您当前的位置是" + label);
		}

		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleScanEvent(Map<String, String> requestMap) {
		TextResponseMessage textMessage = createTextMessage(requestMap);
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		log.info("用户" + fromUserName + "已关注。");
		userService.saveWeiXinUser(fromUserName);

		String eventKey = requestMap.get("EventKey");
		// 用户未关注时，进行关注后的事件推送

		String content = "用户已关注时的事件推送" + eventKey + "。二維碼的ticket为"
				+ requestMap.get("ticket");
		textMessage.setContent(content);
		log.info(content);

		return MessageUtil.messageToXml(textMessage);
	}

	@Override
	public String handleLocationEvent(Map<String, String> requestMap) {

		TextResponseMessage textResponseMessage = createTextMessage(requestMap);
		String content = "您当前的经纬度为：" + requestMap.get("Longitude") + ","
				+ requestMap.get("Latitude");
		textResponseMessage.setContent(content);
		log.info(content);
		return MessageUtil.messageToXml(textResponseMessage);
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
