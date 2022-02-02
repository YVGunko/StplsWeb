package hello.PartBox;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import hello.BoxMoves.BoxMove;
import hello.Employee.Employee;
import hello.MasterData.MasterData;
import hello.Operation.Operation;
import hello.OrderByOutDoc.OrderByOutDoc;
import hello.OutDoc.OutDoc;
import hello.Web.OutDoc.MdWeb;

@Repository
//@Transactional
public interface PartBoxRepository extends JpaRepository<PartBox,String>{
	List<PartBox> findByBoxMoveId(String id);
	
	List<PartBox> findByOutDocId(String id);

	List<PartBox> findByBoxMoveIdAndSentToMasterDateIsNull(String id);
	List<PartBox> findByBoxMoveOperationIdAndSentToMasterDateIsNull(long operationId);

	List<PartBox> findByBoxMoveIdAndSentToMasterDateIsNotNull(String id);
	
	List<PartBox> findByBoxMoveIdInAndSentToMasterDateIsNull(List<String> idList);
	
	List<PartBox> findByBoxMoveIdIn(List<String> idList);

	Page<PartBox> findByIdNotIn(ArrayList<String> pbId, Pageable pageable);
	
	PartBox findFirstByBoxMoveIdAndDepartmentIdAndQuantityAndSentToMasterDateOrderByDate(String boxMoveId, long departmentId, int quantityBox, Date sentToMasterDate);
	List<PartBox> findByBoxMoveIdAndQuantityAndSentToMasterDate(String boxMoveId, int quantityBox, Date sentToMasterDate);

	List<PartBox> findByDateGreaterThan(Date date);

	List<PartBox> findByDateGreaterThanEqual(Date date);

	List<PartBox> findByOutDocIdAndBoxMoveIdIn(String outDocId, List<String> bmIdList);

	Page<PartBox> findByDateGreaterThanEqualOrderByDateAsc(Date date, Pageable paging);

	List<PartBox> findByEmployeeId(Long id);
	
	List<PartBox> findByOutDocIdAndEmployeeId(String id, Long EmployeeId);

	List<Integer> findQuantityByOutDocId(String id);

	List<PartBox> deleteByBoxMoveId(String id);
}
