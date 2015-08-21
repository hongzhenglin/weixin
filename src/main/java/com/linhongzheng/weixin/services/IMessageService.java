package com.linhongzheng.weixin.services;

import java.util.Map;

import com.google.inject.ImplementedBy;
import com.linhongzheng.weixin.services.impl.MessageServiceImpl;

/**
 * Created by Administrator on 2015/8/17.
 */
@ImplementedBy(MessageServiceImpl.class)
public interface IMessageService {
    public abstract String handleTextMessage(Map<String, String> requestMap);

    public abstract String handleImageMessage(Map<String, String> requestMap);

    public abstract String handleVoiceMessage(Map<String, String> requestMap);

    public abstract String handleVideoMessage(Map<String, String> requestMap);

    public abstract String handleLinkMessage(Map<String, String> requestMap);

    public abstract String handleLocationMessage(Map<String, String> requestMap);

    public  abstract  String handleShortVideoMessage(Map<String,String> requestMap);

    public abstract String handleEventMessage(Map<String,String> requestMap);

    public abstract  String  handleNewsMessage(Map<String, String> requestMap);

    public abstract String handleDefaultResp(Map<String, String> requestMap);
}
