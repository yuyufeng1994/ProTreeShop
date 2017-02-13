package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import Exception.NoQuantityException;
import dao.ItemDao;
import dao.TradeDao;
import dao.TreeDao;
import entity.Item;
import entity.Trade;
import entity.Tree;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;
import utils.SerializeUtil;

@Service
public class TradeService {
	@Autowired
	private TradeDao tradeDao;

	@Autowired
	private TreeDao treeDao;

	@Autowired
	private ItemDao itemDao;

	public Trade getCart(String sCart) {
		if (sCart != null) {
			Jedis j = RedisUtil.getJedis();
			byte[] obj = j.get((sCart).getBytes());
			Trade t = (Trade) SerializeUtil.unserialize(obj);
			RedisUtil.release(j);
			return t;
		} else {
			return null;
		}
	}

	public void clear(String sCart) {
		Jedis j = RedisUtil.getJedis();
		j.del(sCart);
		RedisUtil.release(j);
	}

	public String getCartNumber(String sCart) {
		if (sCart == null) {
			return 0 + "";
		}
		Jedis j = RedisUtil.getJedis();
		String n = j.get(sCart);
		RedisUtil.release(j);
		if (n == null) {
			return 0 + "";
		}
		return n;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void pay(Trade t) throws Exception {
		List<String> list = new ArrayList<String>();
		t.setTradeTime(new Date());
		t = tradeDao.save(t);
		t.setTradeState("已付款");
		float total = 0f;
		for (Item i : t.getItems()) {
			if(i.getItemQuantity() == 0){
				continue;
			}
			Long tid = i.getItemTree().getTreeId();
			Tree tr = null;
			tr = treeDao.findOne(tid);
			tr.setTreeQuantity(tr.getTreeQuantity() - i.getItemQuantity());

			total += i.getItemQuantity() * i.getItemTree().getTreePrice();

			if (tr.getTreeQuantity() < 0) {
				list.add(tr.getTreeName());
			}
			treeDao.save(tr);
			i.setItemTrade(t);
			i = itemDao.save(i);
		}

		t.setTotalPrice(total);
		t = tradeDao.save(t);

		StringBuffer res = new StringBuffer();
		if (list.size() > 0) {
			for (String s : list) {
				res.append(">>>>>名称：" + s + "<br />");
			}
			throw new NoQuantityException("<h3>库存不足!</h3>" + res);
		}

	}

	public Page<Trade> getPageByUser(Integer pageNo, int i, Sort sort, Long userId) {
		Pageable pageable = new PageRequest(pageNo, i, sort);
		return tradeDao.getPageByUser(pageable, userId);
	}

	public Page<Trade> getPage(Integer pageNo, int i, Sort sort) {
		Pageable pageable = new PageRequest(pageNo, i, sort);
		return tradeDao.findAll(pageable);
	}

}
