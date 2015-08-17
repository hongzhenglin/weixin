package com.linhongzheng.weixin.guice.modules;

import com.google.inject.Scopes;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.impl.MessageServiceImpl;
import com.linhongzheng.weixin.servlets.CoreServlet;
import com.google.inject.servlet.ServletModule;
import com.linhongzheng.weixin.services.IWeChatService;
import com.linhongzheng.weixin.services.impl.WeChatServiceImpl;

/**
 * Created by Administrator on 2015/8/17.
 */
public class MyServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve("*.html").with(CoreServlet.class);
        bind(IWeChatService.class).toProvider(WeChatServiceImpl.class).in(Scopes.SINGLETON);
        bind(IMessageService.class).toProvider(MessageServiceImpl.class).in(Scopes.SINGLETON);
    }
}

