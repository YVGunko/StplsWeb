package hello.OutDoorOrderRow;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hello.MasterData.Color;
import hello.MasterData.Product;
import hello.OutDoorOrder.ClientRep;
import hello.OutDoorOrder.OutDoorOrder;
import hello.OutDoorOrderRow.OutDoorOrderRow;

@Repository
public interface OutDoorOrderRowRepository extends JpaRepository<OutDoorOrderRow,String>{

	OutDoorOrderRow findOneById(@Param(value = "id") String id);
	List<OutDoorOrderRow> findByOutDoorOrderIdOrderByDtDesc(@Param(value = "order_id") String order_id);
	List<OutDoorOrderRow> findByOutDoorOrderIdOrderByDtAsc(@Param(value = "order_id") String order_id);
	List<OutDoorOrderRow> findByOutDoorOrderIdIn(@Param(value = "order_id") List<String> order_id);
	
	@Query ("SELECT new hello.OutDoorOrder.ClientRep(c.id, c.name, sum(a.number) as number) \n" + 
    		"FROM OutDoorOrder o, OutDoorOrderRow a, Client c\n" + 
    		"WHERE o.sample is true AND (o.date BETWEEN :d1 AND :d2) AND o.id=a.outDoorOrder.id AND a.product.name =:path AND o.client.id=c.id  \n" +
    		"GROUP BY c.name, c.id")
	List<ClientRep>findByProductAndOutDoorOrderIdInGroupByClient(@Param("d1") Date dateS, @Param("d2") Date dateE, @Param("path") String name);
	
	@Query ("SELECT new hello.OutDoorOrderRow.OutDoorOrderRow(a.product.name, a.product.id, a.color.id, a.color.name,\n"+
			"a.liner.id, a.liner.name, a.rant.id,a.rant.name, a.shpalt.id,a.shpalt.name, sum(number) as number) \n" + 
    		"FROM OutDoorOrderRow a\n" + 
    		"WHERE a.outDoorOrder.id IN (SELECT id FROM OutDoorOrder WHERE sample is true AND date BETWEEN :d1 AND :d2) AND a.product.name =:path  \n" +
    		"GROUP BY a.product.name, a.product.id, a.color.id, a.color.name, a.liner.id, a.liner.name, a.rant.id,a.rant.name, a.shpalt.id,a.shpalt.name \n" +
    		"ORDER BY a.product.name")
	List<OutDoorOrderRow>findByOutDoorOrderIdInGrouped(@Param("d1") Date dateS, @Param("d2") Date dateE, @Param("path") String name);
	
	@Query ("SELECT new hello.OutDoorOrderRow.OutDoorOrderRow(a.product.name, a.product.id, sum(number) as number) \n" + 
    		"FROM OutDoorOrderRow a\n" + 
    		"WHERE a.outDoorOrder.id IN (SELECT id FROM OutDoorOrder WHERE sample is true AND date BETWEEN ?1 AND ?2)  \n" +
    		"GROUP BY a.product.name, a.product.id \n" +
		"ORDER BY a.product.name")
	List<OutDoorOrderRow>findByOutDoorOrderIdInGroupByProduct(Date dateS, Date dateE);
	
	@Query ("SELECT new hello.OutDoorOrder.ClientRep(c.id, c.name, sum(a.number) as number) \n" + 
    		"FROM OutDoorOrder o, OutDoorOrderRow a, Client c\n" + 
    		"WHERE o.sample is true AND (o.date BETWEEN :d1 AND :d2) AND o.id=a.outDoorOrder.id AND o.client.id=c.id  \n" +
    		"GROUP BY c.name, c.id")
	List<ClientRep> findByOutDoorOrderIdInGroupByClient(@Param("d1") Date atStartOfDay, @Param("d2") Date atEndOfDay);
	
	@Query ("SELECT new hello.OutDoorOrderRow.OutDoorOrderRow(a.product.name, a.product.id, a.color.id, a.color.name,\n"+
			"a.liner.id, a.liner.name, a.rant.id,a.rant.name, a.shpalt.id,a.shpalt.name, sum(number) as number) \n" + 
    		"FROM OutDoorOrderRow a\n" + 
    		"WHERE a.outDoorOrder.id IN (SELECT id FROM OutDoorOrder WHERE sample is true AND client.id=:cl AND date BETWEEN :d1 AND :d2) \n" +
    		" AND a.product.name =:path  \n" +
    		"GROUP BY a.product.name, a.product.id, a.color.id, a.color.name, a.liner.id, a.liner.name, a.rant.id,a.rant.name, a.shpalt.id,a.shpalt.name \n" +
    		"ORDER BY a.product.name")	
	List<OutDoorOrderRow>findByOutDoorOrderIdInGroupByProductAndClient(@Param("d1") Date dateS, @Param("d2") Date dateE,
			@Param("path") String name, @Param("cl") String id);
	
