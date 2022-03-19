package hello.Controller;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.Device.DeviceService;
import hello.LogGournal.LogGournalService;
import hello.MasterData.Color;
import hello.MasterData.ColorReq;
import hello.MasterData.ColorRepository;
import hello.MasterData.MasterData;
import hello.MasterData.MasterDataRepository;
import hello.MasterData.MasterDataService;
import hello.MasterData.ReqByQr;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.OutDoorOrder.OutDoorOrderReq;
import hello.OutDoorOrderRow.OutDoorOrderRow;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;
import hello.OutDoorOrderRow.OutDoorOrderRowReq;
import hello.OutDoorOrderRow.OutDoorOrderRowService;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;
import hello.Price.Price;
import hello.Price.PriceColumn;
import hello.Price.PriceColumnRepository;
import hello.Price.PriceRepository;
import hello.Price.PriceService;
import hello.Price.PriceType;
import hello.Price.PriceTypeRepository;
import hello.Price.PriceWeb;
import hello.User.UserRepository;
import hello.User.UserService;
import hello.Web.Order.OrderGroupReq;
import hello.Web.Order.OrderMoveRep;
import hello.utils;

@RestController
public class OrderController {
	private int pageSize = 1000;
	
		@Autowired
		MasterDataRepository masterDataRepository;
		@Autowired
		MasterDataService masterDataService;
		@Autowired
		ColorRepository colorRepository;
		@Autowired
		BoxRepository boxRepository;	
		@Autowired
		BoxMovesRepository boxMovesRepository;
		@Autowired
		PartBoxRepository partBoxRepository;	
		@Autowired
		PartBoxService partBoxService;	
		@Autowired
		UserRepository userRepository;
		@Autowired
		UserService uService;	
		@Autowired
		private LogGournalService lgService;
		@Autowired
		DeviceService deviceService;
		@Autowired
		OperationRepository operationRepository;
		@Autowired
		PriceRepository repository;
		@Autowired
		PriceService service;
		@Autowired
		PriceTypeRepository priceTypeRepository;
		@Autowired
		PriceColumnRepository priceColumnRepository;
		@Autowired
		OutDoorOrderRowRepository outDoorOrderRowRepository;
		@Autowired
		OutDoorOrderRowService outDoorOrderRowService;
		
