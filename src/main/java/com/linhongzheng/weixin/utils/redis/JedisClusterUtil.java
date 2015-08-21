package  com.linhongzheng.weixin.utils.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.linhongzheng.weixin.utils.redis.RedisConfigUtil;
import com.linhongzheng.weixin.utils.redis.RedisUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;



import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterUtil {

	private static JedisCluster cluster = null;
	private static JedisClusterUtil jedisClusterUtil = null;
	
	private JedisClusterUtil(){};
	
	public synchronized static JedisClusterUtil getInstance(){
		if(jedisClusterUtil == null){
			jedisClusterUtil = new JedisClusterUtil();
			setCluster();
		}
		return jedisClusterUtil;
	}
	
	/**
	* 获取JedisCluster实例
	* @return
	*/
	public JedisCluster getJedisCluster() {
		try {
			if(cluster == null){
				setCluster();
			}
			return cluster;
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}
	
	/**
	* 初始化JedisCluster
	* @return
	*/
	private static void setCluster() {
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		try {
			List<Map<String, String>> list = RedisConfigUtil.getHostMap();
			if(list != null && list.size() > 0){
				Map<String, String> map = null;
				for(int i = 0; i < list.size(); i++){
					map = list.get(i);
					if(map != null){
						HostAndPort node = new HostAndPort(map.get(RedisConfigUtil.HOST), Integer.parseInt(map.get(RedisConfigUtil.PORT)));
						jedisClusterNode.add(node);
					}
				}
			}
		    GenericObjectPoolConfig config = RedisUtil.getGenericConfig();
		    cluster = new JedisCluster(jedisClusterNode, config);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	} 
	
}
