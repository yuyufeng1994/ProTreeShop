package dao;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.RedisUtil;
import utils.SerializeUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class RedisTest {
	private Jedis jedis = RedisUtil.getJedis();

	@Test
	public void setValue() {

		User user = new User();
		user.setUserName("语言");
		user.setUserCreateTime(new Date());
		user.setUserPwd("yyf12345");
		String uuid = UUID.randomUUID().toString();
		jedis.setex(uuid.getBytes(),1000,SerializeUtil.serialize(user));
		System.out.println(uuid);

		/**
		 * byte[] person = jedis.get(("person:" + id).getBytes()); return
		 * (Person) SerializeUtil.unserialize(person);
		 * 326be9fb-6b7b-468f-bf51-a89c08c6b870
		 */

	}
	
	@Test
	public void getValue() {
		byte[] userb = jedis.get(("cb18e96d-d497-4493-8731-3579916b5f4b").getBytes());
		User user = (User) SerializeUtil.unserialize(userb);
		System.out.println(user);
	}
	
	@Test
	public void deleteValue() {
		jedis.del(("326be9fb-6b7b-468f-bf51-a89c08c6b870").getBytes());
	}
	
	@Test
	public void Ping() {
		System.out.println(jedis.ping());
	}
	
	public static void main(String[] args) {
		//192.168.127.101
		Jedis jedis = new Jedis("172.16.21.6",6379);
		System.out.println(jedis.ping());
//		JedisPool jedisPool = new JedisPool("172.16.21.6", 6379);
//		System.out.println(jedisPool.getResource().ping());
	}

}
