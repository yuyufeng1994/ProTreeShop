package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.Trade;
import entity.User;
import service.TradeService;
import service.UserService;

/**
 * 
 * @author Yu Yufeng
 *
 */
@Controller
@RequestMapping("/user")
public class TradeController {

	@Autowired
	private UserService userService;

	@Autowired
	private TradeService tradeService;

	@RequestMapping("/trade/{pageNo}")
	public String toTradeUI(Model model, @PathVariable("pageNo") Integer pageNo,
			@CookieValue(value = "SESSIONUSER", required = false) String sessionUser) throws Exception {
		if (pageNo == null || pageNo < 0) {
			pageNo = 0;
		}
		User user = userService.getSessionUser(sessionUser);
		Long userId = user.getUserId();
		Sort sort = new Sort(Direction.DESC, "tradeId");
		Page<Trade> page = tradeService.getPageByUser(pageNo,7, sort, userId);
		model.addAttribute("page", page);
		return "user/trade";
	}

	@RequestMapping("/history/{pageNo}")
	public String toistoryUI(Model model, @PathVariable("pageNo") Integer pageNo) throws Exception {
		if (pageNo == null || pageNo < 0) {
			pageNo = 0;
		}
		Sort sort = new Sort(Direction.DESC, "tradeId");
		Page<Trade> page = tradeService.getPage(pageNo,7, sort);
		model.addAttribute("page", page);
		return "user/history";
	}
}
