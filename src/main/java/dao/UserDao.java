package dao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import entity.User;

/**
 * 用户Dao
 * 
 * @author yyf
 *
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {
	@Query("select u from User u where u.userName = ?1 ")
	User findUserByUserName(String userName);
}
