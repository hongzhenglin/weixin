package com.linhongzheng.weixin.services;

import java.util.Map;

/**
 * Created by linhz on 2015/8/18.
 */
 
public interface IEventMessageService {

    public abstract  String handleSubscribeEvent(Map<String, String> requestMap);

    public abstract  String handleUbsubscribeEvent(Map<String, String> requestMap);

    public abstract  String handleClientEvent(Map<String, String> requestMap);
}
