package com.linhongzheng.weixin.services.impl;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by linhz on 2015/8/17.
 * 公众平台的API调用所需的access_token的使用及生成方式说明:
 * 1、为了保密appsecrect，第三方需要一个access_token获取和刷新的中控服务器。而其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，否则会造成access_token覆盖而影响业务；
 * 2、目前access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。中控服务器需要根据这个有效时间提前去刷新新access_token。在刷新过程中，中控服务器对外输出的依然是老access_token，此时公众平台后台会保证在刷新短时间内，新老access_token都可用，这保证了第三方业务的平滑过渡；
 * 3、access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，还需要提供被动刷新access_token的接口，这样便于业务服务器在API调用获知access_token已超时的情况下，可以触发access_token的刷新流程。
 * NOTE:如果第三方不使用中控服务器，而是选择各个业务逻辑点各自去刷新access_token，那么就可能会产生冲突，导致服务不稳定。
 */
public class AccessTokenService {

    protected  static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取access_token
     *
     * @return
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {
        String appid = ConfKit.get("AppId");
        String secret = ConfKit.get("AppSecret");
        String jsonStr = HttpKit.get(ACCESSTOKEN_URL.concat("&appid=") + appid + "&secret=" + secret);
        Map<String, Object> map = JSONObject.parseObject(jsonStr);
        return map.get("access_token").toString();
    }

    /**
     * 获取access_token
     *
     * @return
     * @throws Exception
     */
    public static String getAccessToken(String appid, String appsecret) throws Exception {
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace(
                "APPSECRET", appsecret);
        String jsonStr = HttpKit.get(requestUrl);
        Map<String, Object> map = JSONObject.parseObject(jsonStr);
        Object accessToken =  map.get("access_token");
        Object errCode=   map.get("errcode");

        if(accessToken == null &&errCode !=null){

        }else{
            return    accessToken.toString();
        }

    }
}
