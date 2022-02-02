package hello.Price;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceType2CrudeRepository extends JpaRepository<PriceType2Crude, Integer>{
	public PriceType2Crude findOneById(Integer Id);
	public List<PriceType2Crude> findByCrudeIdAndPriceTypeId(Integer crudeId, Integer priceTypeId);
	public List<PriceType2Crude> findByPriceTypeId(Integer priceTypeId);
}
