package hello.MasterData;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.OrderByOutDoc.OrderByOutDoc;

@RepositoryRestResource
public interface MasterDataRepository extends JpaRepository<MasterData,Long>{
	List<MasterData> findByDate1CGreaterThan(Date date);
	
	List<MasterData> findByDate1CBetweenOrderByDate1C(Date date1, Date date2);
	List<MasterData> findByDate1CBetweenAndDivision_codeOrderByDate1C(Date date1, Date date2, String division_code);
	List<MasterData> findByDate1CBetweenAndClient_idOrderByDate1C(Date date1, Date date2, String Client_id);
	List<MasterData> findByDate1CBetweenAndDivision_codeAndClient_idOrderByDate1C(Date date1, Date date2, String division_code, String Client_id);

	List<MasterData> findByOrderTextContainsAndDate1CBetweenOrderByDate1C(String orderText, Date date1, Date date2);
	List<MasterData> findByOrderTextContainsAndDate1CBetweenAndDivision_codeOrderByDate1C(String orderText, Date date1, Date date2, String division_code);
	List<MasterData> findByOrderTextContainsAndDate1CBetweenAndClient_idOrderByDate1C(String orderText, Date date1, Date date2, String Client_id);
	List<MasterData> findByOrderTextContainsAndDate1CBetweenAndDivision_codeAndClient_idOrderByDate1C(String orderText, Date date1, Date date2, String division_code, String Client_id);
	
	List<MasterData> findByDate1CGreaterThanAndDivision_code(Date date, String division_code);
	List<MasterData> findByDate1CGreaterThanAndDivision_codeAndArchive(Date date, String division_code, Boolean flag);
	
	List<MasterData> findByDtGreaterThan(Date date);
	
	List<MasterData> findByDtBetweenOrderByDt(Date date1, Date date2);
	List<MasterData> findByDtBetweenAndDivision_codeOrderByDt(Date date1, Date date2, String division_code);
	List<MasterData> findByDtBetweenAndClient_idOrderByDt(Date date1, Date date2, String Client_id);
	List<MasterData> findByDtBetweenAndDivision_codeAndClient_idOrderByDt(Date date1, Date date2, String division_code, String Client_id);

	List<MasterData> findByOrderTextContainsAndDtBetweenOrderByDt(String orderText, Date date1, Date date2);
	List<MasterData> findByOrderTextContainsAndDtBetweenAndDivision_codeOrderByDt(String orderText, Date date1, Date date2, String division_code);
	List<MasterData> findByOrderTextContainsAndDtBetweenAndClient_idOrderByDt(String orderText, Date date1, Date date2, String Client_id);
	List<MasterData> findByOrderTextContainsAndDtBetweenAndDivision_codeAndClient_idOrderByDt(String orderText, Date date1, Date date2, String division_code, String Client_id);
	
	List<MasterData> findByDtGreaterThanAndDivision_code(Date date, String division_code);
	List<MasterData> findByDtGreaterThanAndDivision_codeAndArchive(Date date, String division_code, Boolean flag);
	List<MasterData> findByDivision_code(String division_code);
	List<MasterData> findByArchive(Boolean flag);
	List<MasterData> findByOrderIdInAndArchive(ArrayList<String> orderId, boolean b);
	
	List<MasterData> findByOrderId(String orderId);
	List<MasterData> findByOrderIdNotIn(ArrayList<String> orderId);
	List<MasterData> findByid1C(String id1c);
	void deleteByid1CIn(ArrayList<String> id1c);

	Optional<MasterData> getByOrderId(@Param(value = "orderId") String orderId);
	MasterData findOneByOrderId(@Param(value = "orderId") String orderId);
	MasterData getByid1C(String id1c);
	List<MasterData> findDistinctByOrderTextAndCustomerAndDt(String orderText,String customer,String dt);

	
	@Query ("SELECT DISTINCT\n" + 
    		"        new hello.MasterData.MasterData(orderText,\n" + 
    		"        customer, \n" + 
    		"        CAST(dt AS date) as dt ) \n" + 
    		"    FROM\n" + 
    		"        MasterData\n" + 
    		"    ORDER BY dt")
	List<MasterData>findDistinct();
	
	@Query ("SELECT DISTINCT\n" + 
    		"        new hello.MasterData.MasterData(orderText,\n" + 
    		"        customer, \n" + 
    		"        CAST(dt AS date) as dt ) \n" + 
    		"    FROM\n" + 
    		"        MasterData\n" + 
    		"	WHERE\n" + 
    		"        client_id = ?1 \n" +
    		"    ORDER BY dt DESC")
	List<MasterData>findDistinctOrderByClient(String client_id);
	
	@Query ("SELECT \n" + 
    		"        new hello.MasterData.MasterData(customer,\n" + 
    		"        COUNT(DISTINCT orderText) as countProdInOrder ) \n" + 
    		"   FROM MasterData\n" + 
    		"	GROUP BY customer\n" + 
    		"	ORDER BY COUNT(orderText) DESC")
	List<MasterData>findCountOrders();
	
	@Query ("SELECT new hello.MasterData.MasterData(a.orderText, a.customer, CAST(a.dt AS date) as dt ) \n" + 
    		"FROM MasterData a, Box b, BoxMove m \n" + 
    		"WHERE a.division.code = ?1 and a.id = b.masterData and b.id = m.box and m.operation.id=9999 \n" +
    		"GROUP BY a.orderText, a.customer, CAST(a.dt AS date) \n" + 
    		"ORDER BY dt desc")
	List<MasterData>findNoteBookByDivision(String division_code);
	
	@Query ("SELECT new hello.MasterData.MasterData(a.orderText, a.customer, CAST(a.dt AS date) as dt ) \n" + 
    		"FROM MasterData a, Box b, BoxMove m \n" + 
    		"WHERE a.division.code = ?1 and CAST(a.dt AS date) = ?2 and a.id = b.masterData and b.id = m.box and m.operation.id=9999 \n" +
    		"GROUP BY a.orderText, a.customer, CAST(a.dt AS date) \n" + 
    		"ORDER BY dt desc")
	List<MasterData>findNoteBookByDivisionAndOrderDate(String division_code, Date orderDate);
	List<MasterData> findByIdIn(List<String> bIdList);
	
	List<MasterData> findByIdInOrderByOrderTextAscNomenklatureAsc(List<Long> mList);
	List<MasterData> findByColorId(String id);
	List<MasterData> findByLinerId(String id);
	List<MasterData> findByRantId(String idToRemove);
	List<MasterData> findByShpaltId(String idToRemove);

	List<MasterData> findByVstavkaId(String idToRemove);

	List<MasterData> findByGelenokId(String idToRemove);

	List<MasterData> findByGubaId(String idToRemove);

	List<MasterData> findByKablukId(String idToRemove);

	List<MasterData> findByPlastizol2Id(String idToRemove);

	List<MasterData> findByPlastizolId(String idToRemove);

	List<MasterData> findByAshpaltId(String idToRemove);

	List<MasterData> findBySpoylerId(String idToRemove);

	List<MasterData> findBySledId(String idToRemove);

	List<MasterData> findByPyatkaId(String idToRemove);

	List<MasterData> findByProshivId(String idToRemove);

	List<MasterData> findByPechatId(String idToRemove);

	List<MasterData> findByMatirovkaId(String idToRemove);

	List<MasterData> findByArchiveAndDtLessThan(boolean b, Date orderDate);
	
	@Query(value="select id from master_data \n"+
			" where order_id = ?1", nativeQuery=true)
		    Optional<BigInteger> selectIdByOrderId(String Id);

}
