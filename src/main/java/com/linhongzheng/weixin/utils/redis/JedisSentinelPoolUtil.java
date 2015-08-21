package com.linhongzheng.weixin.utils.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.linhongzheng.weixin.utils.redis.RedisConfigUtil;
import com.linhongzheng.weixin.utils.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisSentinelPoolUtil {

	private static boolean ifThreadStart = false; //标示线程是否启动
	private static boolean ifReadNormal = true; //标示读数据是否正常
	private ScheduledExecutorService listenMaster = Executors.newScheduledThreadPool(1);  
	private static HostAndPort currentMaster = null;  //记录当前master
	private static String masterIp = null;
	private static String masterPort = null;
	private static JedisSentinelPoolUtil jedisSentinelPoolUtil = null;
	private static JedisSentinelPool sentinelPool = null;
	private static ShardedJedisPool shardedPool = null;
	private static String password = null;
	
	private JedisSentinelPoolUtil(){};
	
	public synchronized static JedisSentinelPoolUtil getInstance(){
		if(jedisSentinelPoolUtil == null){
			jedisSentinelPoolUtil = new JedisSentinelPoolUtil();
			setSentinelPool();
		}
		return jedisSentinelPoolUtil;
	}
	
	/**
	 * 初始化JedisSentinelPool
	 * @return
	 */
	private static void setSentinelPool(){
		Set<String> sentinels = new HashSet<String>();
		try {
			List<Map<String, String>> list = RedisConfigUtil.getHostMap();
			if(list != null && list.size() > 0){
				Map<String, String> map = null;
				for(int i = 0; i < list.size(); i++){
					map = list.get(i);
					if(map != null){
						sentinels.add(map.get(RedisConfigUtil.HOST) + ":" + Integer.parseInt(map.get(RedisConfigUtil.PORT)));
						password = map.get(RedisConfigUtil.PASSWORD);
					}
				}
			}
			GenericObjectPoolConfig config = RedisUtil.getGenericConfig();
			if(StringUtils.isNotEmpty(password)){
				sentinelPool = new JedisSentinelPool(RedisConfigUtil.get("master_name"), sentinels, config, 3000, password);
			}else{
				sentinelPool = new JedisSentinelPool(RedisConfigUtil.get("master_name"), sentinels, config, 3000);
			}
			currentMaster = sentinelPool.getCurrentHostMaster();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	* 获取Jedis实例
	* @return
	*/
	public Jedis getSentinelWriteJedis() {
		try {
			if (sentinelPool == null) {
				setSentinelPool();
			}
			if(!ifThreadStart){
				ifThreadStart = true;
				listenMaster.scheduleAtFixedRate(new ListenMasterThread(), 50, 1000*30, TimeUnit.MILLISECONDS);
			}
			Jedis resource = sentinelPool.getResource();
			return resource;
		} catch (Exception e) {
			 e.printStackTrace();
			 try{
				 sentinelPool.destroy();
				 sentinelPool.close();
			 }catch(Exception el){
			 }finally{
				 masterIp = null;
			     masterPort = null;
				 sentinelPool = null;
			 }
			 return null;
		}
	}
	
	/**
	 * 设置读取redis数据实例
	 */
	private void setShardedJedisPool() {
		try {
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			Jedis j = new Jedis(masterPort, Integer.parseInt(masterPort));
			List<Map<String, String>> slaves = j.sentinelSlaves(RedisConfigUtil.get("master_name"));
		    if(slaves.size() > 0){
		    	Map<String, String> map = null;
		    	for(int i = 0; i < slaves.size(); i++){
		    		map = slaves.get(i);
		    		if(map != null){
						JedisShardInfo node = new JedisShardInfo(map.get("ip"), Integer.parseInt(map.get("port")));
						if(StringUtils.isNotEmpty(password)){
							node.setPassword(password);
						}
						shards.add(node);
					}
		    	}
		    }
			GenericObjectPoolConfig config = RedisUtil.getGenericConfig();
			shardedPool = new ShardedJedisPool(config, shards);
		} catch (Exception e) {
			 e.printStackTrace();
		}finally{
			ifReadNormal = true;
		}
	} 
	
	/**
	* 释放jedis资源
	* @param jedis
	*/
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			sentinelPool.returnResource(jedis);
		}
	}
	
	/**
	* 释放jedis资源
	* @param jedis
	*/
	public void returnBrokenResource(final Jedis jedis) {
		if (jedis != null) {
			sentinelPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 监听主从redis是否正常，若出现异常，重新设置读写redis实例
	 * @author LAIYI
	 *
	 */
	private class ListenMasterThread implements Runnable {
		public void run(){
			if(masterIp == null || masterPort == null){
				setSentinelAddr();
			}
			if(sentinelPool != null){
				HostAndPort master = sentinelPool.getCurrentHostMaster();
				if(master.equals(currentMaster) && ifReadNormal){  //如果现在取到的master和之前设置的master相同，且读数据正常，则不做处理
				}else{  //重新获取读数据实例
					try{
						currentMaster = master;  //重新设置currentMaster
						if(shardedPool != null){
							shardedPool.destroy();
							shardedPool.close();
							shardedPool = null;
						}
					}catch(Exception e){
					}finally{
						setShardedJedisPool();
					}
				}
			}
		}
	}
		
	private HostAndPort toHostAndPort(List<String> getMasterAddrByNameResult) {
	    String host = getMasterAddrByNameResult.get(0);
	    int port = Integer.parseInt(getMasterAddrByNameResult.get(1));
	    return new HostAndPort(host, port);
	}
	
	/**
	 * 获取sentinel的ip和port
	 */
	private void setSentinelAddr(){
//		HostAndPort master = null;
		boolean sentinelAvailable = false;
		List<Map<String, String>> list = RedisConfigUtil.getHostMap();
		if(list != null && list.size() > 0){
			Map<String, String> map = null;
			for(int i = 0; i < list.size(); i++){
				map = list.get(i);
				if(map != null){
					final HostAndPort hap = new HostAndPort(map.get(RedisConfigUtil.HOST), Integer.parseInt(map.get(RedisConfigUtil.PORT)));
					Jedis jedis = null;
				      try {
				        jedis = new Jedis(hap.getHost(), hap.getPort());
				        List<String> masterAddr = jedis.sentinelGetMasterAddrByName(RedisConfigUtil.get("master_name"));
				        sentinelAvailable = true;
				        if (masterAddr == null || masterAddr.size() != 2) {
				        	continue;
				        }
//				        master = toHostAndPort(masterAddr);
				        masterIp = map.get(RedisConfigUtil.HOST);
				        masterPort = map.get(RedisConfigUtil.PORT);
				        break;
				      } catch (JedisConnectionException e) {
				    	  e.printStackTrace();
				      } finally {
				        if (jedis != null) {
				          jedis.close();
				        }
				     }
				}
			}
		}
//		if (master == null) {
//		      if (sentinelAvailable) {
//		        throw new JedisException("Can connect to sentinel");
//		      } else {
//		        throw new JedisConnectionException("All sentinels down, cannot determine where master is running...");
//		      }
//		}else{
			if(shardedPool == null){
				setShardedJedisPool();  //设置读取数据	
			}
//		}
	}
	
	public Object hmget(final String key, final String field){
		Object value = null;
		ShardedJedis jedis = null;
		try{
			if(shardedPool != null){
				jedis = shardedPool.getResource();
				List<String> list = jedis.hmget(key, field);
				if(list != null && list.size() > 0){
					if(list.get(0) == null){
						ifReadNormal = false;
					}else{
						value =  com.linhongzheng.weixin.utils.SerializeUtil.hessianUnserialize(list.get(0));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			ifReadNormal = false;
			shardedPool.returnBrokenResource(jedis);
		}finally{
			shardedPool.returnResource(jedis);
		}
		
		if(!ifReadNormal){
			Jedis sentineljedis = getSentinelWriteJedis();
			try{
				List<String> list = sentineljedis.hmget(key, field);
				if(list != null && list.size() > 0){
					if(list.get(0) == null){
						return null;
					}
					value = com.linhongzheng.weixin.utils.SerializeUtil.hessianUnserialize(list.get(0));
				}
			}catch(Exception e){
				e.printStackTrace();
				returnBrokenResource(sentineljedis);
				return null;
			}finally{
				returnResource(sentineljedis);
			}
		}
		return value;
	}
	
	
}
