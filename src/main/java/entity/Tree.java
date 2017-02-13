package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.jpa.repository.Lock;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 树苗商品实体
 * 
 * @author Yu Yufeng
 *
 */
@Entity
@Table(name = "tree_info")
public class Tree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id // 主键
	@GeneratedValue(strategy = GenerationType.AUTO) // 自动增长类型
	private Long treeId;
	private String treeName;
	private String treeBrief;
	private String treePath;
	private Float treePrice;
	private Long treeQuantity;// 库存
	private Date sellStartTime;
	private Date sellEndTime;
	private String treeType;
	private String treePlace;
	@Version
	@Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
	private Long version = 0L;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getTreeBrief() {
		return treeBrief;
	}

	public void setTreeBrief(String treeBrief) {
		this.treeBrief = treeBrief;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Float getTreePrice() {
		return treePrice;
	}

	public void setTreePrice(Float treePrice) {
		this.treePrice = treePrice;
	}

	public Long getTreeQuantity() {
		return treeQuantity;
	}

	public void setTreeQuantity(Long treeQuantity) {
		this.treeQuantity = treeQuantity;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSellStartTime() {
		return sellStartTime;
	}

	public void setSellStartTime(Date sellStartTime) {
		this.sellStartTime = sellStartTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSellEndTime() {
		return sellEndTime;
	}

	public void setSellEndTime(Date sellEndTime) {
		this.sellEndTime = sellEndTime;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getTreePlace() {
		return treePlace;
	}

	public void setTreePlace(String treePlace) {
		this.treePlace = treePlace;
	}

	@Override
	public String toString() {
		return "Tree [treeId=" + treeId + ", treeName=" + treeName + ", treeBrief=" + treeBrief + ", treePath="
				+ treePath + ", treePrice=" + treePrice + ", treeQuantity=" + treeQuantity + ", sellStartTime="
				+ sellStartTime + ", sellEndTime=" + sellEndTime + ", treeType=" + treeType + ", treePlace=" + treePlace
				+ "]";
	}

}
