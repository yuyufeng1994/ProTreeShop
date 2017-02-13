package dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Item;

/**
 * 用户Dao
 * 
 * @author yyf
 *
 */
public interface ItemDao extends PagingAndSortingRepository<Item, Long> {

}
