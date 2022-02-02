package hello.Price;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRootRepository extends JpaRepository<PriceRoot, Integer>{
	public PriceRoot findOneById(Integer Id);
	public List<PriceRoot> findBySampleOrderByDateOfChangeDesc(Boolean sample);
	public List<PriceRoot> findBySampleOrderByIdAsc(Boolean sample);
	public PriceRoot findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(Integer id, Boolean sample);
	public List<PriceRoot> findByPriceTypeIdAndSampleOrderByDateOfChangeDesc(Integer pt, Boolean sample);
	public PriceRoot findOneByPriceTypeIdAndSampleOrderByDateOfChangeDesc(Integer pt, Boolean sample);
	
	/*@Query (" SELECT DISTINCT new hello.Price.PriceRoot(a.id, a.dateOfChange, a.note, a.plusValue) \n" + 
    		" FROM Price o, PriceRoot a \n" + 
    		" WHERE o.priceType.id=:priceType AND o.priceRoot.id = a.id  \n" +
    		" order by a.dateOfChange desc")
	List<PriceRoot>findByPriceTypeAndPrice(@Param("priceType") Integer priceType);*/
}
