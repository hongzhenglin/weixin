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
			if (StringUtils.isEmpty(propKey)) {
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
