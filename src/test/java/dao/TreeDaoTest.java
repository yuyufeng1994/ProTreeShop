package dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.Tree;
import entity.User;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TreeDaoTest {
	@Autowired
	private TreeDao dao;

	/**
	 * 并发测试
	 */
	@Test
	public void testLock() throws Exception {

		Tree t = dao.findOne(28l);
		t.setTreeQuantity(t.getTreeQuantity() - 10);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		t = dao.save(t);
		System.out.println(t);

	}
	@Test
	public void testLock2() throws Exception {

		Tree t = dao.findOne(28l);
		t.setTreeQuantity(t.getTreeQuantity() - 20);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		t = dao.save(t);
		System.out.println(t);

	}

	@Test
	public void test() {
		Tree rt = dao.findOne(43l);

		for (int i = 0; i < 100; i++) {
			Tree t = new Tree();
			t.setTreeName(rt.getTreeName() + i);
			t.setTreePath(rt.getTreePath());
			t.setTreePlace(rt.getTreePlace());
			t.setTreeType(rt.getTreeType());
			t.setTreeQuantity(68l + i);
			t.setTreeBrief("没有简介");
			t.setSellStartTime(new Date());
			t.setSellEndTime(new Date());
			t.setTreePrice(6f);
			dao.save(t);
		}
	}
}
