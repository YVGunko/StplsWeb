package hello.Price;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudeRepository extends JpaRepository<Crude, Integer>{
	public Crude findOneById(Integer Id);
	public List<Crude> findByIdGreaterThan(Integer Id);
	
}
