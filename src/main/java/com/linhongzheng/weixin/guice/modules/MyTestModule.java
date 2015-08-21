package com.linhongzheng.weixin.guice.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletScopes;
import com.linhongzheng.weixin.services.IEventMessageService;
import com.linhongzheng.weixin.services.IMessageService;
import com.linhongzheng.weixin.services.IWeChatService;
import com.linhongzheng.weixin.services.impl.EventMessageServiceImpl;
import com.linhongzheng.weixin.services.impl.MessageServiceImpl;
import com.linhongzheng.weixin.services.impl.WeChatServiceImpl;

/**
 * Created by linhz on 2015/8/19.
 */
public class MyTestModule implements Module {
    public void configure(Binder binder) {
        binder. bind(IWeChatService.class).to(WeChatServiceImpl.class).in(Scopes.SINGLETON);
        binder.bind(IMessageService.class).to(MessageServiceImpl.class).in(Scopes.SINGLETON);
        binder. bind(IEventMessageService.class).to( EventMessageServiceImpl.class).in(Scopes.SINGLETON);
    }

}
