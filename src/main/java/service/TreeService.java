package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.TreeDao;
import entity.Tree;

/**
 * 用户Services
 * 
 * @author Yu Yufeng
 *
 */
@Service
public class TreeService {
	@Autowired
	private TreeDao treeDao;

	public Tree save(Tree tree) {
		return treeDao.save(tree);
	}

	public Page<Tree> getPage(Integer pageNo, int i, Sort sort) {
		Pageable pageable = new PageRequest(pageNo, i, sort);
		return treeDao.findAll(pageable);
	}

	public Tree findOne(Long id) {
		return treeDao.findOne(id);
	}
	public void delete(Long id) {
		treeDao.delete(id);
	}

	public List<Tree> getUseablePage(Integer no, int i, Sort sort) {
		Pageable pageable = new PageRequest(no, i, sort);
		Date date = new Date();
		Page<Tree> page = treeDao.findUseablePage(pageable, date, date);
		return page.getContent();
	}

}
