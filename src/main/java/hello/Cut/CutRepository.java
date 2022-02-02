package hello.Cut;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.Cut.Cut;

@Repository
public interface CutRepository extends JpaRepository<Cut,String>{
	public List<Cut> findByDtGreaterThan(Date date);
	
	public List<Cut> findByid(String id) ;

	public List<Cut> findByNameOrderByName(String name);

	public List<Cut> findAllByOrderByIdAsc();

	public List<Cut> findAllByOrderByNameAsc();


	public List<Cut> findByIdGreaterThanOrderByCost(String string);

}
