package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 交易记录
 * 
 * @author Yu Yufeng
 *
 */
@Entity
@Table(name = "trade_info")
public class Trade implements Serializable {
	@Id // 主键
	@GeneratedValue(strategy = GenerationType.AUTO) // 自动增长类型
	private Long tradeId;
	private Date tradeTime;// 交易时间
	private String tradeState;// 订单状态
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "userId")
	private User user;
	private Float totalPrice;

	@OrderBy("itemId desc")
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "itemTrade")
	private Set<Item> items = new LinkedHashSet<Item>();

	public Boolean addToItems(Item item) {
		Boolean flag = false;
		for (Item i : items) {
			if (i.getItemTree().getTreeId().equals(item.getItemTree().getTreeId())) {
				i.setItemQuantity(i.getItemQuantity() + 1);
				if (i.getItemQuantity() > item.getItemTree().getTreeQuantity()) {
					return false;
				}
				flag = true;
			}
		}
		if (flag == false) {
			items.add(item);
		}

		return true;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + ", tradeState=" + tradeState + ", user="
				+ user + ", items=" + items + "]";
	}

}
