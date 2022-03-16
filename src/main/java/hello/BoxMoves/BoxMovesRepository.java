package hello.BoxMoves;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import hello.Box.Box;
import hello.Controller.BoxMoveBoxAndQuantityDTO;
import hello.Employee.Employee;
import hello.MasterData.MasterData;
import hello.OutDoc.OutDoc;
import hello.Web.OutDoc.MdWeb;

@Repository
//@Transactional
public interface BoxMovesRepository extends JpaRepository<BoxMove,String>{

	List<BoxMove> findByIdNotIn(List<String> bmId);
	
	List<BoxMove> findBySentToMasterDateAndIdNotIn(Date sent, List<String> bmId);
	
	List<BoxMove> findByBoxId(String boxId);
	
	List<BoxMove> findByBoxIdInAndOperationId(List<String> boxIdList, long operId);
	
	List<BoxMove> findByBoxIdAndOperationIdAndSentToMasterDateIsNull(String id, long operId);
	List<BoxMove> findByBoxIdAndSentToMasterDateIsNull(String id);
	
	List<BoxMove> findByOperationIdAndBoxSentToMasterDate(long OperId, Date sent);
	
	List<BoxMove> findByOperationIdAndSentToMasterDate(long OperId, Date sent);
	
	List<BoxMove> findBySentToMasterDate (Date sent);
	
	Optional<BoxMove> getByOperationIdAndBoxId(long OperId, String BoxId);

	List<BoxMove> findByBoxMasterDataDivisionCodeAndOperationIdAndSentToMasterDate(String code, long operationId, Date sent);

	List<BoxMove> findByOperationIdAndSentToMasterDateAndBoxMasterDataDivisionCode(long operationId, Date sent,
			String code);

	List<BoxMove> findByDateGreaterThan(Date date);
	List<BoxMove> findByDateGreaterThanOrderByDateAscOperationIdAsc(Date date);
	
	List<BoxMove> findByIdIn(List<String> bmId);
	
	Long countDistinctBoxIdByIdIn(List<String> bmId);
	
	@Query ("SELECT count(distinct box) FROM BoxMove WHERE id in \n" + 
    		" (SELECT boxMove\n" + 
    		" FROM PartBox \n" + 
    		" WHERE outDoc=?1) ")
	Long findCountBoxByPartBoxOutDocId(OutDoc outdoc);
	
	@Query ("SELECT count(distinct box) FROM BoxMove WHERE id in \n" + 
    		" (SELECT boxMove\n" + 
    		" FROM PartBox \n" + 
    		" WHERE outDoc=?1 AND employee=?2) ")
	Long findCountBoxByPartBoxOutDocIdAndEmployeeId(OutDoc outdoc, Employee employee);
	
	@Query ("SELECT distinct box as boxId FROM BoxMove WHERE id in \n" + 
    		" (SELECT boxMove\n" + 
    		" FROM PartBox \n" + 
    		" WHERE outDoc=?1) ")
	List<String> findBoxIdByBoxMovePartBoxOutDocId(OutDoc outdoc);
	
	@Query ("SELECT new hello.Web.OutDoc.MdWeb(a.id, m.operation, p.employee, p.quantity, a.cut, a.glue, a.paint) \n" + 
    		"FROM MasterData a, Box b, BoxMove m, PartBox p \n" + 
    		"WHERE p.outDoc=?1 and p.boxMove = m.id and a.id = b.masterData and b.id = m.box \n" +
    		"ORDER BY a.id")
	List<MdWeb>findMasterDataByBoxMovePartBoxOutDoc(OutDoc outdoc);

	List<BoxMove> findByOperationIdAndIdIn(long operId, List<String> bmIdList);

	List<BoxMove> findByOperationIdAndBoxIdIn(long l, List<String> bmIdList);
	
	Page<BoxMove> findAll(Pageable pageable);
	Page<BoxMove> findByDateGreaterThanEqualOrderByDateAsc(Date date, Pageable pageable);

	List<BoxMove> findByOperationIdAndDateGreaterThanOrderByDateAscOperationIdAsc(Long operationId, Date date);
	List<BoxMove> findByOperationIdAndDateGreaterThanOrderByDateAscOperationIdAsc(Long operationId, String date);

	List<BoxMove> findByOperationIdAndDateBetweenOrderByDateAscOperationIdAsc(Long operationId, Date atStartOfDay,
			Date atEndOfDay);
	List<BoxMove> findByOperationIdAndDateBetweenOrderByDateAscOperationIdAsc(Long operationId, String atStartOfDay,
			String atEndOfDay);

	List<BoxMove> findByOperationIdAndDateBetweenOrderByDateAsc(Long operationId, Date atStartOfDay, Date atEndOfDay);

	List<BoxMove> findBySentToMasterDateOrderByDate(Object object);

	@Query("SELECT new hello.Controller.BoxMoveBoxAndQuantityDTO( m.id, b.id, b.quantityBox) \n"+
			" FROM Box b, BoxMove m \n"+
			" WHERE m.sentToMasterDate IS NULL and b.id = m.box ")
	Page<BoxMoveBoxAndQuantityDTO> getIdAndBoxQuantityPageable(Pageable pageable);
	
	@Query("SELECT new hello.Controller.BoxMoveBoxAndQuantityDTO( m.id, b.id, b.quantityBox) \n"+
			" FROM Box b, BoxMove m \n"+
			" WHERE m.sentToMasterDate IS NULL and b.id = m.box ")
	List<BoxMoveBoxAndQuantityDTO> getIdAndBoxQuantity();
	
	@Modifying
    @Query("UPDATE BoxMove SET sentToMasterDate=?1 WHERE id=?2")
	void setSentToMasterDate(Date date, String bmId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="update box_move set sent_to_master_date= ?1 where id = ?2", nativeQuery=true)
    void updateSentToMasterDate(Date dateToSet, String bmId);


}