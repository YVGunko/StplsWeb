package hello.Operation;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation,Long>{

	List<Operation> findByDtGreaterThan(Date date);

	List<Operation>findByDivisionCodeOrderById(String division_code);

	List<Operation> findAllByOrderByIdAsc();

}
