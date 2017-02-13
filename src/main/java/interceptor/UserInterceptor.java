package interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import entity.User;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;
import utils.SerializeUtil;

/**
 * 测试拦截器
 * 
 * @author yyf
 * 
 */
public class UserInterceptor implements HandlerInterceptor {

	// 执行Handler完成执行此方法
	// 应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
	}

	// 进入Handler方法之后，返回modelAndView之前执行
	// 应用场景从模型出发 公用model数据（菜单导航）在这里传到视图，也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
		Cookie[] cs = request.getCookies();
		for (Cookie c : cs) {
			if (c.getName().equals("SESSIONUSER")) {
				Jedis jedis = null;
				if (c.getValue() != null && !"".equals(c.getValue())) {
					try {
						jedis = RedisUtil.getJedis();
						byte[] userb = jedis.get((c.getValue()).getBytes());
						User user = (User) SerializeUtil.unserialize(userb);
						request.setAttribute("session_user", user);
						String sCart = "cartNumber_" + c.getValue();
						String n = jedis.get(sCart);
						if (n != null) {
							request.setAttribute("cart_quantity", n);
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						jedis.close();
					}
				}
			}
		}

	}

	// 进入Handler方法之前
	// 用于身份认真、身份授权
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		Cookie[] cs = request.getCookies();
		if (cs == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		for (Cookie c : cs) {
			if (c.getName().equals("SESSIONUSER")) {
				if (c.getValue() != null && c.getValue() != null) {
					Jedis jedis = null;
					try {
						jedis = RedisUtil.getJedis();
						if (jedis.exists(c.getValue().getBytes())) {
							jedis.expire("cartNumber_" + c.getValue(), 1800);// 刷新redis时间
							jedis.expire("cart_" + c.getValue(), 1800);// 刷新redis时间
							jedis.expire(c.getValue(), 1800);// 刷新redis时间
							return true;
						} else {
							Cookie cook = new Cookie("SESSIONUSER", null);
							cook.setMaxAge(0);
							response.addCookie(cook);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						jedis.close();
					}

				}
			}
		}
		response.sendRedirect(request.getContextPath() + "/login");

		return false;
	}

}
