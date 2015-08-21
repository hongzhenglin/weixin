package com.linhongzheng.weixin.utils.redis;

import java.util.List;
import java.util.Map;

import com.linhongzheng.weixin.utils.redis.RedisConfigUtil;
import com.linhongzheng.weixin.utils.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class JedisPoolUtil {

	private static JedisPool jedisPool = null;
	private static int TIMEOUT = 10000;
	private static JedisPoolUtil jedisPoolUtil = null;
	
	private JedisPoolUtil(){};
	
	public synchronized static JedisPoolUtil getInstance(){
		if(jedisPoolUtil == null){
			jedisPoolUtil = new JedisPoolUtil();
			setJedisPool();
		}
		return jedisPoolUtil;
	}
	
	/**
	* 初始化Redis连接池
	*/
	private static void setJedisPool(){
		String addr = "132.126.2.29";
		int port = 6379;
		try {
			GenericObjectPoolConfig config =  RedisUtil.getGenericConfig();
			//获取ip、port
			List<Map<String, String>> list = RedisConfigUtil.getHostMap();
			if(list != null && list.size() > 0){
				Map<String, String> map = list.get(0);
				if(map != null){
					addr = map.get(RedisConfigUtil.HOST);
					port = Integer.parseInt(map.get(RedisConfigUtil.PORT));
				}
				if(StringUtils.isNotEmpty(map.get(RedisConfigUtil.PASSWORD))){
					jedisPool = new JedisPool(config, addr, port, TIMEOUT, map.get(RedisConfigUtil.PASSWORD));
				}else{
					jedisPool = new JedisPool(config, addr, port, TIMEOUT);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* 获取Jedis实例
	* @return
	*/
	public Jedis getJedis() {
		try {
			if (jedisPool == null) {
				setJedisPool();
			}
			Jedis resource = jedisPool.getResource();
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
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	* 释放jedis资源
	* @param jedis
	*/
	public void returnBrokenResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
}
