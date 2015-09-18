package com.linhongzheng.weixin.message;

import com.linhongzheng.weixin.services.ITokenService;
import com.linhongzheng.weixin.services.impl.TokenServiceImpl;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by linhz on 2015/8/19.
 */
public class AccessTokenTest extends TestCase{

    @Test
    public void testGetAccessToken(){
        ITokenService accessTokenService = new TokenServiceImpl();
        try {
            System.out.println(accessTokenService.getAccessToken("wx890eefa456e4c786", "b0aad21044c6b2b302f9722ab6145eab"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
