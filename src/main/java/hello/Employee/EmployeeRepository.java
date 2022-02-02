package hello.Employee;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	List<Employee> findByDtGreaterThan(Date date);
	List<Employee> findBycode(String code);
	List<Employee> findByIdGreaterThanOrderByName(Long id);

	@Async
	CompletableFuture<Employee> findOneById(Long id);
}
