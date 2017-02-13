package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Trade;

/**
 * 用户Dao
 * 
 * @author yyf
 *
 */
public interface TradeDao extends PagingAndSortingRepository<Trade, Long> {

	@Query("select t from Trade t where t.user.userId = ?1")
	Page<Trade> getPageByUser(Pageable pageable, Long userId);

}
