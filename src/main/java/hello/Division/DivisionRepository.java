package hello.Division;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DivisionRepository extends JpaRepository<Division,String>{
	List<Division> findBycode(String code);

	List<Division> findAll();

	List<Division> findByNameStartingWith(String name);
	
	Division findOneBycode(String code);
}
