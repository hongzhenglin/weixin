package com.linhongzheng.weixin.services.impl;

import com.google.inject.Inject;
import com.linhongzheng.weixin.entity.message.event.EVENT_TYPE;
import com.linhongzheng.weixin.entity.message.request.REQ_MSG_TYPE;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.utils.message.MessageUtil;
import  com.linhongzheng.weixin.services.IWeChatService;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by linhz on 2015/8/17.
 */
public class WeChatServiceImpl implements  IWeChatService {
    @Inject
    IMessageService messageService;

    public   String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 文本消息
            if (msgType.equals(REQ_MSG_TYPE.TEXT.toString())) {
                // 接收用户发送的文本消息内容
                String content = requestMap.get("Content");
                //非全文匹配

            }
            // 图片消息
            else if (msgType.equals(REQ_MSG_TYPE.IMAGE)) {
                return   messageService.handleImageMessage(requestMap);
            }
            // 地理位置消息
            else if (msgType.equals(REQ_MSG_TYPE.LOCATION)) {
                respMessage = "您发送的是地理位置消息！";
                textMessage.setContent(respMessage);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 链接消息
            else if (msgType.equals(REQ_MSG_TYPE.LINK)) {
                respMessage = "您发送的是链接消息！";
                textMessage.setContent(respMessage);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 音频消息
            else if (msgType.equals(REQ_MSG_TYPE.VOICE)) {
                respMessage = "您发送的是音频消息！";
                textMessage.setContent(respMessage);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            } else if (msgType.equals(REQ_MSG_TYPE.EVENT)) {
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(EVENT_TYPE.SUBSCRIBE)) {
                    respMessage = "谢谢您的关注！";
                    textMessage.setContent(respMessage);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                // 取消订阅
                else if (eventType.equals(EVENT_TYPE.UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");

                    if (eventKey.equals("11")) {
                        respMessage = "天气预报菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("12")) {
                        respMessage = "公交查询菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("13")) {
                        respMessage = "周边搜索菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("14")) {
                        respMessage = "历史上的今天菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("21")) {
                        respMessage = "歌曲点播菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("22")) {
                        respMessage = "经典游戏菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("23")) {
                        respMessage = "美女电台菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("24")) {
                        respMessage = "人脸识别菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("25")) {
                        respMessage = "聊天唠嗑菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("31")) {
                        respMessage = "Q友圈菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("32")) {
                        respMessage = "电影排行榜菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("33")) {
                        respMessage = "幽默笑话菜单项被点击！";
                        textMessage.setContent(respMessage);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    }
                }
            } else {
                respMessage = handleDefaultResp(fromUserName, toUserName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }

}