		/* price */
		@GetMapping("/deleteColorDubles") 
		public int deleteColorDubles(@RequestParam(value="idToRemove", required=true) String idToRemove,
				@RequestParam(value="idToReplace", required=true) String idToReplace) throws Exception{
		
			Color tmp = colorRepository.findOneById(idToRemove);
			Color newColor = colorRepository.findOneById(idToReplace);
			int responce = 0;
			List<MasterData> mdList = new ArrayList<>();
			List<OutDoorOrderRow> orList = new ArrayList<>();

			if ((tmp != null)&(newColor != null)) {

					Field[] fields = MasterData.class.getFields();
					for(Field f : fields)
					{
					  //Class<?> k = f.getType();
					  if (f.getType().equals(Color.class)) {
						  //System.out.println(f.getName().toString());
						  
						  if (f.getName().toString()=="color") {
							  mdList = masterDataRepository.findByColorId(idToRemove);
							  orList = outDoorOrderRowRepository.findByColorId(idToRemove);
						  }
						  if (f.getName().toString()=="liner") {
							  mdList = masterDataRepository.findByLinerId(idToRemove);
							  orList = outDoorOrderRowRepository.findByLinerId(idToRemove);
						  }
						  if (f.getName().toString()=="rant") {
							  mdList = masterDataRepository.findByRantId(idToRemove);
							  orList = outDoorOrderRowRepository.findByRantId(idToRemove);
						  }
						  if (f.getName().toString()=="shpalt") {
							  mdList = masterDataRepository.findByShpaltId(idToRemove);
							  orList = outDoorOrderRowRepository.findByShpaltId(idToRemove);
						  }
						  if (f.getName().toString()=="vstavka") {
							  mdList = masterDataRepository.findByVstavkaId(idToRemove);
							  orList = outDoorOrderRowRepository.findByVstavkaId(idToRemove);
						  }
						  if (f.getName().toString()=="gelenok") {
							  mdList = masterDataRepository.findByGelenokId(idToRemove);
							  orList = outDoorOrderRowRepository.findByGelenokId(idToRemove);
						  }
						  if (f.getName().toString()=="guba") {
							  mdList = masterDataRepository.findByGubaId(idToRemove);
							  orList = outDoorOrderRowRepository.findByGubaId(idToRemove);
						  }
						  if (f.getName().toString()=="kabluk") {
							  mdList = masterDataRepository.findByKablukId(idToRemove);
							  orList = outDoorOrderRowRepository.findByKablukId(idToRemove);
						  }
						  if (f.getName().toString()=="matirovka") {
							  mdList = masterDataRepository.findByMatirovkaId(idToRemove);
							  orList = outDoorOrderRowRepository.findByMatirovkaId(idToRemove);
						  }
						  if (f.getName().toString()=="pechat") {
							  mdList = masterDataRepository.findByPechatId(idToRemove);
							  orList = outDoorOrderRowRepository.findByPechatId(idToRemove);
						  }
						  if (f.getName().toString()=="proshiv") {
							  mdList = masterDataRepository.findByProshivId(idToRemove);
							  orList = outDoorOrderRowRepository.findByProshivId(idToRemove);
						  }
						  if (f.getName().toString()=="pyatka") {
							  mdList = masterDataRepository.findByPyatkaId(idToRemove);
							  orList = outDoorOrderRowRepository.findByPyatkaId(idToRemove);
						  }
						  if (f.getName().toString()=="sled") {
							  mdList = masterDataRepository.findBySledId(idToRemove);
							  orList = outDoorOrderRowRepository.findBySledId(idToRemove);
						  }
						  if (f.getName().toString()=="spoyler") {
							  mdList = masterDataRepository.findBySpoylerId(idToRemove);
							  orList = outDoorOrderRowRepository.findBySpoylerId(idToRemove);
						  }
						  if (f.getName().toString()=="ashpalt") {
							  mdList = masterDataRepository.findByAshpaltId(idToRemove);
							  orList = outDoorOrderRowRepository.findByAshpaltId(idToRemove);
						  }
						  if (f.getName().toString()=="plastizol") {
							  mdList = masterDataRepository.findByPlastizolId(idToRemove);
							  orList = outDoorOrderRowRepository.findByPlastizolId(idToRemove);
						  }
						  if (f.getName().toString()=="plastizol2") {
							  mdList = masterDataRepository.findByPlastizol2Id(idToRemove);
							  //orList = outDoorOrderRowRepository.findByPlastizol2Id(idToRemove);
						  }
						  
						  for (MasterData b : mdList) {
							  if (f.getName().toString()=="color") b.setColor(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="liner") b.setLiner(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="rant") b.setRant(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="shpalt") b.setShpalt(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="vstavka") b.setVstavka(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="gelenok") b.setGelenok(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="guba") b.setGuba(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="kabluk") b.setKabluk(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="matirovka") b.setMatirovka(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="pechat") b.setPechat(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="proshiv") b.setProshiv(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="pyatka") b.setPyatka(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="sled") b.setSled(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="spoyler") b.setSpoyler(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="ashpalt") b.setAshpalt(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="plastizol") b.setPlastizol(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="plastizol") b.setPlastizol2(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  
							  masterDataService.saveOrUpdate(b);
							  responce++;
							}
						  for (OutDoorOrderRow b : orList) {
							  if (f.getName().toString()=="color") b.setColor(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="liner") b.setLiner(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="rant") b.setRant(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="shpalt") b.setShpalt(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="vstavka") b.setVstavka(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="gelenok") b.setGelenok(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="guba") b.setGuba(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="kabluk") b.setKabluk(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="matirovka") b.setMatirovka(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="pechat") b.setPechat(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="proshiv") b.setProshiv(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="pyatka") b.setPyatka(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="sled") b.setSled(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="spoyler") b.setSpoyler(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="ashpalt") b.setAshpalt(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  if (f.getName().toString()=="plastizol") b.setPlastizol(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  //if (f.getName().toString()=="plastizol") b.setPlastizol2(new Color(newColor.id, newColor.name, newColor.division, newColor.occurrence));
							  
							  /*String id, String order_id, String attribute, Integer number, String barcode,
								String product_id, String size, 
								String color_id, String liner_id, String rant_id, String shpalt_id, 
								String vstavka_id, 
								String gelenok_id,  
								String guba_id, 
								String kabluk_id, 
								String matirovka_id, 
								String pechat_id, 
								String proshiv_id,  
								String pyatka_id,  
								String sled_id,	 
								String spoyler_id, 
								String ashpalt_id, 
								Boolean prodir, Boolean difersize, Boolean tert, Boolean frez, Boolean sample,
								String plastizol_id
														   * */
							  outDoorOrderRowService.saveOrUpdate( new OutDoorOrderRowReq (
									    	b.id, b.outDoorOrder.id, b.attribute, b.number, b.barcode, 
									    	b.product.id, b.size, b.color.id, b.liner.id, b.rant.id, b.shpalt.id, 
										b.vstavka.id, b.gelenok.id, b.guba.id, b.kabluk.id, 
										b.matirovka.id, b.pechat.id, b.proshiv.id, 
										b.pyatka.id, b.sled.id, b.spoyler.id, 
										b.ashpalt.id, 
										b.prodir, b.difersize, b.tert, b.frez, b.sample,
										b.plastizol.id));
							  responce++;
							}
					  }
					  /*if (k.getName().contains("Color")) {
						  System.out.println("/deleteColorDubles k: " + k.getName());
						  System.out.println("/deleteColorDubles f: " + f.getName().);
					  // depending on k, methods like f.getInt(yourObject),
					  // f.getFloat(yourObject), 
					  // f.getObject(hourObject) to get each member.
					  }*/
					}
					colorRepository.delete(tmp);
					//TODO for debug purpose. remove
					System.out.println("/deleteColorDubles responce: " + tmp.name + ", " + newColor.name);
				}
			return responce;
		}
		/* price */
		@CrossOrigin
		@GetMapping("/login/outDoorPrice/price/getReqs") 
		public List<Price> getReqs(@RequestParam(value="PriceTypeId", required=true) Integer id) throws Exception{
			List<Price> responce = new ArrayList<>();
			if (id==0)
				responce = repository.findByOrderByDateOfLastChangeDesc();
			else
				responce = repository.findByPriceTypeIdOrderByDateOfLastChangeDesc(id);
			
			return responce;
		}
		/* price */
		@CrossOrigin
		@GetMapping("/login/outDoorPrice/price/getRec") 
		public Price getPriceRec(@RequestParam(value="id", required=true) Integer id) throws Exception{
			Price responce = repository.findOneById(id);

			return responce;
		}
		@CrossOrigin
		@PostMapping("/login/outDoorPrice/price/saveOrUpdate") 
		public Price SaveOrUpdate(@RequestBody Price request) throws Exception{
			//service.sendMail(request, "Изменено наименование");
			return service.save(request);
		}
		/* priceType */
		@CrossOrigin
		@GetMapping("/login/outDoorPrice/priceType/getReqs") 
		public List<PriceType> getReqs() throws Exception{
			return priceTypeRepository.findAll(Sort.by("name"));
		}
		/* priceColumn */
		@CrossOrigin
		@GetMapping("/login/outDoorPrice/priceColumn/getReqs") 
		public List<PriceColumn> getRecs(@RequestParam(value="id", required=true) Integer id) throws Exception{
			return priceColumnRepository.findByPriceId(id);	
		}
		@CrossOrigin
		@GetMapping("/login/outDoorPrice/priceColumn/getRec") 
		public Optional<PriceColumn> getPriceColumnRec(@RequestParam(value="id", required=true) Integer id) throws Exception{
			return priceColumnRepository.findById(id);	
		}
		@CrossOrigin
		@PostMapping("/login/outDoorPrice/priceColumn/saveAndUpdate") 
		public PriceColumn saveAndUpdateRec(@RequestParam(value="record", required=true) PriceColumn record) throws Exception{
			return priceColumnRepository.save(record);
		}
		/* price */
		
	    @GetMapping(path="/login/getOneByQrCode")
	    public ReqByQr getOneByQr(@RequestParam(value="orderId", required=true) String orderId) throws Exception {
	    	MasterData tmp = new MasterData();
	    	ReqByQr result = new ReqByQr();
		    	int pos = 0;
		    	
		    	if (orderId.length() > 20) {
						
			    	pos = orderId.lastIndexOf(".", orderId.length());
			    	pos = orderId.lastIndexOf(".", pos-1);
			    	
			    	tmp = masterDataRepository.findOneByOrderId(orderId.substring(0, pos));
			    	if (tmp != null) {
			    		
			    		result.division_code = tmp.getDivision().getCode();
			    		result.barcode = tmp.orderId;
			    		result.attribute = tmp.other;
			    		result.product_id = tmp.getProduct().getId();
			    		result.sProduct = tmp.getProduct().getName();
			    		result.size = tmp.size;
			    		result.color_id = tmp.getColor().getId();
			    		result.sColor = tmp.getColor().getName();
			    		result.rant_id = tmp.getRant().getId();
			    		result.sLiner = tmp.getRant().getName();
			    		result.liner_id = tmp.getLiner().getId();
			    		result.sRant = tmp.getLiner().getName();
			    		result.shpalt_id = tmp.getShpalt().getId();
			    		result.sShpalt = tmp.getShpalt().getName();
			    		result.vstavka_id =	tmp.vstavka.id;
			    		result.sVstavka = tmp.getVstavka().getName(); 
			    		result.gelenok_id =	tmp.gelenok.id;
			    		result.sGelenok = tmp.getGelenok().getName();
			    		result.guba_id =	tmp.guba.id;
			    		result.sGuba =	tmp.getGuba().getName();
			    		result.kabluk_id =	tmp.kabluk.id;
			    		result.sKabluk =	tmp.getKabluk().getName(); 
			    		result.matirovka_id =	tmp.matirovka.id;
			    		result.sMatirovka =	tmp.getMatirovka().getName(); 

			    		result.pechat_id =		tmp.pechat.id;
			    		result.sPechat =	tmp.getPechat().getName(); 
			    		result.proshiv_id =		tmp.proshiv.id;
			    		result.sProshiv =	tmp.getProshiv().getName(); 
			    		result.pyatka_id =		tmp.pyatka.id;
			    		result.sPyatka =	tmp.getPyatka().getName(); 
			    		result.sled_id =		tmp.sled.id;
			    		result.sSled =	tmp.getSled().getName(); 
			    		result.spoyler_id =		tmp.spoyler.id;
			    		result.sSpoyler =	tmp.getSpoyler().getName(); 
			    		result.ashpalt_id =		tmp.ashpalt.id;
			    		result.sAshpalt =	tmp.getAshpalt().getName(); 

			    		result.prodir =		tmp.prodir;
			    		result.difersize =	tmp.difersize;
			    		result.tert =	tmp.tert;
			    		result.frez =	tmp.frez;
			    		result.sample =	tmp.sample;
			    	}
		    		return result;
		    	} else throw new RuntimeException ("no such orderId in MasterData: " + orderId);
	    }
	    
	    @GetMapping(path="/distinctOrder")
	    public List<MasterData> getDistinctOrders() throws RuntimeException{
	    	return masterDataRepository.findDistinct();
	    }
	    @GetMapping(path="/getDistinctOrderByClient")
	    public List<MasterData> getDistinctOrders(@RequestParam(value= "client_id", required=true) String client_id) throws RuntimeException{
	    	return masterDataRepository.findDistinctOrderByClient(client_id);
	    }
	    
	    @GetMapping(path="/countOrder")
	    public List<MasterData> getCountOrders() throws RuntimeException{
	    	return masterDataRepository.findCountOrders();
	    }

	    @GetMapping("/orderUser") 
		public List<OrderReq> getOrdersUser(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
				@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
	    		List<OrderReq> result = new ArrayList<>();
	    		
	    		if (date==null) {
				for (MasterData b : masterDataRepository.findAll()) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}}
			else
			{
				for (MasterData b : masterDataRepository.findByDtGreaterThan(date)) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}}

	    		//lgService.addLog(DeviceId, userId, result.size(), new Date(), "getOrdersUser. Запрос обновления заказов с: ");   		
	    		return result;
		}
	    
	    @GetMapping("/order") 
		public List<OrderReq> getOrders(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
				@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
	    		List<OrderReq> result = new ArrayList<>();
	    		if (date==null) {
				for (MasterData b : masterDataRepository.findAll()) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}}
			else
			{
				for (MasterData b : masterDataRepository.findByDtGreaterThan(date)) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}}
	    		
