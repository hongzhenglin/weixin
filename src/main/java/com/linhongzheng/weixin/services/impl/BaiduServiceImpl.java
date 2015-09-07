package com.linhongzheng.weixin.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.linhongzheng.weixin.demo.weather.entity.WeatherData;
import com.linhongzheng.weixin.demo.weather.entity.WeatherResp;
import com.linhongzheng.weixin.demo.weather.entity.WeatherResult;
import com.linhongzheng.weixin.entity.baidu.TranslateResult;
import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IBaiduService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.Dom4jUtil;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.JSONUtil;
import com.linhongzheng.weixin.utils.URLConstants;
import com.linhongzheng.weixin.utils.message.MessageUtil;

@Service("baiduMusicService")
public class BaiduServiceImpl extends AbstractWeChatService implements
		IBaiduService {
	/**
	 * 翻译（中->英 英->中 日->中 ）
	 * 
	 * @param source
	 * @return
	 */
	@Override
	public String translate(String source) {
		String dst = null;

		// 组装查询地址
		String requestUrl = URLConstants.BAIDU.BAIDU_TRANSLATE_URL;
		ConfigUtil config = new ConfigUtil("baidu.properties");
		requestUrl = requestUrl.replace("{AK}", config.getValue("AK"));
		// 对参数q的值进行urlEncode utf-8编码
		requestUrl = requestUrl.replace("{keyWord}",
				CommonUtil.urlEncodeUTF8(source));

		// 查询并解析结果
		try {
			// 查询并获取返回结果
			String json = HttpUtil.get(requestUrl);

			TranslateResult translateResult = JSONUtil.jsonToObject(json,
					TranslateResult.class);
			// 取出translateResult中的译文
			dst = translateResult.getTrans_result().get(0).getDst();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == dst)
			dst = "翻译系统异常，请稍候尝试！";
		return dst;
	}

	@Override
	public void queryWeather(String cityName,
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
	private String queryWeather(String cityName) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linhongzheng.weixin.services.IBaiduService#searchMusic(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public Music searchMusic(String musicTitle, String musicAuthor) {
		String requestUrl = URLConstants.BAIDU.BAIDU_MUSIC_URL;
		// 对音乐名称、作者进URL编码
		requestUrl = requestUrl.replace("{TITLE}",
				CommonUtil.urlEncodeUTF8(musicTitle));
		requestUrl = requestUrl.replace("{AUTHOR}",
				CommonUtil.urlEncodeUTF8(musicAuthor));
		// 处理名称、作者中间的空格
		requestUrl = requestUrl.replaceAll("\\+", "%20");

		// 查询并获取返回结果
		String sourceText = null;
		try {
			sourceText = HttpUtil.get(requestUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 从返回结果中解析出Music
		Music music = parseMusic(sourceText);

		// 如果music不为null，设置标题和描述
		if (null != music) {
			music.setTitle(musicTitle);
			// 如果作者不为""，将描述设置为作者
			if (!"".equals(musicAuthor))
				music.setDescription(musicAuthor);
			else
				music.setDescription("来自百度音乐");
		}

		return music;
	}

	/**
	 * 歌曲点播使用指南
	 * 
	 * @return
	 */
	@Override
	public String getMusicUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("歌曲点播操作指南").append("\n\n");
		buffer.append("回复：歌曲+歌名").append("\n");
		buffer.append("例如：歌曲存在").append("\n");
		buffer.append("或者：歌曲存在@汪峰").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}

	@Override
	public String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(MessageUtil.emoji(0xe148)).append("Q译通使用指南")
				.append("\n\n");
		buffer.append("Q译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    中 -> 英").append("\n");
		buffer.append("    英 -> 中").append("\n");
		buffer.append("    日 -> 中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}

	private WeatherResp queryWeatherResp(String cityName) {
		String url = URLConstants.BAIDU.BAIDU_WEATHER_URL.replace("{LOCATION}",
				CommonUtil.urlEncodeUTF8(cityName));
		ConfigUtil config = new ConfigUtil("baidu.properties");
		url = url.replace("{AK}", config.getValue("AK"));

		WeatherResp weatherResp = null;
		try {
			String result = HttpUtil.get(url);
			weatherResp = JSONUtil.jsonToObject(result, WeatherResp.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return weatherResp;
	}

	/**
	 * 
	 * @param picUrl
	 * @return
	 */
	private String covertPicUrl(String picUrl) {
		return "/image/weather/weather_"
				+ picUrl.substring(picUrl.lastIndexOf("/") + 1);
	}

	/**
	 * 解析音乐参数
	 * 
	 * @param inputStream
	 *            百度音乐搜索API返回的输入流
	 * @return Music
	 */
	@SuppressWarnings("unchecked")
	private static Music parseMusic(String inputText) {
		Music music = null;
		try {

			Document document = Dom4jUtil.parseText(inputText);
			// 得到xml根元素
			Element root = document.getRootElement();
			// count表示搜索到的歌曲数
			String count = root.element("count").getText();
			// 当搜索到的歌曲数大于0时
			if (!"0".equals(count)) {
				// 普通品质
				List<Element> urlList = root.elements("url");
				// 高品质
				List<Element> durlList = root.elements("durl");

				// 普通品质的encode、decode
				String urlEncode = urlList.get(0).element("encode").getText();
				String urlDecode = urlList.get(0).element("decode").getText();
				// 普通品质音乐的URL
				String url = urlEncode.substring(0,
						urlEncode.lastIndexOf("/") + 1) + urlDecode;
				if (-1 != urlDecode.lastIndexOf("&"))
					url = urlEncode
							.substring(0, urlEncode.lastIndexOf("/") + 1)
							+ urlDecode
									.substring(0, urlDecode.lastIndexOf("&"));

				// 默认情况下，高音质音乐的URL 等于 普通品质音乐的URL
				String durl = url;

				// 判断高品质节点是否存在
				Element durlElement = durlList.get(0).element("encode");
				if (null != durlElement) {
					// 高品质的encode、decode
					String durlEncode = durlList.get(0).element("encode")
							.getText();
					String durlDecode = durlList.get(0).element("decode")
							.getText();
					// 高品质音乐的URL
					durl = durlEncode.substring(0,
							durlEncode.lastIndexOf("/") + 1) + durlDecode;
					if (-1 != durlDecode.lastIndexOf("&"))
						durl = durlEncode.substring(0,
								durlEncode.lastIndexOf("/") + 1)
								+ durlDecode.substring(0,
										durlDecode.lastIndexOf("&"));
				}
				music = new Music();
				// 设置普通品质音乐链接
				music.setMusicUrl(url);
				// 设置高品质音乐链接
				music.setHQMusicUrl(durl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return music;
	}

}
