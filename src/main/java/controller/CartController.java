package controller;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Exception.NoQuantityException;
import entity.Item;
import entity.Trade;
import entity.Tree;
import entity.User;
import redis.clients.jedis.Jedis;
import service.TradeService;
import service.TreeService;
import service.UserService;
import utils.RedisUtil;
import utils.SerializeUtil;

/**
 * 
 * @author Yu Yufeng
 *
 */
@Controller
@RequestMapping("/user")
public class CartController {
	@Autowired
	private TreeService treeService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private UserService userService;

	@RequestMapping("/cart")
	public String toCartUI(Model model) throws Exception {
		return "user/cart";
	}

	@RequestMapping("/item-delete/{id}")
	public @ResponseBody String itemDelete(@CookieValue(value = "SESSIONUSER", required = false) String sessionUser,
			@PathVariable("id") Long id) throws Exception {
		Jedis j = RedisUtil.getJedis();
		String sCart = "cart_" + sessionUser;
		Trade t = tradeService.getCart(sCart);
		for (Item i : t.getItems()) {
			if (i.getItemTree().getTreeId().equals(id)) {
				t.getItems().remove(i);
				break;
			}
		}

		j.setex(sCart.getBytes(), 1800, SerializeUtil.serialize(t));
		String sNCart = "cartNumber_" + sessionUser;
		j.setex(sNCart, 1800, t.getItems().size() + "");
		RedisUtil.release(j);
		return "success";
	}

	@RequestMapping("/item-add/{id}")
	public @ResponseBody String itemAdd(@CookieValue(value = "SESSIONUSER", required = false) String sessionUser,
			@PathVariable("id") Long id) throws Exception {
		Jedis j = RedisUtil.getJedis();
		String sCart = "cart_" + sessionUser;
		Trade t = tradeService.getCart(sCart);
		for (Item i : t.getItems()) {
			if (i.getItemTree().getTreeId().equals(id)) {
				if (i.getItemQuantity() >= i.getItemTree().getTreeQuantity()) {
					RedisUtil.release(j);
					return "falure";
				}
				i.setItemQuantity(i.getItemQuantity() + 1);
				break;
			}
		}

		j.setex(sCart.getBytes(), 1800, SerializeUtil.serialize(t));
		String sNCart = "cartNumber_" + sessionUser;
		j.setex(sNCart, 1800, t.getItems().size() + "");
		RedisUtil.release(j);
		return "success";
	}

	@RequestMapping("/item-decr/{id}")
	public @ResponseBody String itemDecr(@PathVariable("id") Long id,
			@CookieValue(value = "SESSIONUSER", required = false) String sessionUser) throws Exception {
		Jedis j = RedisUtil.getJedis();
		String sCart = "cart_" + sessionUser;
		Trade t = tradeService.getCart(sCart);
		for (Item i : t.getItems()) {
			if (i.getItemTree().getTreeId().equals(id)) {
				if (i.getItemQuantity() < 1) {
					RedisUtil.release(j);
					return "falure";
				}
				i.setItemQuantity(i.getItemQuantity() - 1);
				break;
			}
		}

		j.setex(sCart.getBytes(), 1800, SerializeUtil.serialize(t));
		String sNCart = "cartNumber_" + sessionUser;
		j.setex(sNCart, 1800, t.getItems().size() + "");
		RedisUtil.release(j);
		return "success";
	}

	@RequestMapping("/pay-success")
	public String paySuccess(Model model, @CookieValue(value = "SESSIONUSER", required = false) String sessionUser)
			throws Exception {
		String sCart = "cart_" + sessionUser;
		String sNCart = "cartNumber_" + sessionUser;
		tradeService.clear(sCart);
		tradeService.clear(sNCart);
		model.addAttribute("message", "购买成功！");
		return "user/cart-message";
	}

	@RequestMapping("/cart-clear")
	public String cartClear(Model model, @CookieValue(value = "SESSIONUSER", required = false) String sessionUser)
			throws Exception {
		String sCart = "cart_" + sessionUser;
		String sNCart = "cartNumber_" + sessionUser;
		tradeService.clear(sCart);
		tradeService.clear(sNCart);
		return "redirect:cart";
	}

	@RequestMapping("/pay-failure")
	public String payFailure(Model model) throws Exception {
		model.addAttribute("message", "购买失败！");
		return "user/cart";
	}

	@RequestMapping("/pay")
	public String pay(Model model, @CookieValue(value = "SESSIONUSER", required = false) String sessionUser)
			throws Exception {
		String sCart = "cart_" + sessionUser;
		Trade t = tradeService.getCart(sCart);

		User user = userService.getSessionUser(sessionUser);
		t.setUser(user);
		try {
			tradeService.pay(t);
		} catch (OptimisticLockException e2) {
			model.addAttribute("message", "当前用户过多，服务器超载中，请稍后付款～");
			return "user/cart-message";
		} catch (NoQuantityException e1) {
			model.addAttribute("message", e1.getMessage());
			return "user/cart-message";
		}
		return "redirect:pay-success";
	}

	@RequestMapping("/get-cart")
	public @ResponseBody Trade getCart(@CookieValue(value = "SESSIONUSER", required = false) String sessionUser,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		String sCart = "cart_" + sessionUser;
		Trade t = tradeService.getCart(sCart);
		return t;
	}

	@RequestMapping("/get-cart-number")
	public @ResponseBody String getCartNumber(@CookieValue(value = "SESSIONUSER", required = false) String sessionUser,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		String sCart = "cartNumber_" + sessionUser;
		String t = tradeService.getCartNumber(sCart);
		return t;
	}

	@RequestMapping("/add-cart/{id}")
	public @ResponseBody String add(Model model, @PathVariable("id") Long id,
			@CookieValue(value = "SESSIONUSER", required = false) String sessionUser, HttpServletResponse response)
			throws Exception {
		String sCart = "cart_" + sessionUser;
		Tree tree = treeService.findOne(id);
		if (tree.getTreeQuantity() <= 0) {
			return "faliure";
		}
		Item i = new Item();
		i.setItemTree(tree);
		i.setItemQuantity(1);
		Trade t = null;

		Jedis j = RedisUtil.getJedis();
		if (sCart != null && !"".equals(sCart)) {
			byte[] obj = j.get((sCart).getBytes());
			t = (Trade) SerializeUtil.unserialize(obj);
			if (t == null) {
				t = new Trade();
				t.setTradeState("未结算");
			}
		} else {
			t = new Trade();
			t.setTradeState("未结算");
		}
		
		Boolean res = t.addToItems(i);
		if(res == false){
			return "faliure";
		}
		String sNCart = "cartNumber_" + sessionUser;
		j.setex(sNCart, 1800, t.getItems().size() + "");
		j.setex(sCart.getBytes(), 1800, SerializeUtil.serialize(t));
		RedisUtil.release(j);

		return "success";
	}

}