	    		//lgService.addLog(DeviceId, userId, result.size(), date, "getOrders. Запрос обновления заказов с: ");
	    		return result;
		}
	    
	    @GetMapping("/getOrdersByDateAndDivision") 
		public List<OrderReq> getOrdersByDateAndDivision(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
	    		List<OrderReq> result = new ArrayList<>();
	    		if (date==null) {
				for (MasterData b : masterDataRepository.findAll()) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}}
			else
			{
				if (utils.belongsToCurrentDay(date)) {
					System.out.println("/order period: " + utils.atStartOfDay(date) + " - "+ date);
					for (MasterData b : masterDataRepository.findByDtBetweenOrderByDt(utils.atStartOfDay(date), date)) {
						OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
								b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
						result.add(orderReq);			
					}
				} else {
				System.out.println("/order period: " + utils.atStartOfDay(date) + " - "+ utils.toDate(utils.endOfMonth(date)));
				for (MasterData b : masterDataRepository.findByDtBetweenOrderByDt(utils.atStartOfDay(date), utils.toDate(utils.endOfMonth(date)))) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
					}
				}
			}
	    		System.out.println("order records number: " + (result.size()));
	    		return result;
		}

		@PostMapping("/archiveOrders") 
		public List<String> getArchiveOrders(@RequestBody ArrayList<String> orderId) throws RuntimeException{
			return masterDataRepository.findByOrderIdInAndArchive(orderId, true).stream().map(MasterData::getOrderId).collect(Collectors.toList());
		}
		@PostMapping("/orders") 
		public List<OrderReq> getOrdersNotIn(@RequestBody ArrayList<String> orderId) throws RuntimeException{
			List<OrderReq> result = new ArrayList<>();
			if (orderId==null || orderId.isEmpty()) {
				for (MasterData b : masterDataRepository.findAll()) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}
				return result;
			}
			else {
	    			long start = System.currentTimeMillis();
				for (MasterData b : masterDataRepository.findByOrderIdNotIn(orderId)) {
					OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
							b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
					result.add(orderReq);
				}
				System.out.println("Data syncronize... getOrdersNotIn took: " + (System.currentTimeMillis() - start));
				return result;
			}
		}
		@PostMapping("/boxes") 
		public List<BoxReq> getBoxes(@RequestBody ArrayList<String> boxesId) throws RuntimeException{
			List<BoxReq> result = new ArrayList<>();
			if (boxesId==null || boxesId.isEmpty()) {
				for (Box b : boxRepository.findAll()) {
					BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
					result.add(boxReq);
				}
				return result;
			}
			else {
	    			long start = System.currentTimeMillis();
				for (Box b : boxRepository.findByIdNotIn(boxesId)) {
					BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
					result.add(boxReq);
				}
				System.out.println("Data syncronize... getBoxes took: " + (System.currentTimeMillis() - start));
				return result;
			}
		}
		@PostMapping("/bm") 
		public List<MoveReq> getBoxMoves(@RequestBody ArrayList<String> bmId) throws RuntimeException{
			List<MoveReq> result = new ArrayList<>();
			if (bmId==null || bmId.isEmpty()) {
				for (BoxMove bm : boxMovesRepository.findAll()) {
					MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
					result.add(moveReq);
				}
				return result;
			}
			else {
				long start = System.currentTimeMillis();
				for (BoxMove bm : boxMovesRepository.findByIdNotIn(bmId)) {
					MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
					result.add(moveReq);
				}
				System.out.println("Data syncronize... getBoxMoves took: " + (System.currentTimeMillis() - start));
				return result;
			}
		}
	
	    @GetMapping("/getSumOrdersByDateAndDivision") 
		public List<OrderReq> getSumOrdersByDateAndDivision(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date,
				@RequestParam(value= "division_code", required=true) String division_code) throws RuntimeException{
	    		List<OrderReq> result = new ArrayList<>();

			System.out.println("/order period: " + utils.atStartOfDay(date) + " - "+ date);
			for (MasterData b : masterDataRepository.findByDtGreaterThanAndDivision_codeAndArchive(utils.atStartOfDay(date), division_code, false)) {
				OrderReq orderReq = new OrderReq(b.getId(), b.orderId, b.orderText, b.customer, b.nomenklature, 
						b.attrib, b.countProdInOrder, b.countBoxInOrder, b.countProdInBox, b.dt, b.division.getCode());
				result.add(orderReq);			
			}


	    		System.out.println("order records number: " + (result.size()));
	    		return result;
		}
		
	    @GetMapping("/getTest") 
		public List<OrderMoveRep> getTest(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date,
				@RequestParam(value= "division_code", required=true) String division_code) throws RuntimeException{
	    		List<OrderMoveRep> result = new ArrayList<>();
	    		List<Box> boxList = new ArrayList<>();
	    		List<BoxMove> bmList = new ArrayList<>();
	    		List<Operation> opList = new ArrayList<>();
	    		
	    		System.out.println("/order period: " + utils.atStartOfDay(date) + " - "+ date);
	    		Optional<Operation> opOpt = operationRepository.findById((long)1);
	    		opOpt.ifPresent(opList::add);
	    		opList.addAll(operationRepository.findByDivisionCodeOrderById(division_code));

	    		opOpt = operationRepository.findById((long)9999);
	    		opOpt.ifPresent(opList::add);
	    		
	    		if (opOpt.isPresent()) {
	
				Long boxNumber = masterDataRepository.findByDtGreaterThanAndDivision_code(utils.atStartOfDay(date), division_code).stream().mapToLong(MasterData::getCountBoxInOrder).sum();
				Long prodNumber = masterDataRepository.findByDtGreaterThanAndDivision_code(utils.atStartOfDay(date), division_code).stream().mapToLong(MasterData::getCountProdInOrder).sum();	   
				result.add(new OrderMoveRep(opList.get(0).getId() ,opList.get(0).getName(), boxNumber, prodNumber));
				
				boxList = boxRepository.findByMasterDataIdIn(masterDataRepository.findByDtGreaterThanAndDivision_code(utils.atStartOfDay(date), division_code).stream().map(MasterData::getId).collect(Collectors.toList()));
				
				for (int i=1; i < opList.size(); i++) {
					
					bmList = boxMovesRepository.findByBoxIdInAndOperationId(boxList.stream().map(Box::getId).collect(Collectors.toList()), opList.get(i).getId());
					prodNumber = partBoxRepository.findByBoxMoveIdIn(bmList.stream().map(BoxMove::getId).collect(Collectors.toList())).stream().mapToLong(PartBox::getQuantity).sum();
					result.add(new OrderMoveRep(opList.get(i).getId() ,opList.get(i).getName(), (long)bmList.size(), prodNumber));	
				}
	    		}
	    		System.out.println("order records number: " + (result.size()));
	    		return result;
		}
	    
	    @GetMapping("/getTest1") 
		public List<OrderMoveRep> getTest1(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date,
				@RequestParam(value= "division_code", required=true) String division_code) throws RuntimeException{
	    		List<OrderMoveRep> result = new ArrayList<>();
	    		List<Box> boxList = new ArrayList<>();
	    		List<BoxMove> bmList = new ArrayList<>();
	    		List<Operation> opList = new ArrayList<>();
	    		Long boxNumber = (long)0;
	    		Long prodNumber = (long)0;
	    		
	    		System.out.println("/order period: " + utils.atStartOfDay(date) + " - "+ date);
	    		opList.addAll(operationRepository.findAllByOrderByIdAsc());
	    		
	    		for (int i=0; i < opList.size(); i++) {
		    		bmList = boxMovesRepository.findByOperationIdAndDateBetweenOrderByDateAscOperationIdAsc(opList.get(i).getId(),utils.atStartOfDay(date),utils.atEndOfDay(date));
		    		if (!bmList.isEmpty()) {
					prodNumber = partBoxRepository.findByBoxMoveIdIn(bmList.stream().map(BoxMove::getId).collect(Collectors.toList())).stream().mapToLong(PartBox::getQuantity).sum();
					result.add(new OrderMoveRep(opList.get(i).getId() ,opList.get(i).getName(), (long)bmList.size(), prodNumber));	
				}
	    		}
	    		System.out.println("order records number: " + (result.size()));
	    		return result;
		}
	    
	    @GetMapping("/getTest2") 
		public List<OrderGroupReq> getTest2(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date,
				@RequestParam(value= "operationId", required=true) Long operationId) throws RuntimeException{
	    		List<OrderGroupReq> result = new ArrayList<>();
	    		List<String> tmp = new ArrayList<>();
	    		List<BoxMove> bmList = new ArrayList<>();
	    		
		    	System.out.println("/order period: " + utils.atStartOfDay(date) );
	    		
	    		if (operationId != null) {

		    		bmList = boxMovesRepository.findByOperationIdAndDateBetweenOrderByDateAsc(operationId,utils.atStartOfDay(date),utils.atEndOfDay(date));
		    		for (BoxMove bm : bmList) {
		    			if (masterDataRepository.findById(bm.getBox().getMasterData().getId()).isPresent()) {
		    				MasterData tmpOrder = masterDataRepository.findById(bm.getBox().getMasterData().getId()).get();
		    				if (!tmp.contains(tmpOrder.getOrderText())) {
		    					tmp.add(tmpOrder.getOrderText());
		    					//String orderText, String customer, Long sumProd, Long sumBox, int countProdInBox, String sDate, String division_code
		    					result.add(new OrderGroupReq(tmpOrder.getOrderText(), tmpOrder.getCustomer(), (long)0, (long)0, 0,
		    							utils.getStartOfDay(tmpOrder.getDt()), tmpOrder.getDivision().getName(), tmpOrder.getDivision(), tmpOrder.getClient()));
		    				}
		    			}
		    		}

	    		}
	    		
	    		/*
	    		result.sort(new Comparator<OrderGroupReq>() {
	    	        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");

				@Override
				public int compare(OrderGroupReq o1, OrderGroupReq o2) {
	    	            try {
	    	                return f.parse(o1.sdate).compareTo(f.parse(o2.sdate));
	    	            } catch (ParseException e) {
	    	                throw new IllegalArgumentException(e);
	    	            }
				}
	    	    });
	    		System.out.println("order records number: " + (result.size()));*/
	    		
	    		return result.stream()
	    				.sorted(Comparator.comparing(OrderGroupReq::getSdate)
	    						.thenComparing(OrderGroupReq::getCustomer)).collect(Collectors.toList());
	    }
}

 