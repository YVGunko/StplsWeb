package hello.Price;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
	public List<Price> findByPriceTypeIdOrderByName (Integer priceTypeId);
	public List<Price> findByPriceTypeIdOrderByDateOfLastChangeDesc (Integer priceTypeId);
	public List<Price> findByOrderByDateOfLastChangeDesc ();
	public List<Price> findByNameStartingWithOrderByName (String name);
	public List<Price> findByPriceTypeIdAndNameStartingWithOrderByName (Integer priceTypeId,String name);
	public Price findOneById (Integer Id);
	public Optional<Price> findOneByNameAndPriceTypeId (String name, Integer priceTypeId);
	public Optional<List<Price>> findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName(Integer id, Integer id2,
			String name);
	public Optional<List<Price>> findByPriceTypeIdAndPriceRootIdOrderByName(Integer ptId, Integer prId);
	public Optional<List<Price>> findByPriceTypeIdAndPriceRootId(Integer ptId, Integer prId);
	public List<Price> findByPriceRootIdAndNameStartingWithOrderByName(Integer id, String name);
	public Optional<Price> findOneByNameAndPriceTypeIdAndPriceRootId(@NotNull String name, Integer id, Integer id2);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="update price set paint= ?2  \n"+
	" where price_root_id= ?1", nativeQuery=true)
    void updatePricePaint(Integer id, Double value);
}
