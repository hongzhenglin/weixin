package com.linhongzheng.weixin.services;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.linhongzheng.weixin.dao.BaseDAO;
import com.linhongzheng.weixin.entity.baidu.UserLocation;
import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.entity.message.response.NewsResponseMessage;

public interface IBaiduService {

	/**
	 * 根据名称和作者搜索音乐
	 * 
	 * @param musicTitle
	 *            音乐名称
	 * @param musicAuthor
	 *            音乐作者
	 * @return Music
	 */
	public abstract Music searchMusic(String musicTitle, String musicAuthor);

	public abstract void queryWeather(String cityName,
			NewsResponseMessage newsRespMessage);

	public abstract String translate(String source);

	public abstract String getMusicUsage();

	public abstract String getTranslateUsage();

	public abstract UserLocation getLastLocation(String openId);

	public abstract void saveUserLocation(UserLocation userLocation);

}