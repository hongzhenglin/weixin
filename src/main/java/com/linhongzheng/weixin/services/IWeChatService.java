package com.linhongzheng.weixin.services;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.ImplementedBy;
import com.linhongzheng.weixin.services.impl.WeChatServiceImpl;

/**
 * Created by linhz on 2015/8/17.
 */

@ImplementedBy(WeChatServiceImpl.class)
public interface IWeChatService {
    public abstract   String processRequest(HttpServletRequest request);

}
