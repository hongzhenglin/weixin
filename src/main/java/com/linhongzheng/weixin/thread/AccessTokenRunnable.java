package com.linhongzheng.weixin.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linhongzheng.weixin.services.impl.TokenServiceImpl;

/**
 * Created by Administrator on 2015/8/18.
 */
public class AccessTokenRunnable implements  Runnable{
	  private static Logger log = LoggerFactory.getLogger(AccessTokenRunnable.class);  
	    // 第三方用户唯一凭证  
	    public static String appid = "";  
	    // 第三方用户唯一凭证密钥  
	    public static String appsecret = "";  
	    public void run() {  }
	   /* public static AccessToken accessToken = null;  
	  
	    public void run() {  
	        while (true) {  
	            try {  
	                accessToken = AccessTokenService.getAccessToken(appid, appsecret);  
	                if (null != accessToken) {  
	                    log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());  
	                    // 休眠7000秒  
	                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);  
	                } else {  
	                    // 如果access_token为null，60秒后再获取  
	                    Thread.sleep(60 * 1000);  
	                }  
	            } catch (InterruptedException e) {  
	                try {  
	                    Thread.sleep(60 * 1000);  
	                } catch (InterruptedException e1) {  
	                    log.error("{}", e1);  
	                }  
	                log.error("{}", e);  
	            }  
	        }  
	    }  */
}
