package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import entity.User;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;
import utils.SerializeUtil;

/**
 * 用户Services
 * 
 * @author Yu Yufeng
 *
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	@Transactional(rollbackFor=Exception.class,readOnly = false, propagation = Propagation.REQUIRED)
	public User testUser(User u) throws Exception{
		u = userDao.save(u);
//		if(1 == 1){
//			throw new Exception();
//		}
		return u;
	}

	public boolean checkUserPwd(User user) {
		User record = userDao.findUserByUserName(user.getUserName());
		if (record == null) {
			return false;// 用户不存在
		} else if (!record.getUserPwd().equals(user.getUserPwd())) {
			return false;// 用户密码错误
		}
		return true;
	}

	public User register(User user) {
		user.setUserCreateTime(new Date());
		return userDao.save(user);
	}

	public User getUserByName(String userName) {
		return userDao.findUserByUserName(userName);
	}

	public User getSessionUser(String sessionUser) {
		Jedis jedis = RedisUtil.getJedis();
		byte[] userb = jedis.get(sessionUser.getBytes());
		User user = (User) SerializeUtil.unserialize(userb);
		RedisUtil.release(jedis);
		return user;
	}
}
