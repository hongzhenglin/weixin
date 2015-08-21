package  com.linhongzheng.weixin.utils.redis;

import com.linhongzheng.weixin.utils.redis.RedisConfigUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class RedisUtil {
	
	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;
	   
	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;
	    
	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;
	 
	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;
	  
	public static GenericObjectPoolConfig getGenericConfig(){
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
	    if(  RedisConfigUtil.get("max_active") != null && !"".equals(RedisConfigUtil.get("max_active"))){
	    	MAX_ACTIVE = Integer.parseInt(RedisConfigUtil.get("max_active"));
	    }
	    if(RedisConfigUtil.get("max_idle") != null && !"".equals(RedisConfigUtil.get("max_idle"))){
	    	MAX_IDLE = Integer.parseInt(RedisConfigUtil.get("max_idle"));
	    }
	    if(RedisConfigUtil.get("max_wait") != null && !"".equals(RedisConfigUtil.get("max_wait"))){
	    	MAX_WAIT = Integer.parseInt(RedisConfigUtil.get("max_wait"));
	    }
	    config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		return config;
	}
	

}
