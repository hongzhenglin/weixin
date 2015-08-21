package  com.linhongzheng.weixin.utils.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.linhongzheng.weixin.utils.redis.RedisConfigUtil;
import com.linhongzheng.weixin.utils.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisShardedPoolUtil {

	private static ShardedJedisPool shardedPool = null;
	private static JedisShardedPoolUtil jedisShardedPoolUtil = null;
	
	private JedisShardedPoolUtil(){};
	
	public synchronized static JedisShardedPoolUtil getInstance(){
		if(jedisShardedPoolUtil == null){
			jedisShardedPoolUtil = new JedisShardedPoolUtil();
			setShardedJedisPool();
		}
		return jedisShardedPoolUtil;
	}
	
	/**
	* 初始化ShardedJedisPool
	* @return
	*/
	private static void setShardedJedisPool() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		try {
			List<Map<String, String>> list = RedisConfigUtil.getHostMap();
			if(list != null && list.size() > 0){
				Map<String, String> map = null;
				for(int i = 0; i < list.size(); i++){
					map = list.get(i);
					if(map != null){
						JedisShardInfo node = new JedisShardInfo(map.get(RedisConfigUtil.HOST), Integer.parseInt(map.get(RedisConfigUtil.PORT)));
						if(StringUtils.isNotEmpty(map.get(RedisConfigUtil.PASSWORD))){
							node.setPassword(map.get(RedisConfigUtil.PASSWORD));
						}
						shards.add(node);
					}
				}
			}
			GenericObjectPoolConfig config = RedisUtil.getGenericConfig();
			shardedPool = new ShardedJedisPool(config, shards);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	} 
	
	/**
	* 获取Jedis实例
	* @return
	*/
	public ShardedJedis getShardedJedis() {
		try {
			if (shardedPool == null) {
				setShardedJedisPool();
			}
			ShardedJedis resource = shardedPool.getResource();
			return resource;
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}
	
	/**
	* 释放jedis资源
	* @param jedis
	*/
	public void returnResource(final ShardedJedis jedis) {
		if (jedis != null) {
			shardedPool.returnResourceObject(jedis);
		}
	}
	
	/**
	* 释放jedis资源
	* @param jedis
	*/
	public void returnBrokenResource(final ShardedJedis jedis) {
		if (jedis != null) {
			shardedPool.returnResourceObject(jedis);
		}
	}
	
}
