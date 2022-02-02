package hello.MasterData;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product, String>{
	Product findOneById(@Param("id") String id);
	Optional<Product> findOneByNameAndDivisionCode(@Param("name") String name, @Param("division_code") String division_code);
	List<Product> findTop20ByNameStartingWithOrderByName(@Param("name") String name);
	List<Product> findByNameStartingWithOrderByName(@Param("name") String name);
	List<Product> findByDivisionCodeOrderByName(String code);
	List<Product> findTop100ByDivisionCodeOrderByName(String code);
}
