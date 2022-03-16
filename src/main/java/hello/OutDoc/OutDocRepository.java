package hello.OutDoc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutDocRepository extends JpaRepository<OutDoc,String>{
	List<OutDoc> findBynumber(String number);
	List<OutDoc> findByid(String id);
	List<OutDoc> findByIdNotIn(ArrayList<String> id);
	List<OutDoc> findByIdIn(ArrayList<String> id);
	List<OutDoc> findByDateGreaterThan(Date date);
	List<OutDoc> findByDateGreaterThanAndDivision_code(Date date, String division_code);
	List<OutDoc> findByDivision_code(String division_code);
	List<OutDoc> findByDivisionCodeAndOperationIdAndDateBetweenOrderByDate
(String code, long operationId, Date DateS, Date DateE);
	List<OutDoc> findByDateBetween(Date DateS, Date DateE);
	List<OutDoc> findByDateBetweenOrderByDate(Date atStartOfDay, Date atEndOfDay);
	List<OutDoc> findByDivisionCodeAndDateBetweenOrderByDate(String code, Date atStartOfDay, Date atEndOfDay);
	List<OutDoc> findByOperationIdAndDateBetweenOrderByDate(Long id, Date atStartOfDay, Date atEndOfDay);
	List<OutDoc> findByDivisionCodeAndOperationIdAndUserIdAndDateBetweenOrderByDate(String code, Long id, Long id2,
			Date atStartOfDay, Date atEndOfDay);
	List<OutDoc> findByOperationIdAndUserIdAndDateBetweenOrderByDate(Long id, Long id2, Date atStartOfDay,
			Date atEndOfDay);
}
