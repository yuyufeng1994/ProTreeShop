package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static String host = null;
	private static Integer port = null;
	private static JedisPoolConfig config = new JedisPoolConfig();  
	static {
		Properties properties = new Properties();
		InputStream in = RedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			System.out.println("no redis.properties");
		}
		host = properties.getProperty("redis.host");
		port = Integer.valueOf(properties.getProperty("redis.port"));
		
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
        config.setMaxTotal(500);  
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
        config.setMaxIdle(5);  
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
        config.setMaxWaitMillis(1000 * 100);  
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
        config.setTestOnBorrow(true);  
		
	}
	private final static JedisPool jedisPool = new JedisPool(config,host, port);

	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	public static void release(Jedis j) {
		if (j != null) {
			try {
				j.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
