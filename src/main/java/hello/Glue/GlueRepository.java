package hello.Glue;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.Glue.Glue;
@Repository
public interface GlueRepository extends JpaRepository<Glue,String>{
	public List<Glue> findByDtGreaterThan(Date date);
	public List<Glue> findByid(String id);
	public List<Glue> findByNameOrderByName(String name);
	public List<Glue> findAllByOrderByNameAsc();
	public List<Glue> findAllByOrderByIdAsc();

	public List<Glue> findByIdGreaterThanOrderByCost(String string);

}
