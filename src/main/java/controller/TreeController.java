package controller;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import entity.Tree;
import service.TreeService;

/**
 * 
 * @author Yu Yufeng
 *
 */
@Controller
@RequestMapping("/user")
public class TreeController {
	@Autowired
	private TreeService treeService;

	@RequestMapping("/tree/{pageNo}")
	public String toTreeUI(HttpSession session, Model model, @PathVariable("pageNo") Integer pageNo) throws Exception {
		if (pageNo == null || pageNo < 0) {
			pageNo = 0;
		}
		Sort sort = new Sort(Direction.DESC, "treeId");
		Page<Tree> page = treeService.getPage(pageNo, 5, sort);
		model.addAttribute("page", page);
		return "user/tree";
	}

	@RequestMapping("/tree-save")
	public String toTreeSaveUI(HttpSession session, Model model, Long id, Integer pageNo) throws Exception {
		if (id != null) {
			Tree t = treeService.findOne(id);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("t", t);
		}

		return "user/tree-save";
	}
	
	@RequestMapping("/tree-delete")
	public String toTreeDelete(HttpSession session, Model model, Long id, Integer pageNo) throws Exception {
		if (id != null) {
			treeService.delete(id);
			model.addAttribute("pageNo", pageNo);
		}

		return "redirect:/user/tree/" + pageNo;
	}

	@RequestMapping("/tree-save-sub")
	public String toTreeSaveSub(@RequestParam(value = "file", required = false) MultipartFile file, Tree tree,
			HttpSession session, Model model, Integer pageNo) throws Exception {
		if (pageNo == null) {
			pageNo = 0;
		}
		
		File doc = new File("c://upload");
		if (!doc.exists()) {
			doc.mkdirs();
		}
		String uuid = UUID.randomUUID().toString();
		String ofName = file.getOriginalFilename();
		String ext = ofName.substring(ofName.lastIndexOf(".") + 1, ofName.length());
		// 如果是图片则保存
		if (ext.equals("png") || ext.equals("jpeg") || ext.equals("jpg") || ext.equals("bmp") || ext.equals("gif")) {
			tree.setTreePath(uuid);
			file.transferTo(new File("c://upload/" + uuid + ".png"));
		}

		if (tree.getSellStartTime() == null) {
			tree.setSellStartTime(new Date());
		}
		if (tree.getSellEndTime() == null) {
			tree.setSellEndTime(new Date());
		}

		if (tree.getSellStartTime().getTime() > tree.getSellEndTime().getTime()) {
			Date tDate = tree.getSellEndTime();
			tree.setSellEndTime(tree.getSellStartTime());
			tree.setSellStartTime(tDate);
		}
		
		if(tree.getTreeId()!=null){
			tree.setVersion(treeService.findOne(tree.getTreeId()).getVersion());
		}

		treeService.save(tree);
		
		return "redirect:/user/tree/" + pageNo;
	}

}
