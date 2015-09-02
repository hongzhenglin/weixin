package com.linhongzheng.weixin.utils.weather;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.linhongzheng.weixin.demo.weather.entity.WeatherData;
import com.linhongzheng.weixin.demo.weather.entity.WeatherResp;
import com.linhongzheng.weixin.demo.weather.entity.WeatherResult;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.JSONUtil;
import com.linhongzheng.weixin.utils.URLConstants;

public class WeatherUtil {

	public static void queryWeather(String cityName,
			NewsResponseMessage newsRespMessage) {
		ConfigUtil configUtil = new ConfigUtil("host.properties");
		String hostUrl = configUtil.getValue("LOCALHOST_URL");
		WeatherResp weatherResp = queryWeatherResp(cityName);
		if (weatherResp != null && weatherResp.getError() == 0) {
			List<Article> articleList = new ArrayList<Article>();
			List<WeatherResult> weatherResults = weatherResp.getResults();
			List<WeatherData> weatherDatas = weatherResults.get(0)
					.getWeather_data();

			Article article = new Article();
			WeatherData todayWeatherData = weatherDatas.get(0);
			article.setTitle(todayWeatherData.getDate() + " "
					+ todayWeatherData.getWeather() + " "
					+ todayWeatherData.getWind());
			article.setDescription("");
			article.setPicUrl(hostUrl + "/image/weather/top.jpg");

			articleList.add(article);
			weatherDatas.remove(0);
			// 未来几天的天气情况
			for (WeatherData weatherData : weatherDatas) {
				Article article1 = new Article();
				String title = weatherData.getDate() + " "
						+ weatherData.getWeather() + " "
						+ weatherData.getWind() + " "
						+ weatherData.getTemperature();

				article1.setTitle(title);
				article1.setDescription("");
				article1.setPicUrl(hostUrl
						+ covertPicUrl(weatherData.getDayPictureUrl()));
				article1.setUrl("");
				articleList.add(article1);

			}
			// 设置图文消息个数
			newsRespMessage.setArticleCount(articleList.size());
			// 设置图文消息包含的图文集合
			newsRespMessage.setArticles(articleList);
		}
	}

	/**
	 * 
	 * @param cityName
	 * @return
	 */
	public static String queryWeather(String cityName) {
		WeatherResp weatherResp = queryWeatherResp(cityName);
		StringBuilder sb = new StringBuilder();
		if (weatherResp != null && weatherResp.getError() == 0) {
			sb.append("时间：").append(weatherResp.getDate()).append("\n\n");
			List<WeatherResult> weatherResults = weatherResp.getResults();
			for (WeatherResult weatherResult : weatherResults) {
				sb.append(" 城市:").append(weatherResult.getCurrentCity());
				if (StringUtils.isNotEmpty(weatherResult.getPm25())) {
					sb.append(",pm2.5值：").append(weatherResult.getPm25())
							.append(" \n");
				}
				List<WeatherData> weatherDatas = weatherResult
						.getWeather_data();
				if (!CollectionUtils.isEmpty(weatherDatas)) {
					sb.append("未来").append(weatherDatas.size())
							.append("天天气情况：\n");
					for (WeatherData weatherData : weatherDatas) {
						sb.append("日期：").append(weatherData.getDate())
								.append(",天气：")
								.append(weatherData.getWeather())
								.append(",风向：").append(weatherData.getWind())
								.append(",温度：")
								.append(weatherData.getTemperature())
								.append(";\n");

					}
				}
			}
		}

		return sb.toString();
	}

	private static WeatherResp queryWeatherResp(String cityName) {
		String url = URLConstants.BAIDU.BAIDU_WEATHER_URL.replace("{LOCATION}",
				CommonUtil.urlEncodeUTF8(cityName));
		ConfigUtil config = new ConfigUtil("baidu.properties");
		url = url.replace("{AK}", config.getValue("AK"));

		WeatherResp weatherResp = null;
		try {
			String result = HttpUtil.get(url);
			weatherResp = JSONUtil.jsonToObject(result, WeatherResp.class);
		} catch (KeyManagementException | NoSuchAlgorithmException
				| NoSuchProviderException | IOException | ExecutionException
				| InterruptedException e) {

			e.printStackTrace();
		}
		return weatherResp;
	}

	/**
	 * 
	 * @param picUrl
	 * @return
	 */
	private static String covertPicUrl(String picUrl) {
		return "/image/weather/weather_"
				+ picUrl.substring(picUrl.lastIndexOf("/") + 1);
	}
}
