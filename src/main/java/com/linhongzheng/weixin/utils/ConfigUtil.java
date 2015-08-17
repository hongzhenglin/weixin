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
    private Map<String, Properties> configProp = new HashMap<String, Properties>();

    /**
     * 配置文件名
     */
    private String cfgFile;

    /**
     * 前缀
     */
    private String pref;

    public ConfigUtil(String cfgFile, String pref) {
        this.cfgFile = cfgFile;
        this.pref = pref;
        init();
    }

    /**
     * 用配置文件初始化参数。
     */
    private void init() {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(this.cfgFile);
        try {
            Properties cfgs = new Properties();
            cfgs.load(in);

            // 初始化参数
            String cKey = null;
            String cValue = null;
            String groupName = null;
            String propKey = null;
            StringTokenizer st = null;
            int length = pref.length();
            for (Iterator<?> it = cfgs.entrySet().iterator(); it.hasNext();) {
                Entry<?, ?> e = (Entry<?, ?>) it.next();
                cKey = (String) e.getKey();
                cValue = (String) e.getValue();
                if (!cKey.startsWith(pref)) {
                    continue;
                }

                // 获取参数名
                cKey = cKey.substring(length);
                st = new StringTokenizer(cKey, ".");
                if (st.hasMoreTokens()) {
                    groupName = st.nextToken();
                }
                if (st.hasMoreTokens()) {
                    propKey = st.nextToken();
                }

                if (propKey != null) {
                    Properties conf;
                    if (configProp.containsKey(groupName)) {
                        conf = (Properties) configProp.get(groupName);
                    } else {
                        conf = new Properties();
                        configProp.put(groupName, conf);
                    }
                    conf.put(propKey, cValue);
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
     * @param groupName
     *            分组项
     * @param key
     * @return 参数值(string)
     */
    public String getValue(String groupName, String key) {
        Properties conf = configProp.get(groupName);
        if (conf != null) {
            return conf.getProperty(key);
        }
        return null;
    }

    /**
     * 取配置参数的值(int)
     *
     * @param groupName
     *            分组项
     * @param key
     *            键值
     * @return 参数值(int)
     */
    public int getIntValue(String groupName, String key) {
        return NumberUtils.toInt(getValue(groupName, key));
    }

    /**
     * 取配置参数的值(long)
     *
     * @param groupName
     *            分组项
     * @param key
     *            键值
     * @return 参数值(long)
     */
    public long getLongValue(String groupName, String key) {
        return NumberUtils.toLong(getValue(groupName, key));
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<?> it = configProp.entrySet().iterator(); it.hasNext();) {
            Entry<?, ?> entry = (Entry<?, ?>) it.next();
            Properties conf = (Properties) entry.getValue();

            String prefStr = pref + entry.getKey();
            for (Iterator<?> confIt = conf.entrySet().iterator(); confIt
                    .hasNext();) {
                Entry<?, ?> confEntry = (Entry<?, ?>) confIt.next();
                sb.append(prefStr).append(".").append(confEntry.getKey())
                        .append("=").append(confEntry.getValue())
                        .append("\r\n");
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }

    /**
     * 获取分组项的配置属性
     *
     * @param groupName
     *            分组项
     * @return 配置属性(Properties)
     */
    public Properties getProperties(String groupName) {
        return configProp.get(groupName);
    }

    /**
     * 获取所有分组项
     *
     * @return 所有分组项名
     */
    public Set<String> getAllGroupNames() {
        return configProp.keySet();
    }

}
