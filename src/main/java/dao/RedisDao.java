package dao;

import redis.clients.jedis.Jedis;
import utils.RedisUtil;

public class RedisDao {
	static int  i = 0;
	public static void main(String[] args) {
		
		for (i = 0; i < 100; i++) {
			new Thread() {
				@Override
				public void run() {
					Jedis j = RedisUtil.getJedis();
					System.out.println(j);
					j.close();
					
				}
			}.start();
		}

	}

}
