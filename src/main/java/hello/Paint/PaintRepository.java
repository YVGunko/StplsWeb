package hello.Paint;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.Paint.Paint;

@Repository
public interface PaintRepository extends JpaRepository<Paint,String>{
	public List<Paint> findByDtGreaterThan(Date date);
	
	public List<Paint> findByid(String id) ;

	public List<Paint> findByName(String name);

	public List<Paint> findAllByOrderByIdAsc();

	public List<Paint> findAllByOrderByNameAsc();

	public List<Paint> findByNameOrderByName(String name);

	public List<Paint> findByIdGreaterThanOrderByCost(String string);

}