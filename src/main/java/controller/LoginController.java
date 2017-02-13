package controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.User;
import redis.clients.jedis.Jedis;
import service.UserService;
import utils.RedisUtil;
import utils.SerializeUtil;

/**
 * 
 * @author Yu Yufeng
 *
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	/**
	 * 跳转到登录视图
	 * 
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String loginUI(HttpSession session, Model model) throws Exception {
		return "login";
	}

	@RequestMapping("/quit")
	public String quit(HttpSession session, Model model, HttpServletResponse response,
			@CookieValue(value = "SESSIONUSER", required = false) String sessionUser) throws Exception {
		Cookie cookie = new Cookie("SESSIONUSER", null);
		Jedis jedis = RedisUtil.getJedis();
		jedis.del(sessionUser.getBytes());
		jedis.close();
		response.addCookie(cookie);
		return "redirect:login";
	}

	/**
	 * 跳转到注册视图
	 * 
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/register")
	public String registerUI(HttpSession session, Model model) throws Exception {
		return "register";
	}

	/**
	 * 登录提交
	 * 
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login-sub")
	public String loginSubmit(HttpSession session, Model model, User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (userService.checkUserPwd(user)) {
			User record = userService.getUserByName(user.getUserName());
			String uuid = UUID.randomUUID().toString();
			Jedis jedis = RedisUtil.getJedis();
			jedis.setex(("user_"+uuid).getBytes(), 1800, SerializeUtil.serialize(record));
			jedis.close();
			Cookie cookie = new Cookie("SESSIONUSER", "user_"+uuid);
			response.addCookie(cookie);
			return "redirect:user/main";
		} else {
			model.addAttribute("message", "登录失败，用户名或密码错误！");
			return "login";
		}
	}

	@RequestMapping("/register-sub")
	public String registerSubmit(HttpSession session, Model model, User user) throws Exception {
		User record = null;
		try {
			record = userService.register(user);
		} catch (Exception e) {
			model.addAttribute("message", "用户名已存在");
		}

		if (null != record) {
			model.addAttribute("user", record);
			model.addAttribute("message", "注册成功");
			return "login";
		}

		return "register";
	}
}
