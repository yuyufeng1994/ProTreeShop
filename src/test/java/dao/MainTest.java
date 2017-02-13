package dao;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class MainTest {
	@Autowired
	private DataSource dataSource;

	@Test
	public void testGataSource() {
		System.out.println(dataSource);
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis("192.168.127.101", 6379);
		System.out.println(jedis.ping());
	}
}
