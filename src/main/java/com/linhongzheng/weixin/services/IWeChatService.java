package com.linhongzheng.weixin.services;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by linhz on 2015/8/17.
 */

public interface IWeChatService {
    public abstract   String processRequest(HttpServletRequest request);
}
