package hello.Price;

import java.util.List;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceType2CrudeRepository extends JpaRepository<PriceType2Crude, Integer>{
	public PriceType2Crude findOneById(Integer Id);
	public List<PriceType2Crude> findByCrudeIdAndPriceTypeId(Integer crudeId, Integer priceTypeId);
	public List<PriceType2Crude> findByPriceTypeId(Integer priceTypeId);
	
	@Query("SELECT new hello.Price.PriceType2Crude( p.id, p.priceType, p.crude, p.pt2CrudeRoot) \n"+
			" FROM PriceType2Crude p \n"+
			" WHERE p.pt2CrudeRoot.id = (SELECT MAX(r.id) FROM Pt2CrudeRoot r WHERE r.dateOfChange<=?2) \n "+
			" AND p.priceType.id = ?1")
	public List<PriceType2Crude> findByPriceTypeId(Integer priceTypeId, Date priceDate);
}
