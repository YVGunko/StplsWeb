package hello.PartBox;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Query ("SELECT quantity FROM PartBox WHERE boxMove.id=?1 AND sentToMasterDate IS NOT NULL")
	List<Integer> getQuantityByBoxMoveId(String id);
	@Query ("SELECT quantity FROM PartBox WHERE boxMove.id=?1 AND department.id = ?2 AND quantity = ?3 AND sentToMasterDate IS NOT NULL")
	Optional<List<Integer>> getQuantityByBoxMoveIdAndDepartmentAndQuantity(String bmId, Long departmentId, Integer Quantity);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="update part_box set sent_to_master_date= ?1  \n"+
	" where box_move = ?2 and sent_to_master_date is null", nativeQuery=true)
    void updateSentToMasterDateByBoxMoveId(Date dateToSet, String bmId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="update part_box set sent_to_master_date= ?1  \n"+
	" where box_move = ?2 and department = ?3 and quantity = ?4 \n"+
	" and sent_to_master_date is null", nativeQuery=true)
    void updateSentToMasterDateByBoxMoveIdAndDepartmentAndQuantity(Date dateToSet, String bmId, Long departmentId, Integer Quantity);
	
	@Query(value="SELECT CONCAT(o.order_id, '.', b.quantity_box, '.', b.num_box, ';', d.code) \n"+
	"FROM master_data o, box b, box_move m, part_box p, department d \n"+
	"WHERE o.id=b.master_data_id and b.id = m.box_id and m.id = p.box_move \n"+
	"and p.department=d.id and (p.sent_to_master_date is null) \n"+
	"and (m.sent_to_master_date is null) and m.operation_id = 1", nativeQuery=true)
    Optional<ArrayList<String>> selectDataForService3();
}
