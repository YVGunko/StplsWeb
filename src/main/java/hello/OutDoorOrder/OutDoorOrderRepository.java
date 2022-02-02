package hello.OutDoorOrder;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hello.OutDoorOrder.OutDoorOrder;

@Repository
public interface OutDoorOrderRepository extends PagingAndSortingRepository<OutDoorOrder,String>{
	OutDoorOrder findOneById(@Param(value = "id") String id) ;
	List<OutDoorOrder> findByClientId(@Param(value = "ClientId") String ClientId) ;
	List<OutDoorOrder> findByUserIdOrderByDateDesc(@Param(value = "UserId") Long UserId);
	List<OutDoorOrder> findByClientIdOrderByDateDesc(@Param(value = "ClientId") String ClientId);

	@Query ("SELECT count(id) FROM OutDoorOrder where client.id=?1")
	Long getNextOrderNumber(String id);
	
	List<OutDoorOrder> findByDateBetweenOrderByDate (Date dateS, Date dateE);
	List<OutDoorOrder> findByDateBetweenAndDivision_codeOrderByDate(Date date1, Date date2, String division_code);
	List<OutDoorOrder> findByDateBetweenAndClient_idOrderByDate(Date date1, Date date2, String Client_id);
	List<OutDoorOrder> findByDateBetweenAndDivision_codeAndClient_idOrderByDate(Date date1, Date date2, String division_code, String Client_id);

	List<OutDoorOrder> findByIdContainsAndDateBetweenOrderByDate(String id, Date date1, Date date2);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndDivision_codeOrderByDate(String id, Date date1, Date date2, String division_code);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndClient_idOrderByDate(String id, Date date1, Date date2, String Client_id);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndDivision_codeAndClient_idOrderByDate(String id, Date date1, Date date2, String division_code, String Client_id);
	
	List<OutDoorOrder> findByDateBetweenAndSampleIsTrueOrderByDate (Date dateS, Date dateE);
	List<OutDoorOrder> findByDateBetweenAndDivision_codeAndSampleIsTrueOrderByDate(Date date1, Date date2, String division_code);
	List<OutDoorOrder> findByDateBetweenAndClient_idAndSampleIsTrueOrderByDate(Date date1, Date date2, String Client_id);
	List<OutDoorOrder> findByDateBetweenAndDivision_codeAndClient_idAndSampleIsTrueOrderByDate(Date date1, Date date2, String division_code, String Client_id);

	List<OutDoorOrder> findByIdContainsAndDateBetweenAndSampleIsTrueOrderByDate(String id, Date date1, Date date2);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndDivision_codeAndSampleIsTrueOrderByDate(String id, Date date1, Date date2, String division_code);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndClient_idAndSampleIsTrueOrderByDate(String id, Date date1, Date date2, String Client_id);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndDivision_codeAndClient_idAndSampleIsTrueOrderByDate(String id, Date date1, Date date2, String division_code, String Client_id);
	
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndSampleIsFalseOrderByDate(String orderText, Date atStartOfDay,
			Date atEndOfDay);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndDivision_codeAndSampleIsFalseOrderByDate(String orderText,
			Date atStartOfDay, Date atEndOfDay, String code);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndClient_idAndSampleIsFalseOrderByDate(String orderText,
			Date atStartOfDay, Date atEndOfDay, String id);
	List<OutDoorOrder> findByDateBetweenAndDivision_codeAndClient_idAndSampleIsFalseOrderByDate(Date atStartOfDay,
			Date atEndOfDay, String code, String id);
	List<OutDoorOrder> findByDateBetweenAndDivision_codeAndSampleIsFalseOrderByDate(Date atStartOfDay, Date atEndOfDay,
			String code);
	List<OutDoorOrder> findByDateBetweenAndClient_idAndSampleIsFalseOrderByDate(Date atStartOfDay, Date atEndOfDay,
			String id);
	List<OutDoorOrder> findByDateBetweenAndSampleIsFalseOrderByDate(Date atStartOfDay, Date atEndOfDay);
	List<OutDoorOrder> findByIdContainsAndDateBetweenAndDivision_codeAndClient_idAndSampleIsFalseOrderByDate(
			String orderText, Date atStartOfDay, Date atEndOfDay, String code, String id);
	/* 1c */
	List<OutDoorOrder> findBySentToMasterDateIsNull();
	List<OutDoorOrder> findBySentToMasterDateIsNullAndDivision_code(String division_code);

	@Query("SELECT new hello.OutDoorOrder.OutDoorOrder(o.id, o.comment, o.date, o.division.code, o.user.id, o.client.id, o.sample) \n"+
		 	"FROM OutDoorOrder o, OutDoorOrderRow a, Product p \n"+
			"WHERE o.id=a.outDoorOrder.id AND a.product.id=p.id AND p.division.code != o.division.code \n"+
			"GROUP by o.id, o.comment, o.date, o.division.code, o.user.id, o.client.id, o.sample")
			List<OutDoorOrder>fixDifDiv();
	
	List<OutDoorOrder> findByUserIdAndDate(Long id, Date day);

}
