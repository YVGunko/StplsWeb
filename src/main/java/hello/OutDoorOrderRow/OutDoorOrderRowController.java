package hello.OutDoorOrderRow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.OutDoorOrderRow.OutDoorOrderRowService;
import hello.OutDoorOrderRow.OutDoorOrderRow;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;
import hello.OutDoorOrderRow.OutDoorOrderRowReq;

@RestController
public class OutDoorOrderRowController {
	@Autowired
	OutDoorOrderRowService service;
	@Autowired
	OutDoorOrderRowRepository repository;
	
	@PostMapping("/login/outDoorOrderRows/saveOrUpdate") 
	public OutDoorOrderRowReq SaveOrUpdate(@RequestBody OutDoorOrderRowReq request) throws Exception{

		return service.saveOrUpdate(request);
	}
    @DeleteMapping("/login/outDoorOrderRows/delRow")
    public void delete(@RequestParam(value="id", required=true) String id) throws Exception {
    		repository.deleteById(id);
    }
	@GetMapping("/login/outDoorOrderRows") 
	public List<OutDoorOrderRow> get() throws Exception{

		return repository.findAll();
	}
	@GetMapping("/login/outDoorOrderRows/getReqs") 
	public List<OutDoorOrderRowReq> getReqs(@RequestParam(value="order_id", required=true) String order_id) throws Exception{
		List<OutDoorOrderRow> tmp = repository.findByOutDoorOrderIdOrderByDtDesc(order_id);
		List<OutDoorOrderRowReq> responce = new ArrayList<>();
		// OutDoorOrderRow(String id, @NotNull OutDoorOrder outDoorOrder, String name, String attribute, Integer number, String barcode)
		if (!tmp.isEmpty()) {
			for (OutDoorOrderRow b : tmp) {
				responce.add (new OutDoorOrderRowReq (b.id, b.outDoorOrder.id, 
						b.attribute, b.number, b.barcode, b.product.id, b.getProduct().getName(), b.size,
						b.color.id, b.liner.id, b.rant.id, b.shpalt.id, 
						b.getColor().getName(), b.getLiner().getName(), b.getRant().getName(), b.getShpalt().getName(), 
						b.vstavka.id, b.getVstavka().getName(), 
						b.gelenok.id, b.getGelenok().getName(), 
						b.guba.id, b.getGuba().getName(), 
						b.kabluk.id, b.getKabluk().getName(), 

						b.matirovka.id, b.getMatirovka().getName(), 

						b.pechat.id, b.getPechat().getName(), 
						b.proshiv.id, b.getProshiv().getName(), 
						b.pyatka.id, b.getPyatka().getName(), 
						b.sled.id, b.getSled().getName(), 
						b.spoyler.id, b.getSpoyler().getName(), 
						b.ashpalt.id, b.getAshpalt().getName(), 

						b.prodir, b.difersize, b.tert, b.frez, b.sample, 
						b.plastizol.id, b.getPlastizol().getName()));
				/*OutDoorOrderRowReq(String id, String order_id, String name, String attribute, Integer number, String barcode,
						String product_id, String size, String color_id, String liner_id, String shpalt_id, String rant_id,
						String sColor, String sLiner, String sShpalt, String sRant, String vstavka_id, String sVstavka,
						String gelenok_id, String sGelenok, String guba_id, String sGuba, String kabluk_id, String sKabluk,
						String m1_id, String sM1, String m2_id, String sM2, String m3_id, String sM3, String maska_id,
						String sMaska, String matirovka_id, String sMatirovka, String mcolor_id, String sMcolor, String pechat_id,
						String sPechat, String proshiv_id, String sProshiv, String pyatka_id, String sPyatka, String sled_id,
						String sSled, String spoyler_id, String sSpoyler, String ashpalt_id, String sAshpalt, String other_id, String sOther,
						Boolean prodir, Boolean prokras, Boolean difersize, Boolean tert, Boolean frez, Boolean sample)*/
			}
		}
		return responce;
	}
	@GetMapping("/outDoorOrderRows/export1c") 
	public List<OutDoorOrderRowReq> export1c(@RequestParam(value="order_id", required=true) String order_id) throws Exception{
		List<OutDoorOrderRow> tmp = repository.findByOutDoorOrderIdOrderByDtDesc(order_id);
		List<OutDoorOrderRowReq> responce = new ArrayList<>();
		// OutDoorOrderRow(String id, @NotNull OutDoorOrder outDoorOrder, String name, String attribute, Integer number, String barcode)
		if (!tmp.isEmpty()) {
			for (OutDoorOrderRow b : tmp) {
				responce.add (new OutDoorOrderRowReq (b.id, b.outDoorOrder.id, 
						b.attribute, b.number, b.barcode, b.product.id, b.getProduct().getName(), b.size,
						b.color.id, b.liner.id, b.rant.id, b.shpalt.id, 
						b.getColor().getName(), b.getLiner().getName(), b.getRant().getName(), b.getShpalt().getName(), 
						b.vstavka.id, b.getVstavka().getName(), 
						b.gelenok.id, b.getGelenok().getName(), 
						b.guba.id, b.getGuba().getName(), 
						b.kabluk.id, b.getKabluk().getName(), 

						b.matirovka.id, b.getMatirovka().getName(), 

						b.pechat.id, b.getPechat().getName(), 
						b.proshiv.id, b.getProshiv().getName(), 
						b.pyatka.id, b.getPyatka().getName(), 
						b.sled.id, b.getSled().getName(), 
						b.spoyler.id, b.getSpoyler().getName(), 
						b.ashpalt.id, b.getAshpalt().getName(), 

						b.prodir, b.difersize, b.tert, b.frez, b.sample, 
						b.plastizol.id, b.getPlastizol().getName()));
				/*OutDoorOrderRowReq(String id, String order_id, String name, String attribute, Integer number, String barcode,
						String product_id, String size, String color_id, String liner_id, String shpalt_id, String rant_id,
						String sColor, String sLiner, String sShpalt, String sRant, String vstavka_id, String sVstavka,
						String gelenok_id, String sGelenok, String guba_id, String sGuba, String kabluk_id, String sKabluk,
						String m1_id, String sM1, String m2_id, String sM2, String m3_id, String sM3, String maska_id,
						String sMaska, String matirovka_id, String sMatirovka, String mcolor_id, String sMcolor, String pechat_id,
						String sPechat, String proshiv_id, String sProshiv, String pyatka_id, String sPyatka, String sled_id,
						String sSled, String spoyler_id, String sSpoyler, String ashpalt_id, String sAshpalt, String other_id, String sOther,
						Boolean prodir, Boolean prokras, Boolean difersize, Boolean tert, Boolean frez, Boolean sample)*/
			}
		}
		return responce;
	}
	@GetMapping("/login/outDoorOrderRows/getReq") 
	public OutDoorOrderRowReq getReq(@RequestParam(value="id", required=true) String id) throws Exception{
		OutDoorOrderRow b = repository.findOneById(id);
		OutDoorOrderRowReq responce = new OutDoorOrderRowReq();
		if (b != null) {
			/* 	public OutDoorOrderRowReq(String id, String order_id, String name, String attribute, Integer number, String barcode,
			String product_id, String size, 
			String color_id, String liner_id, String rant_id, String shpalt_id, 
			String sColor, String sLiner, String sRant, String sShpalt, 
			String vstavka_id, String sVstavka,
			String gelenok_id, String sGelenok, 
			String guba_id, String sGuba, 
			String kabluk_id, String sKabluk,
			String m1_id, String sM1, 
			String m2_id, String sM2, 
			String m3_id, String sM3, 
			String maska_id, 	String sMaska, 
			String matirovka_id, String sMatirovka, 
			String mcolor_id, String sMcolor, 
			String pechat_id, String sPechat, 
			String proshiv_id, String sProshiv, 
			String pyatka_id, String sPyatka, 
			String sled_id,		String sSled, 
			String spoyler_id, String sSpoyler, 
			String ashpalt_id, String sAshpalt, 
			String other_id, String sOther,
			Boolean prodir, Boolean prokras, Boolean difersize, Boolean tert, Boolean frez, Boolean sample)
			 */
				responce = new OutDoorOrderRowReq (b.id, b.outDoorOrder.id, 
						b.attribute, b.number, b.barcode, b.product.id, b.getProduct().getName(), b.size,
						b.color.id, b.liner.id, b.rant.id, b.shpalt.id, 
						b.getColor().getName(), b.getLiner().getName(), b.getRant().getName(), b.getShpalt().getName(),
						b.vstavka.id, b.getVstavka().getName(), 
						b.gelenok.id, b.getGelenok().getName(), 
						b.guba.id, b.getGuba().getName(), 
						b.kabluk.id, b.getKabluk().getName(), 

						b.matirovka.id, b.getMatirovka().getName(), 

						b.pechat.id, b.getPechat().getName(), 
						b.proshiv.id, b.getProshiv().getName(), 
						b.pyatka.id, b.getPyatka().getName(), 
						b.sled.id, b.getSled().getName(), 
						b.spoyler.id, b.getSpoyler().getName(), 
						b.ashpalt.id, b.getAshpalt().getName(), 

						b.prodir, b.difersize, b.tert, b.frez, b.sample, 
						b.plastizol.id, b.getPlastizol().getName());
			}
		return responce;
	}
}
