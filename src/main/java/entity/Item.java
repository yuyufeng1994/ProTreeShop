package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 交易项
 * 
 * @author Yu Yufeng
 *
 */
@Entity
@Table(name = "item_info")
public class Item implements Serializable {
	@Id // 主键
	@GeneratedValue(strategy = GenerationType.AUTO) // 自动增长类型
	private Long itemId;
	@OneToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "treeId")
	private Tree itemTree;
	private Integer itemQuantity;
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "tradeId")
	private Trade itemTrade;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Tree getItemTree() {
		return itemTree;
	}

	public void setItemTree(Tree itemTree) {
		this.itemTree = itemTree;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Trade getItemTrade() {
		return itemTrade;
	}

	public void setItemTrade(Trade itemTrade) {
		this.itemTrade = itemTrade;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemTree=" + itemTree.getTreeName() + ", itemQuantity=" + itemQuantity
				+ "]";
	}

}
