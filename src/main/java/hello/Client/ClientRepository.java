package hello.Client;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,String>{
	List<Client> findByDtGreaterThan(Date date);
	List<Client> findByNameOrderByName(String name);
	List<Client> findTop50ByNameContainingOrderByName(@Param("name") String name);
	List<Client> findByid(String id);
	Client findOneByid(@Param("id") String id);
	List<Client> findAllByOrderByNameAsc();
	List<Client> findAllByOrderByIdAsc();
	List<Client> findAllByOrderByName();
	List<Client> findById1cIsNull();
}
