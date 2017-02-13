package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Tree;
import service.TreeService;

/**
 * 
 * @author Yu Yufeng
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private TreeService treeService;

	@RequestMapping("/info")
	public String toUserUI(HttpSession session, Model model) throws Exception {
		return "user/info";
	}

	@RequestMapping("/main")
	public String indexUi(HttpSession session, Model model) throws Exception {
		return "index";
	}

	/**
	 * 得到商品列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tree-list/{no}")
	public @ResponseBody List<Tree> getList(HttpSession session, Model model, @PathVariable("no") Integer no)
			throws Exception {
		Sort sort = new Sort(Direction.DESC, "treeId");
		List<Tree> trees = treeService.getUseablePage(no, 8, sort);
		return trees;
	}

}
