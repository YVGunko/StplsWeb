package hello.Price;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PriceColumnRepository extends JpaRepository<PriceColumn, Integer>{
	public PriceColumn findOneById(Integer Id);
	public Optional<PriceColumn> findOneByPriceIdAndPriceType2CrudeId(Integer price_id, Integer price_type2crude_id);
	public List<PriceColumn> findByPriceId(Integer price_id);
	@Transactional
	public void deleteByPriceId(Integer id);
}
