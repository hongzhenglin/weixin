package com.linhongzheng.weixin.guice.modules;

import com.google.inject.servlet.ServletModule;
import com.linhongzheng.weixin.servlets.CoreServlet;


/**
 * Created by linhz on 2015/8/17.
 */
public class MyServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve("/coreServlet/*").with(CoreServlet.class);
        /*bind(IWeChatService.class).to( WeChatServiceImpl.class).in(Scopes.SINGLETON);
        bind(IMessageService.class).to(MessageServiceImpl.class).in(Scopes.SINGLETON);
        bind(IEventMessageService.class).to(EventMessageServiceImpl.class).in(Scopes.SINGLETON);*/
    }
}

