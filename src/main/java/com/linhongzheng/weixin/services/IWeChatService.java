package com.linhongzheng.weixin.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

public interface IWeChatService {
	public abstract String processRequestRaw(HttpServletRequest request)
			throws Exception;

	public abstract String processRequestCrypt(HttpServletRequest request)
			throws Exception;

	public abstract List<String> getServerIP(String accessToken);

	public abstract String getShortUrl(String accessToken, String longUrl);

}
