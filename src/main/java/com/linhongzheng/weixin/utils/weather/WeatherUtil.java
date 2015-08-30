package com.linhongzheng.weixin.utils.weather;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import com.linhongzheng.weixin.demo.weather.entity.WeatherResp;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.JSONUtil;
import com.linhongzheng.weixin.utils.URLConstants;

public class WeatherUtil {

	public String queryWeather(String cityName) {
		String url = URLConstants.BAIDU.BAIDU_WEATHER_URL.replace("{LOCATION}",
				CommonUtil.urlEncodeUTF8(cityName));
		ConfigUtil config = new ConfigUtil("baidu.properties");
		url = url.replace("{AK}", config.getValue("AK"));
		StringBuilder sb = new StringBuilder();
		try {
			String result = HttpUtil.get(url);
			WeatherResp weatherResp = JSONUtil.jsonToObject(result,
					WeatherResp.class);
		} catch (KeyManagementException | NoSuchAlgorithmException
				| NoSuchProviderException | IOException | ExecutionException
				| InterruptedException e) {

			e.printStackTrace();
		}
		return sb.toString();
	}
}
