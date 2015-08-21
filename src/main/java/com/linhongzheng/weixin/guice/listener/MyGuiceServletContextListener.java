package com.linhongzheng.weixin.guice.listener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.linhongzheng.weixin.guice.modules.MyServletModule;

/**
 * Created by linhz on 2015/8/17.
 */
public class MyGuiceServletContextListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
         return Guice.createInjector(new MyServletModule());
    }
}
