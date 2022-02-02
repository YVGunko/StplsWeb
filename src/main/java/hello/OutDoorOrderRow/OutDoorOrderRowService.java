package hello.OutDoorOrderRow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.OutDoorOrder.OutDoorOrder;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;
import hello.OutDoorOrder.OutDoorOrderRepository;

@Service
public class OutDoorOrderRowService {

	@Autowired
	OutDoorOrderRowRepository repository;
	
	@Autowired
	OutDoorOrderRepository orderRepository;
	public String getGoods (String order_id) {
		List<OutDoorOrderRow> rows = new ArrayList<>();
		String result = "";
		rows = repository.findByOutDoorOrderIdOrderByDtAsc(order_id);
		if (rows != null) {
			for (OutDoorOrderRow b : rows) {
				if (!result.equals("")) result = (result + ", ").trim();
				result = (result + b.getProduct().getName()).trim()+" р."+b.size.trim();
				if (result.length() >= 15) {
					result = result+"...";
					    break;
					  }
			}
		}
		return result;
	}
	
	public OutDoorOrderRowReq saveOrUpdate(OutDoorOrderRowReq request) throws Exception {
		OutDoorOrder outDoorOrder = orderRepository.findOneById(request.order_id);
		if (outDoorOrder == null)
			throw new Exception("Error while saving OutDoorOrderRow with id= " + request.id + ", no OutDoorOrder with id= " + request.order_id);

		/* OutDoorOrderRow((@NotNull String id, @NotNull OutDoorOrder outDoorOrder, String name, String attribute,
			Integer number, String barcode, Product product, String size, 
			Color color, Color liner, Color rant, Color shpalt, 
			Color vstavka, 
			Color gelenok, 
			Color guba, 
			Color kabluk, 
			Color m1, Color m2, Color m3,
			Color maska, 
			Color matirovka, 
			Color mcolor, 
			Color pechat, 
			Color proshiv, 
			Color pyatka, 
			Color sled,
			Color spoyler, 
			Color ashpalt, 
			Color other, 
			Boolean prodir, Boolean prokras, Boolean difersize, Boolean tert,
			Boolean frez, Boolean sample, Color plastizol)
		*/
		// OutDoorOrder (String id, String comment, Date date, 
		// String division_code, Long idUser, String clientId)
		OutDoorOrderRow tmp = repository.save(new OutDoorOrderRow(request.id, 
				new OutDoorOrder (outDoorOrder.id, outDoorOrder.getComment(), outDoorOrder.getDate(), 
						outDoorOrder.division.getCode(), outDoorOrder.user.getId(), outDoorOrder.client.getId(), outDoorOrder.getSample()), 
					(!(request.attribute == null)) ? request.attribute : "", 
						(!(request.number == null)) ? request.number : 0,
							(!(request.barcode == null)) ? request.barcode : "",
				(!(request.product_id == null)) ? request.product_id : "0", 
						request.size, 
					(!(request.color_id == null)) ? request.color_id : "0", 
						(!(request.liner_id == null)) ? request.liner_id : "0", 
							(!(request.rant_id == null)) ? request.rant_id : "0", 
								(!(request.shpalt_id == null)) ? request.shpalt_id : "0",
										
					(!(request.vstavka_id == null)) ? request.vstavka_id : "0",
						(!(request.gelenok_id == null)) ? request.gelenok_id : "0",
							(!(request.guba_id == null)) ? request.guba_id : "0",
								(!(request.kabluk_id == null)) ? request.kabluk_id : "0",
									(!(request.matirovka_id == null)) ? request.matirovka_id : "0",
											(!(request.pechat_id == null)) ? request.pechat_id : "0",
												(!(request.proshiv_id == null)) ? request.proshiv_id : "0",
														
				(!(request.pyatka_id == null)) ? request.pyatka_id : "0", 
						(!(request.sled_id == null)) ? request.sled_id : "0", 
							(!(request.spoyler_id == null)) ? request.spoyler_id : "0",
								(!(request.ashpalt_id == null)) ? request.ashpalt_id : "0",
										(!(request.prodir == null)) ? request.prodir : false,
														(!(request.difersize == null)) ? request.difersize : false,
																(!(request.tert == null)) ? request.tert : false,
																		(!(request.frez == null)) ? request.frez : false,
																				(!(request.sample == null)) ? request.sample : false,
				(!(request.plastizol_id == null)) ? request.plastizol_id : "0")
				);
		
		OutDoorOrderRowReq responce = new OutDoorOrderRowReq (tmp.id, tmp.outDoorOrder.id, 
				tmp.attribute, tmp.number, tmp.barcode, tmp.product.id, tmp.getProduct().getName(), tmp.size, 
				tmp.color.id, tmp.liner.id, tmp.rant.id, tmp.shpalt.id, 
				tmp.getColor().getName(), tmp.getLiner().getName(), tmp.getRant().getName(), tmp.getShpalt().getName(),
				tmp.vstavka.id, tmp.getVstavka().getName(), 
				tmp.gelenok.id, tmp.getGelenok().getName(), 
				tmp.guba.id, tmp.getGuba().getName(), 
				tmp.kabluk.id, tmp.getKabluk().getName(), 

				tmp.matirovka.id, tmp.getMatirovka().getName(), 

				tmp.pechat.id, tmp.getPechat().getName(), 
				tmp.proshiv.id, tmp.getProshiv().getName(), 
				tmp.pyatka.id, tmp.getPyatka().getName(), 
				tmp.sled.id, tmp.getSled().getName(), 
				tmp.spoyler.id, tmp.getSpoyler().getName(), 
				tmp.ashpalt.id, tmp.getAshpalt().getName(), 

				tmp.prodir, tmp.difersize, tmp.tert, tmp.frez, tmp.sample,
				tmp.plastizol.id, tmp.getPlastizol().getName()
				);
		
		return responce;
	}
}
