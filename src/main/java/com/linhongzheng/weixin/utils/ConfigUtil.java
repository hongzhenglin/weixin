package com.linhongzheng.weixin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

/**
 * Created by linhz on 2015/8/17.
 */
public class ConfigUtil {
	/**
	 * 服务号相关信息
	 */
 
	public final static String TOKEN = "";// 服务号的配置token
	public final static String MCH_ID = "";// 商户号
	public final static String API_KEY = "";// API密钥
	public final static String SIGN_TYPE = "";// 签名加密方式
	public final static String CERT_PATH = "";// 微信支付证书存放路径地址
	// 微信支付统一接口的回调action
	public final static String NOTIFY_URL = "";
	// 微信支付成功支付后跳转的地址
	public final static String SUCCESS_URL = "";
	// oauth2授权时回调action
	public final static String REDIRECT_URI = "";
	

	public static Map<String, String> PropMap = new HashMap<String, String>();

	private static final String DEFAULT_CFG_FILE = "wechat.properties";

	/**
	 * 配置文件名
	 */
	private String cfgFile;

	public ConfigUtil() {
		this.cfgFile = DEFAULT_CFG_FILE;
		init();
	}

	/**
	 * 前缀
	 */
	private String pref;

	public ConfigUtil(String cfgFile) {
		this.cfgFile = cfgFile;

		init();
	}

	/**
	 * 用配置文件初始化参数。
	 */
	private void init() {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(cfgFile);
		try {
			Properties cfgs = new Properties();
			cfgs.load(in);

			// 初始化参数
			String cKey = null;
			String cValue = null;
			String groupName = null;
			String propKey = null;
			StringTokenizer st = null;
			if (StringUtil.isEmpty(propKey)) {
				for (Iterator<?> it = cfgs.entrySet().iterator(); it.hasNext();) {
					Entry<?, ?> e = (Entry<?, ?>) it.next();
					cKey = (String) e.getKey();
					cValue = (String) e.getValue();
					if (!PropMap.containsKey(cKey)) {
						PropMap.put(cKey, cValue);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 取配置参数的值
	 *
	 * @param key
	 * @return 参数值(string)
	 */
	public String getValue(String key) {
		String value = PropMap.get(key);
		if (value == null) {
			init();
			value = PropMap.get(key);
		}
		return value;
	}

}
