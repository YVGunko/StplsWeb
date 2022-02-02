package hello.Price;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTypeRepository extends JpaRepository<PriceType, Integer>{
	public PriceType findOneById(Integer Id);
	public List<PriceType> findByIdGreaterThanOrderByName(Integer Id);
	public PriceType findTopByIdGreaterThanOrderByName(Integer Id);
	
}
