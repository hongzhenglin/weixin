package com.linhongzheng.weixin.entity.message;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.linhongzheng.weixin.guice.modules.MyTestModule;
import com.linhongzheng.weixin.services.impl.WeChatServiceImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linhz on 2015/8/19.
 */
public class GuiceTest extends TestCase{
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceTest.class);
    @Test
    public void testGuice() {
        MyTestModule module = new MyTestModule();// 定义注射规则
        Injector injector = Guice.createInjector(module);// 根据注射规则，生成注射者
        WeChatServiceTestImpl weChatServiceImpl = new WeChatServiceTestImpl();
        injector.injectMembers(weChatServiceImpl);// 注射者将需要注射的bean,按照规则,把weChatServiceImpl这个客户端进行注射
        LOGGER.info(weChatServiceImpl.processRequest());
    }
}
