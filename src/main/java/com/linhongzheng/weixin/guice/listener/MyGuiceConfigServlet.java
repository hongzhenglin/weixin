package com.linhongzheng.weixin.guice.listener;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

/**
 * Created by Administrator on 2015/8/17.
 */
@WebListener
public class MyGuiceConfigServlet extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
         return Guice.createInjector(new MyServletModule());
    }
}
