package com.linhongzheng.weixin.services;


 
public interface ITokenService {

	/**
	 * 获取access_token
	 *
	 * @return
	 * @throws Exception
	 */
	public abstract String getAccessToken() throws Exception;

	/**
	 * 获取access_token
	 *
	 * @return
	 * @throws Exception
	 */
	public abstract String getAccessToken(String appId, String appSecret)
			throws Exception;

}