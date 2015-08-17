package com.linhongzheng.weixin.services.impl;

import com.linhongzheng.weixin.entity.message.event.EVENT_TYPE;
import com.linhongzheng.weixin.entity.message.request.REQ_MSG_TYPE;
import com.linhongzheng.weixin.entity.message.response.*;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.utils.StringUtils;
import com.linhongzheng.weixin.utils.message.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.linhongzheng.weixin.entity.message.request.REQ_MSG_TYPE.TEXT;

/**
 * Created by linhz on 2015/8/17.
 */

public class MessageServiceImpl extends AbstractWeChatService  implements IMessageService {


    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    public static NewsResponseMessage handleNewsMessage(String fromUserName, String toUserName, String content) {
        NewsResponseMessage newsMessage = new NewsResponseMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(RESP_MSG_TYPE.NEWS);

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
            article.setDescription("柳峰，80后，"
                    + emoji(0x1F6B9)
                    + "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
            // 将图片置为空
            article.setPicUrl("");
            article.setUrl("http://blog.csdn.net/lyq8479");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        // 多图文消息
        return newsMessage;
    }

    /**
     *
     * @param requestMap
     * @return
     */
    public   String handleImageMessage(    Map<String, String> requestMap){
        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 默认回复此文本消息
        ImageResponseMessage imageResponseMessage = createImageMessage(fromUserName, toUserName);
         Image image = createImage();
        imageResponseMessage.setImage(image );
        return   MessageUtil.imageMessageToXml(imageResponseMessage);

    }

    /**
     *
     * @param fromUserName
     * @param toUserName
     * @return
     */
    private static ImageResponseMessage createImageMessage(String fromUserName, String toUserName){
        ImageResponseMessage imageResponseMessage = new ImageResponseMessage();
        imageResponseMessage.setToUserName(fromUserName);
        imageResponseMessage.setFromUserName(toUserName);
        imageResponseMessage.setCreateTime(new Date().getTime());
        imageResponseMessage.setMsgType(RESP_MSG_TYPE.IMAGE.toString());
        return imageResponseMessage;
    }

    /**
     *
     * @param requestMap
     * @return
     */
    public static String handleDefaultResp(    Map<String, String> requestMap) {
        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 默认回复此文本消息
        TextResponseMessage textResponseMessage = createTextMessage(fromUserName, toUserName);


        // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
        StringBuffer contentMsg = new StringBuffer();
        contentMsg.append(
                "欢迎访问<a href=\"http://chatcourse.duapp.com\">个人主页</a>")
                .append("\n");
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
        return MessageUtil.textMessageToXml(textResponseMessage);

    }

    private static TextResponseMessage createTextMessage(String fromUserName, String toUserName) {
        TextResponseMessage textResponseMessage = new TextResponseMessage();
        textResponseMessage.setToUserName(fromUserName);
        textResponseMessage.setFromUserName(toUserName);
        textResponseMessage.setCreateTime(new Date().getTime());
        textResponseMessage.setMsgType(RESP_MSG_TYPE.TEXT.toString());
        return textResponseMessage;
    }
}
