package hello.User;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long>{
	
	List<User> findByDtGreaterThan(Date date);
	List<User> findByIdGreaterThanOrderByName(Long id);
	List<User> findByName(String name);
	List<User> findByNameAndPswd(@Param("name") String name, @Param("pswd") String pswd);
	User findOneByNameAndPswd(@Param("name") String name, @Param("pswd") String pswd);
	User findOneByNameAndPswdAndExternalTrue(@Param("name") String name, @Param("pswd") String pswd);
	User findOneById(@Param("id") Long id);
	List<User> findByIdGreaterThanEqualOrderByName(Long id);
	List<User> findByNameStartingWith(String name);
	List<User> findByIdGreaterThanAndNameStartingWith(long l, String name);
	List<User> findByIdGreaterThanAndNameStartingWithOrderByName(long l, String name);
	List<User>  findByExternalFalse();
	List<User>  findByDtGreaterThanAndExternalFalse(Date date);
	User findOneByName(String name);
}
