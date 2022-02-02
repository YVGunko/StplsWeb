package hello.MasterData;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository <Color, String>{
	Color findOneById(@Param("id") String id);
	//Optional<Color> findOneByName (@Param("name") String name);
	Optional<Color> findOneByNameAndDivisionCode(@Param("name") String name, @Param("division_code") String division_code);
	List<Color> findByDivisionCodeOrderByName(String code);
	List<Color> findTop20ByNameStartingWithOrderByName(@Param("name") String name);
	
	List<Color> findAllByOrderByNameAsc();
	List<Color> findByDivisionCodeAndOccurrenceOrderByName(String division_code, String occurrence);
}
