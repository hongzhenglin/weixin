package com.linhongzheng.weixin.services;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/17.
 */
public interface IMessageService{
    public   abstract  String   handleImageMessage(  Map<String, String> requestMap  );
}
