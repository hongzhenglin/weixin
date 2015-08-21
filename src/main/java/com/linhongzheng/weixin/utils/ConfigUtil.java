package com.linhongzheng.weixin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by linhz on 2015/8/17.
 */
public class ConfigUtil {


    public  static Map<String, String> PropMap = new HashMap<String, String>();

    private static final String DEFAULT_CFG_FILE = "wechat.properties";

    /**
     * 配置文件名
     */
    private String cfgFile;

    static {

        init();


    }

    /**
     * 前缀
     */
    private String pref;

    public ConfigUtil() {
        this(null);
    }

    public ConfigUtil(String pref) {
        this(DEFAULT_CFG_FILE, pref);
    }

    public ConfigUtil(String cfgFile, String pref) {
        this.cfgFile = cfgFile;
        this.pref = pref;
        init();
    }

    /**
     * 用配置文件初始化参数。
     */
    private static void init() {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(DEFAULT_CFG_FILE);
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
                for (Iterator<?> it = cfgs.entrySet().iterator(); it.hasNext(); ) {
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
