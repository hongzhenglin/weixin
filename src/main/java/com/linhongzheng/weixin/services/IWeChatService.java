package com.linhongzheng.weixin.services;

import javax.servlet.http.HttpServletRequest;

public interface IWeChatService {
	public abstract String processRequestRaw(HttpServletRequest request)
			throws Exception;

	public abstract String processRequestCrypt(HttpServletRequest request)
			throws Exception;

}
