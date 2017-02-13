package dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Tree;

/**
 * 用户Dao
 * 
 * @author yyf
 *
 */
public interface TreeDao extends PagingAndSortingRepository<Tree, Long> {

	/**
	 * 查询在时间范围内的树苗列表
	 * 
	 * @param pageable
	 * @param date1
	 * @param date2
	 * @return
	 */
	@Query("select t from Tree t where t.sellStartTime <= ?1 and t.sellEndTime >= ?2")
	Page<Tree> findUseablePage(Pageable pageable, Date date1, Date date2);

	// @Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select t from Tree t where t.treeId = ?1")
	Tree findLockOne(Long id);
}
