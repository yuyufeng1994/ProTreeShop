package dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.User;
import redis.clients.jedis.Jedis;
import service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class UserDaoTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService service;

	@Test
	public void testSave() {
		User user = new User();
		user.setUserName("test用户1");
		user.setUserPwd("12345");
		user.setUserCreateTime(new Date());
		userDao.save(user);
	}

	@Test
	public void testFindByUserName() {
		User user = userDao.findUserByUserName("test");
		System.out.println(user);
	}

	@Test
	public void testTransation() throws Exception {
		User user = new User();
		user.setUserName(new Date().toString());
		user.setUserPwd("132");
		service.testUser(user);
		
	}

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.127.101", 6379);
		System.out.println(jedis.ping());
	}
}
