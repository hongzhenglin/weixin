package  com.linhongzheng.weixin.utils.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class RedisConfigUtil {

	private static Properties PROS = null;  
	private static List<Map<String, String>> hostList = new ArrayList<Map<String, String>>();
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String PASSWORD = "password";
	public static final String NO_CLUSTER = "nocluster";
	public static final String CLUSTER = "cluster";
	public static final String SHARDED = "sharded";
	public static final String SENTINEL = "sentinel";
	
	private static Properties getPros() {
		InputStream in = null;
		if (PROS == null) {
			PROS = new Properties();
			in = RedisConfigUtil.class.getResourceAsStream("/redis_config_default.properties");
			try {
				PROS.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return PROS;
	}
	
	public static String get(String key) {
		return getPros().getProperty(key);
	}
	
	public static List<Map<String, String>> getHostMap(){
		if(hostList.size() > 0){
			return hostList;
		}
		Properties prop = getPros();
		if(prop != null){
			Set set = prop.keySet();
			Iterator it = set.iterator();
			String hostkey = null;
			String index = null;
			String portkey = null;
			String passwordkey = null;
			while(it.hasNext()){
				hostkey = String.valueOf(it.next());
				if(hostkey.indexOf("host") != -1){
					index = hostkey.substring(4);
					portkey = "port" + index;
					passwordkey = "password" + index;
					Map<String, String> map = new HashMap<String, String>();
					map.put(RedisConfigUtil.HOST, get(hostkey));
					map.put(RedisConfigUtil.PORT, get(portkey));
					map.put(RedisConfigUtil.PASSWORD, get(passwordkey));
					hostList.add(map);
				}
			}
		}
		return hostList;
	}
	
}
