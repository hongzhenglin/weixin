package com.linhongzheng.weixin.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.linhongzheng.weixin.entity.message.MSG_TYPE;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.IWeChatService;
import com.linhongzheng.weixin.utils.message.MessageUtil;

/**
 * Created by linhz on 2015/8/17.
 */
@RequestScoped
public class WeChatServiceImpl extends AbstractWeChatService implements IWeChatService {
    
	@Inject
    IMessageService messageService;
	
	public WeChatServiceImpl(){
		messageService = new MessageServiceImpl();
    }
	
   
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get("MsgType");
            switch (MSG_TYPE.valueOf(msgType.toUpperCase())) {
                case TEXT:    // 文本消息
                    respMessage = messageService.handleTextMessage(requestMap);
                    break;
                case IMAGE:   // 图片消息
                    respMessage = messageService.handleImageMessage(requestMap);
                    break;
                case VOICE:  // 音频消息
                    respMessage = messageService.handleVoiceMessage(requestMap);
                    break;
                case VIDEO: //视频消息
                    respMessage = messageService.handleVideoMessage(requestMap);
                    break;
                case SHORTVIDEO: //小视频消息
                    respMessage = messageService.handleShortVideoMessage(requestMap);
                    break;
                case LOCATION:  // 地理位置消息
                    respMessage = messageService.handleLocationMessage(requestMap);
                    break;
                case LINK:  // 链接消息
                    respMessage = messageService.handleLinkMessage(requestMap);
                    break;
                case EVENT: //事件消息
                    respMessage = messageService.handleEventMessage(requestMap);
                    break;
                default:
                    respMessage = messageService.handleDefaultResp(requestMap);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

}