	@Query ("SELECT new hello.OutDoorOrderRow.OutDoorOrderRow(a.product.name, a.product.id, sum(number) as number) \n" + 
    		"FROM OutDoorOrderRow a\n" + 
    		"WHERE a.outDoorOrder.id IN (SELECT id FROM OutDoorOrder WHERE sample is true AND client.id=:cl AND date BETWEEN :d1 AND :d2)  \n" +
    		"GROUP BY a.product.name, a.product.id \n" +
		"ORDER BY a.product.name")
	List<OutDoorOrderRow> findByClientAndOutDoorOrderIdInGroupByProduct(@Param("d1") Date atStartOfDay, @Param("d2") Date atEndOfDay,
			@Param("cl") String id);
	
	@Query ("SELECT new hello.OutDoorOrder.ClientRep(c.id, c.name, sum(a.number) as number) \n" + 
    		"FROM OutDoorOrder o, OutDoorOrderRow a, Client c\n" + 
    		"WHERE o.sample is true AND (o.date BETWEEN :d1 AND :d2) AND o.id=a.outDoorOrder.id AND o.client.id=c.id AND a.product.name =:pname \n" +
    		"GROUP BY c.name, c.id")
	List<ClientRep> findClientsByProductAndOutDoorOrderIdIn(@Param("d1") Date atStartOfDay, @Param("d2") Date atEndOfDay, @Param("pname") String name);
	
	@Query ("SELECT new hello.OutDoorOrder.ClientRep(c.id, c.name, sum(a.number) as number) \n" +
		"FROM OutDoorOrder o, OutDoorOrderRow a, Client c\n" + 
		"WHERE o.sample is true AND (o.date BETWEEN :d1 AND :d2) AND o.id=a.outDoorOrder.id AND o.client.id=c.id AND a.product.name =:pname \n" +
    		" AND a.color.name =:color AND a.liner.name =:liner AND a.rant.name =:rant AND a.shpalt.name =:shpalt  \n" +
		"GROUP BY c.name, c.id")	
	List<ClientRep>findClientsByProductAndReq(@Param("d1") Date dateS, @Param("d2") Date dateE,
			@Param("pname") String name, @Param("color") String color, @Param("liner") String liner, @Param("rant") String rant, @Param("shpalt") String shpalt);
	
	
	List<OutDoorOrderRow> findByColorId(String idToRemove);
	List<OutDoorOrderRow> findByLinerId(String idToRemove);
	List<OutDoorOrderRow> findByRantId(String idToRemove);
	List<OutDoorOrderRow> findByShpaltId(String idToRemove);
	List<OutDoorOrderRow> findByVstavkaId(String idToRemove);
	List<OutDoorOrderRow> findByGelenokId(String idToRemove);
	List<OutDoorOrderRow> findByGubaId(String idToRemove);
	List<OutDoorOrderRow> findByKablukId(String idToRemove);
	List<OutDoorOrderRow> findByMatirovkaId(String idToRemove);
	List<OutDoorOrderRow> findByPechatId(String idToRemove);
	List<OutDoorOrderRow> findByProshivId(String idToRemove);
	List<OutDoorOrderRow> findByPyatkaId(String idToRemove);
	List<OutDoorOrderRow> findBySledId(String idToRemove);
	List<OutDoorOrderRow> findBySpoylerId(String idToRemove);
	List<OutDoorOrderRow> findByAshpaltId(String idToRemove);
	List<OutDoorOrderRow> findByPlastizolId(String idToRemove);
	//List<OutDoorOrderRow> findByPlastizol2Id(String idToRemove);
	
	/*@Query ("SELECT new hello.OutDoorOrder.ClientRep(c.id, c.name, sum(a.number) as number) \n" +
    		"FROM OutDoorOrderRow a\n" + 
    		"WHERE a.outDoorOrder.id IN (SELECT id FROM OutDoorOrder WHERE sample is true AND client.id=:cl AND date BETWEEN :d1 AND :d2) \n" +
    		" AND a.product.name =:path AND a.color.name =:color AND a.liner.name =:liner AND a.rant.name =:rant AND a.shpalt.name =:shpalt  \n" +
		"GROUP BY c.name, c.id")	
	List<ClientRep>findByProductAndClientAndReq(@Param("d1") Date dateS, @Param("d2") Date dateE,
			@Param("path") String name, @Param("cl") String id
			, @Param("color") String color, @Param("liner") String liner, @Param("rant") String rant, @Param("shpalt") String shpalt);*/

}
