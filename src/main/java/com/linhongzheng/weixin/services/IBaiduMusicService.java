package com.linhongzheng.weixin.services;

import com.linhongzheng.weixin.entity.message.response.Music;

 
public interface IBaiduMusicService {

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

	public abstract String getUsage();

}