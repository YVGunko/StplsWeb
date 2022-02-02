package hello.Price;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
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
	public List<Price> findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName(Integer id, Integer id2,
			String name);
	public List<Price> findByPriceTypeIdAndPriceRootIdOrderByName(Integer id, Integer id2);
	public List<Price> findByPriceRootIdAndNameStartingWithOrderByName(Integer id, String name);
	public Optional<Price> findOneByNameAndPriceTypeIdAndPriceRootId(@NotNull String name, Integer id, Integer id2);

}
