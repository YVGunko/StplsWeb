package hello.Department;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{
	
	@Async
	CompletableFuture<Department> findOneById(Long id);
	
	List<Department> findByDtGreaterThan(Date date);
	//List<DepartmentReq> findByDtGreaterThan(Date date);
	List<Department> findBycode(String code);
	Optional<Department> findByCode(String code);
	List<Department> findByIdGreaterThanOrderByName(Long id);

}
