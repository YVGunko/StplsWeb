package hello.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.utils;
import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.Box.BoxService;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.BoxMoves.BoxMovesService;
import hello.Department.Department;
import hello.Department.DepartmentRepository;
import hello.Device.Device;
import hello.Device.DeviceService;
import hello.LogGournal.LogGournal;
import hello.LogGournal.LogGournalRepository;
import hello.LogGournal.LogGournalService;
import hello.MasterData.MasterData;
import hello.MasterData.MasterDataRepository;
import hello.MasterData.MasterDataService;
import hello.OutDoc.OutDoc;
import hello.OutDoc.OutDocService;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;
import hello.User.User;
import hello.User.UserRepository;
import hello.User.UserService;


@RestController
public class BoxController {
	private int pageSize = 3000;
	
	@Autowired
	MasterDataRepository masterDataRepository;
	
	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	BoxMovesService boxMovesService;
	@Autowired
	private BoxMovesRepository boxMovesRepository;
	
	@Autowired
	PartBoxService partBoxService;
	
	@Autowired
	BoxService boxService;
	
	@Autowired
	BoxRepository boxRepository;
	
	@Autowired
	private PartBoxRepository partBoxRepository;

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private OutDocService Service;
	
	@Autowired
	UserService uService;	
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LogGournalService lgService;
	@Autowired
	DeviceService deviceService;	
	@Autowired
	private LogGournalRepository lgRepository;
	
	@GetMapping("/getBoxesByDateAndDivision") 
	public List<BoxReq> getBoxesByDateAndDivision(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date,
			@RequestParam(value= "division_code", required=false) String division_code) throws RuntimeException{
		List<BoxReq> result = new ArrayList<>();
		
		long start = System.currentTimeMillis();
		System.out.println("/boxesByDate start date: " + date);
		
		for (Box b : boxService.selectBoxByDateAndDivision(date, division_code)) {
			BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
			result.add(boxReq);
		}
		System.out.println("Data syncronize... getOrdersByDateAndDivision took: " + (System.currentTimeMillis() - start));
		System.out.println("getOrdersByDateAndDivision records number: " + (result.size()));
		return result;
	}

	@GetMapping("/qr") 
	public List<String> getQr(@RequestParam(value="operation", required=true) long operationId) throws RuntimeException{
		List<String> qrList = new ArrayList<>();
		List<BoxMove> boxMoveList = boxMovesRepository.findByOperationIdAndSentToMasterDate(operationId, null);
		for (BoxMove boxMove:boxMoveList) {
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			if (partBoxQuantityList.size()>0) {
			int numBox = boxMove.getBox().getNumBox();
			String orderId = boxMove.getBox().getMasterData().getOrderId();
			//List<Department> partBoxDepartmentList = partBoxRepository.findByBoxMoveId(boxMove.getId()).stream().map(PartBox::getDepartment).collect(Collectors.toList());
			for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
				int quantityBox = partBoxQuantityList.get(i);
				qrList.add(orderId+"."+quantityBox+"."+numBox);
			}
			}
		}
		return qrList;
	}
	@GetMapping("/qrNkl") 
	public List<String> getQrOutDoc(@RequestParam(value="operation", required=true) long operationId) throws RuntimeException{
		List<String> qrList = new ArrayList<>();
		List<BoxMove> boxMoveList = boxMovesRepository.findByOperationIdAndSentToMasterDate(operationId, null);
		for (BoxMove boxMove:boxMoveList) {
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			if (partBoxQuantityList.size()>0) {			
			int numBox = boxMove.getBox().getNumBox();
			String orderId = boxMove.getBox().getMasterData().getOrderId();

			List<OutDoc> outDocList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getOutDoc).collect(Collectors.toList());
			for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
				int quantityBox = partBoxQuantityList.get(i);
				OutDoc odId = outDocList.get(i);
				//Date dt = partBoxDateList.get(i);
				qrList.add(orderId+"."+quantityBox+"."+numBox+"n"+odId.getNumber()+"dd"+odId.getDate());
				//qrList.add(orderId+"."+quantityBox+"."+numBox+";"+depId.getCode()+";"+dt);
			}
			}
		}
		return qrList;
	}
	@GetMapping("/qrNklDiv") 
	public List<String> getQrNklDiv(@RequestParam(value="operation", required=true) long operationId,@RequestParam(value= "division_code", required=true) String division_code) throws RuntimeException{
		System.out.println("qrNklDiv starts at: " + new Date() + ". Operation: "+String.valueOf(operationId) + ", Division: " +division_code);
		List<String> qrList = new ArrayList<>();
		List<BoxMove> boxMoveList = boxMovesRepository.findByOperationIdAndSentToMasterDateAndBoxMasterDataDivisionCode(operationId, null, division_code);
		System.out.println("qrNklDiv boxMoveList Row counter: " + boxMoveList.size());
		for (BoxMove boxMove:boxMoveList) {
			//if (boxMove.getSentToMasterDate() != null) System.out.println("qrNklDiv boxMove.getSentToMasterDate: " + boxMove.getSentToMasterDate().toString());
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			//System.out.println("qrNklDiv partBoxQuantityList Row counter: " + partBoxQuantityList.size());
			if (partBoxQuantityList.size()>0) {			
				int numBox = boxMove.getBox().getNumBox();
				String orderId = boxMove.getBox().getMasterData().getOrderId();
	
				List<OutDoc> outDocList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getOutDoc).collect(Collectors.toList());
				for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
					int quantityBox = partBoxQuantityList.get(i);
					OutDoc odId = outDocList.get(i);
					qrList.add(orderId+"."+quantityBox+"."+numBox+"n"+odId.getNumber()+"dd"+odId.getDate());
				}
			}
		}
		System.out.println("qrNklDiv ends at: " + new Date());
		return qrList;
	}
	@GetMapping("/qrdDiv") 
	public List<String> getQrOutDocDiv(@RequestParam(value= "operation", required=true) long operationId,@RequestParam(value= "division_code", required=true) String division_code) throws RuntimeException{
		System.out.println("qrdDiv starts at: " + new Date() + ". Operation: "+String.valueOf(operationId) + ", Division: " +division_code);
		List<String> qrList = new ArrayList<>();	
		List<BoxMove> boxMoveList = boxMovesRepository.findByOperationIdAndSentToMasterDateAndBoxMasterDataDivisionCode(operationId, null, division_code);
		System.out.println("qrdDiv boxMoveList Row counter: " + boxMoveList.size());
		for (BoxMove boxMove:boxMoveList) {
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			
			if (partBoxQuantityList.size()>0) {
				int numBox = boxMove.getBox().getNumBox();
				String orderId = boxMove.getBox().getMasterData().getOrderId();
				List<Department> partBoxDepartmentList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getDepartment).collect(Collectors.toList());
				
				for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
					int quantityBox = partBoxQuantityList.get(i);
					Department depId = partBoxDepartmentList.get(i);
					qrList.add(orderId+"."+quantityBox+"."+numBox+";"+depId.getCode());
				}
			}
		}
		System.out.println("qrdDiv ends at: " + new Date());
		return qrList;
	}

	@GetMapping("/qrdNkl") 
	public List<String> getQrDOutDoc(@RequestParam(value="operation", required=true) long operationId) throws RuntimeException{
		List<String> qrList = new ArrayList<>();
		List<BoxMove> boxMoveList = boxMovesRepository.findByOperationIdAndSentToMasterDate(operationId, null);
		for (BoxMove boxMove:boxMoveList) {
			int numBox = boxMove.getBox().getNumBox();
			String orderId = boxMove.getBox().getMasterData().getOrderId();
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			List<Department> partBoxDepartmentList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getDepartment).collect(Collectors.toList());
			List<OutDoc> outDocList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getOutDoc).collect(Collectors.toList());
			for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
				int quantityBox = partBoxQuantityList.get(i);
				Department depId = partBoxDepartmentList.get(i);
				OutDoc odId = outDocList.get(i);
				//Date dt = partBoxDateList.get(i);
				qrList.add(orderId+"."+quantityBox+"."+numBox+";"+depId.getCode()+"n"+odId.getNumber()+"dd"+odId.getDate());
				//qrList.add(orderId+"."+quantityBox+"."+numBox+";"+depId.getCode()+";"+dt);
			}
		}
		return qrList;
	}
	@GetMapping("/qrd") 
	public List<String> getQrD(@RequestParam(value="operation", required=true) long operationId) throws RuntimeException{
		List<String> qrList = new ArrayList<>();
		List<BoxMove> boxMoveList = boxMovesRepository.findByOperationIdAndSentToMasterDate(operationId, null);
		for (BoxMove boxMove:boxMoveList) {
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			if (partBoxQuantityList.size()>0) {
				int numBox = boxMove.getBox().getNumBox();
				String orderId = boxMove.getBox().getMasterData().getOrderId();
				List<Department> partBoxDepartmentList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getDepartment).collect(Collectors.toList());
			//List<Date> partBoxDateList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(boxMove.getId()).stream().map(PartBox::getDate).collect(Collectors.toList());
				for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
					int quantityBox = partBoxQuantityList.get(i);
					Department depId = partBoxDepartmentList.get(i);
					//Date dt = partBoxDateList.get(i);
					qrList.add(orderId+"."+quantityBox+"."+numBox+";"+depId.getCode());
					//qrList.add(orderId+"."+quantityBox+"."+numBox+";"+depId.getCode()+";"+dt);
				}
			}
		}
		return qrList;
	}
	
	@PostMapping("/setSendToMasterDate")
	@Transactional
	public boolean setSendToMasterDate(@RequestBody ArrayList<String> Qrd, @RequestParam(value="operation", required=true) long operationId) throws Exception{
		//[	    "712000100000014545.13.0.50.14.3;000000028",
		//Нужно найти запись в MasterData где orderId = "712000100000014545.13.0.50"
		long depId = 0;

		String orderId = null;
		for (int i = 0; i < Qrd.size(); i = i + 1 ) { 
			if (operationId != 9999) {
				String sdepId = Qrd.get(i).substring(Qrd.get(i).indexOf(";")+1, Qrd.get(i).length());
    				if (!departmentRepository.findBycode(sdepId).isEmpty()) {
    					Department oldE = departmentRepository.findBycode(sdepId).get(0);
    					if (departmentRepository.findBycode(sdepId).size() > 1)
    						throw new RuntimeException("ERROR: somehow there is more than 1 Department with ID : " + sdepId);
    					else
    						depId = oldE.getId();
    				orderId = Qrd.get(i).substring(0,Qrd.get(i).indexOf(";")); //до ;
    				}
    			}
			else {
				depId = 0;
				orderId = Qrd.get(i); //до ;
			}
			if (orderId != null) {
			int boxNum = Integer.valueOf(orderId.substring(orderId.lastIndexOf(".")+1,orderId.length())); //номер коробки
			orderId = orderId.substring(0, orderId.lastIndexOf("."));
			int quantityBox = Integer.valueOf(orderId.substring(orderId.lastIndexOf(".")+1,orderId.length()));
			orderId = orderId.substring(0, orderId.lastIndexOf("."));

			Date date = new Date();
			
			MasterData md = masterDataRepository.getByOrderId(orderId);
			if (md == null) {
				throw new RuntimeException ("no such orderId in MasterData: " + orderId);
			}
			Box box = boxRepository.findByMasterDataIdAndNumBox (md.getId(), boxNum);
			if (box == null) {
				throw new RuntimeException ("no such masterDataId in Box: " + md.getId().toString());
			}
			try {
				BoxMove bm = boxMovesRepository.findByOperationIdAndBoxId (operationId, box.getId());
				if (bm == null) {
					throw new RuntimeException ("no such boxId in BoxMove: " + box.getId().toString());
				}
				if (!partBoxService.updateSendToMasterDate(date, bm.getId(), depId, quantityBox)) {
					System.out.println("Didn't update partBoxService.updateSendToMasterDate " + Qrd.get(i));
					}
				else { //Сначала нужно выяснить полностью ли подошва из состава коробки загружена в 1С.
					List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNotNull(bm.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
					//Integer partBoxQuantity = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNotNull(bm.getId()).stream().map(PartBox::getQuantity).reduce(0,(x,y)->x+y);
//TODO
					if (partBoxQuantityList.size()>0) { //Если есть
						int quantityBox1 = bm.getBox().getQuantityBox();
						for (int y = 0; y < partBoxQuantityList.size(); y = y + 1 )
								quantityBox1 = quantityBox1 - partBoxQuantityList.get(y);

						if (quantityBox==0) {
								bm.setSentToMasterDate(date);
								boxMovesRepository.save(bm);
								/*if (operationId == 9999) {
									box.setSentToMasterDate(date);
									boxRepository.save(box);
								}*/
							}
						}
					}
				}
				catch (Exception e) {
					System.out.println("NonUnique BoxMove for OperationId: " +operationId+ " and BoxId: " + box.getId().toString());
					throw new Exception(
					           "boxMovesRepository", e);
				}	
			}
		}		
		return true;
	}

	@PostMapping("/boxSync") 
	public BoxWithMovesWithPartsRequest BoxSync(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date)  throws Exception{
		BoxWithMovesWithPartsRequest responce = new BoxWithMovesWithPartsRequest();

		long startSync = System.currentTimeMillis();	
		List<Box> savedBoxList = boxRepository.findByReceivedFromMobileDateGreaterThan(date);
		if (savedBoxList!=null) {
			for (Box b : savedBoxList) {
				responce.boxReqList.add(new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(), b.getReceivedFromMobileDate(), b.getArchive()));

				List<BoxMove> savedBoxMoveList = boxMovesRepository.findByBoxId(b.getId());
				if (savedBoxMoveList!=null) {
					for (BoxMove bm : savedBoxMoveList) {
						responce.movesReqList.add(new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(), bm.getReceivedFromMobileDate()));

						List<PartBox> savedPartBoxList = partBoxRepository.findByBoxMoveId(bm.getId());
						if (savedBoxMoveList!=null) {
							for (PartBox pb : savedPartBoxList) {
								responce.partBoxReqList.add(new PartBoxReq(pb.getId(),pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(),pb.getDate(), pb.getReceivedFromMobileDate(), pb.getOutDoc().getId()));
							}
						}
					}
				}
			}
		}
		System.out.println("BoxSync. Thats it! " + (System.currentTimeMillis() - startSync));

		return responce;
	}
	
	@PostMapping("/partBox")
	@Transactional
	public BoxWithMovesWithPartsRequest addBox(@RequestBody @Valid BoxWithMovesWithPartsRequest boxWithMovesWithPartsRequest, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws Exception{
		BoxWithMovesWithPartsRequest responce = new BoxWithMovesWithPartsRequest();
		Date currentDate = new Date();

		List<Box> savedBoxList = boxService.save(boxWithMovesWithPartsRequest.boxReqList, currentDate);
		for (Box b : savedBoxList) {
			responce.boxReqList.add(new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(), b.getReceivedFromMobileDate(), b.getArchive()));
		}

		List<BoxMove> savedBoxMoveList = boxMovesService.save(boxWithMovesWithPartsRequest.movesReqList, currentDate);	

		List<PartBox> savedPartBoxList = partBoxService.save(boxWithMovesWithPartsRequest.partBoxReqList, currentDate);

		for (PartBox pb : savedPartBoxList) {
			responce.partBoxReqList.add(new PartBoxReq(pb.getId(),pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(),pb.getDate(), pb.getReceivedFromMobileDate(), pb.getOutDoc().getId()));
			if (savedBoxMoveList == null) {			
			}
		};
		for (BoxMove bm : savedBoxMoveList) {
			if (bm.getOperation().getId() == 9999) {
				int prodInBoxCount = bm.getBox().getQuantityBox();
				List<String> boxMoveIdList = boxMovesRepository.findByBoxIdInAndOperationId(bm.getBox().getId(), (long)9999).stream().map(BoxMove::getId).collect(Collectors.toList());
				List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdIn(boxMoveIdList).stream().map(PartBox::getQuantity).collect(Collectors.toList());
				int sum = partBoxQuantityList.stream().mapToInt(Integer::intValue).sum();
				if (sum == prodInBoxCount) {
					Optional<Box> b = boxRepository.findById(bm.getBox().getId());
					if (b.isPresent()) b.get().setArchive(true);
					boxRepository.save(b.get());
				}
				//TODO BoxMove setArchive
				int prodInOrderCount = masterDataRepository.findById(bm.getBox().getMasterData().getId()).get().getCountProdInOrder();
				List<String> boxIdList = boxRepository.findByMasterDataId(bm.getBox().getMasterData().getId()).stream().map(Box::getId).collect(Collectors.toList());
				boxMoveIdList = boxMovesRepository.findByBoxIdInAndOperationId(boxIdList, (long)9999).stream().map(BoxMove::getId).collect(Collectors.toList());
				partBoxQuantityList = partBoxRepository.findByBoxMoveIdIn(boxMoveIdList).stream().map(PartBox::getQuantity).collect(Collectors.toList());
				sum = partBoxQuantityList.stream().mapToInt(Integer::intValue).sum();
				
				if (sum == prodInOrderCount) {
					Optional<MasterData> md = masterDataRepository.findById(bm.getBox().getMasterData().getId());
					if (md.isPresent()) md.get().setArchive(true);
					masterDataRepository.save(md.get());
				}
			}
			responce.movesReqList.add(new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(), bm.getReceivedFromMobileDate()));
		}
		/*if (responce.boxReqList.size() !=0)
			lgService.addLog(DeviceId, userId, responce.boxReqList.size(), responce.boxReqList.get(0).date, "partBox.addBox. Выгрузка коробок.");
		else
			lgService.addLog(DeviceId, userId, 0, currentDate, "partBox.addBox. Выгрузка коробок.");
		if (responce.movesReqList.size() != 0)
			lgService.addLog(DeviceId, userId, responce.movesReqList.size(), responce.movesReqList.get(0).date, "partBox.addBox. Выгрузка движений коробок.");
		else
			lgService.addLog(DeviceId, userId, 0, currentDate, "partBox.addBox. Выгрузка движений коробок.");
		if (responce.partBoxReqList.size() != 0)
			lgService.addLog(DeviceId, userId, responce.partBoxReqList.size(), responce.partBoxReqList.get(0).date, "partBox.addBox. Выгрузка подошвы.");
		else
			lgService.addLog(DeviceId, userId, 0, currentDate, "partBox.addBox. Выгрузка подошвы.");*/
		
		return responce;
	}
	
	@GetMapping("/deleteArchiveData") 
	public int deleteArchiveData(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy") Date orderDate) throws RuntimeException{
		int pbCount = 0;
		int bmCount = 0;
		int bCount = 0;
		int mdCount = 0;
		List<MasterData> archivedMasterDataList = masterDataRepository.findByArchiveAndDtLessThan(true, orderDate); 
		System.out.println("MasterData records to be deleted - " + archivedMasterDataList.size());
		for (MasterData md : archivedMasterDataList){
			List<Box> archivedBoxList = boxRepository.findByMasterDataId(md.getId());
			if (archivedBoxList != null) {
				for (Box b : archivedBoxList) {
					List<BoxMove> archivedBoxMoveList = boxMovesRepository.findByBoxId(b.getId());
					for (BoxMove bm : archivedBoxMoveList) {
						List<PartBox> archivedPartBoxList = partBoxRepository.findByBoxMoveId(bm.getId());
						partBoxRepository.deleteAll(archivedPartBoxList);
						pbCount = pbCount + archivedPartBoxList.size();
						/*for (PartBox pb : archivedPartBoxList) {
							partBoxRepository.delete(pb);
							pbCount = pbCount + 1;							
						}*/
						boxMovesRepository.delete(bm);
						bmCount = bmCount + 1;	
					}
					boxRepository.delete(b);
					bCount = bCount + 1;
				}
			} 
			masterDataRepository.delete(md);
			System.out.println("deleteArchiveData. MasterData record deleted: " + md.orderText + ", "+md.nomenklature);
			mdCount = mdCount + 1;
		}
		System.out.println("deleteArchiveData. PartBox records deleted: " + pbCount);
		System.out.println("deleteArchiveData. BoxMove records deleted: " + bmCount);
		System.out.println("deleteArchiveData. Box records deleted: " + bCount);
		System.out.println("deleteArchiveData. MasterData records deleted: " + mdCount);
		return 0;
	}
	
	@GetMapping("/serviceSetBoxMoveSentToMasterDate") 
	public int serviceSetBoxMoveSentToMasterDate(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy") Date orderDate) throws RuntimeException{
		int count = 0;
		int countBox = 0;
		Date dt = null;
		List<BoxMove> boxMoveList = boxMovesRepository.findBySentToMasterDateOrderByDate(null);
		System.out.println("BoxMove records to be updated by PartBox - " + boxMoveList.size());
		for (BoxMove boxMove:boxMoveList) {
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNotNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			List<Date> partBoxDateList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNotNull(boxMove.getId()).stream().map(PartBox::getDate).collect(Collectors.toList());
			if (partBoxDateList.size()>0) dt = partBoxDateList.get(0);
			if (partBoxQuantityList.size()>0) { //Если есть
				int quantityBox = boxMove.getBox().getQuantityBox();
				for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
					quantityBox = quantityBox - partBoxQuantityList.get(i);
				}
				if (quantityBox==0) {
					boxMove.setSentToMasterDate(dt);
					boxMovesRepository.save(boxMove);
					count = count + 1;
					System.out.println("BoxMove updated by PartBox - " + count);
				}
			}			
		}
		System.out.println("Total BoxMove updated by PartBox - " + count);
		List<Box> boxList = boxRepository.findBySentToMasterDateIsNotNullOrderByDate();
		for (Box box:boxList) {
			List<BoxMove> boxMoveList1 = boxMovesRepository.findByBoxIdAndSentToMasterDateIsNull(box.getId()).stream().collect(Collectors.toList());
			if (boxMoveList1.size() >0) { //Если есть
				dt = box.getSentToMasterDate();
				for (int i = 0; i < boxMoveList1.size(); i = i + 1 ) { 
					boxMoveList1.get(i).setSentToMasterDate(dt);
					boxMovesRepository.saveAll(boxMoveList1);
					countBox = countBox + 1;
					System.out.println("BoxMove updated by Box - " + countBox);
				}
			}
		}
		boxList = boxRepository.findByArchiveOrderByDate(true);
		System.out.println("BoxMove records to be updated by BoxArchive - " + boxList.size());
		for (Box box:boxList) {
			List<BoxMove> boxMoveList1 = boxMovesRepository.findByBoxIdAndOperationIdAndSentToMasterDateIsNull(box.getId(), 1).stream().collect(Collectors.toList());
			if (boxMoveList1.size() >0) { //Если есть
				dt = box.getReceivedFromMobileDate();
				for (int i = 0; i < boxMoveList1.size(); i = i + 1 ) { 
					boxMoveList1.get(i).setSentToMasterDate(dt);
					boxMovesRepository.saveAll(boxMoveList1);
					countBox = countBox + 1;
					System.out.println("BoxMove updated by Box - " + countBox);
				}
			}
		}
		System.out.println("Total BoxMove updated by Box - " + countBox);
		count = count + countBox;
		System.out.println("Total BoxMove updated - " + count);
		return count;
	}

	@GetMapping("/service1") 
	public int service1(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy") Date orderDate) throws RuntimeException{
		int count = 0;
		Date dt = null;
		List<BoxMoveBoxAndQuantityDTO> boxMoveList = boxMovesRepository.getIdAndBoxQuantity(PageRequest.of(0, pageSize)); //BoxMove.id, Box.id, Box.boxQuantity
		
				
		System.out.println("Total BoxMove updated by PartBox - " + boxMoveList.size());

		return count;
	}

	@GetMapping("/serverUpdateTime") 
	public Timestamp getServerUpdateTime() throws RuntimeException{
		return new Timestamp(System.currentTimeMillis());

	}
	@GetMapping("/boxesByDate") 
	public List<BoxReq> getBoxesByDate(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
		List<BoxReq> result = new ArrayList<>();
		if (date==null) {
			for (Box b : boxRepository.findAll()) {
				BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
				result.add(boxReq);
			}
		}
		else {
			for (Box b : boxRepository.findByDateGreaterThan(date)) {
				BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
				result.add(boxReq);
			}
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getBoxesByDate. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/boxesByDateUser") 
	public List<BoxReq> getBoxesByDateUser(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
		List<BoxReq> result = new ArrayList<>();
		if (date==null) {
			for (Box b : boxRepository.findAll()) {
				BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
				result.add(boxReq);
			}
		}
		else {
			for (Box b : boxRepository.findByDateGreaterThan(date)) {
				BoxReq boxReq = new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), b.getDate(),b.getReceivedFromMobileDate(),b.getArchive());
				result.add(boxReq);
			}
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getBoxesByDateUser. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/bmByDate") 
	public List<MoveReq> getBoxMovesByDate(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
		List<MoveReq> result = new ArrayList<>();
		if (date==null) { 
			for (BoxMove bm : boxMovesRepository.findAll()) {
				MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
				result.add(moveReq);
			}
		}
		else {
			for (BoxMove bm : boxMovesRepository.findByDateGreaterThan(date)) {
				MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
				result.add(moveReq);
			}
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getBoxMovesByDate. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/bmByDateAndArchive") 
	public List<MoveReq> getBoxMovesByDateAndArchive(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
		List<MoveReq> result = new ArrayList<>();
		for (BoxMove bm : boxMovesService.findAllWhereArhive(date)) {
			MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
			result.add(moveReq);
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getBoxMovesByDateAndArchive. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/bmByDatePagebleCount") 
	public int getBoxMovesByDatePagebleCount(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		try {
			int totalPages = boxMovesRepository.findByDateGreaterThanEqualOrderByDateAsc(date,PageRequest.of(0, pageSize)).getTotalPages();
			//lgService.addLog("Пусто", (long)0, totalPages, date, "getBoxMovesByDatePagebleCount. Запрос количества страниц. Обновление с : ");
			return totalPages;
		}catch (Exception e) {
			e.printStackTrace();
			//lgService.addLog("Пусто", (long)0, 0, date, "getBoxMovesByDatePagebleCount. Exception: "+e.getMessage());
			return 0;
		}
	}
	@GetMapping("/bmByDatePageble") 
	@Transactional
	public List<MoveReq> getBoxMovesByDatePageble(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId,
			@RequestParam(value="page", defaultValue = "0") int page) throws RuntimeException{
		System.out.println("bmByDatePageble. Requested page: " +String.valueOf(page));
		List<MoveReq> result = new ArrayList<>();
		try {
			Pageable paging = PageRequest.of(page, pageSize);
	
			for (BoxMove bm : boxMovesRepository.findByDateGreaterThanEqualOrderByDateAsc(date,paging).getContent()) {
				MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
				result.add(moveReq);
			}
	
			//lgService.addLog(DeviceId, userId, result.size(), date, "getBoxMovesByDatePageble. Page="+String.valueOf(page)+". Запрос обновления коробов с: ");

		}catch (Exception e) {
				e.printStackTrace();
				//lgService.addLog(DeviceId, userId, 0, date, "getBoxMovesByDatePageble. Exception: "+e.getMessage());
		}
		return result;
	}
	@GetMapping("/pbByDatePagebleCount") 
	public int getPartBoxByDatePagebleCount(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		try {
			int totalPages = partBoxRepository.findByDateGreaterThanEqualOrderByDateAsc(date,PageRequest.of(0, pageSize)).getTotalPages();
			lgService.addLog("Пусто", (long)0, totalPages, date, "getPartBoxByDatePagebleCount. Запрос количества страниц. Обновление с : ");
			return totalPages;
		}catch (Exception e) {
			e.printStackTrace();
			lgService.addLog("Пусто", (long)0, 0, date, "getPartBoxByDatePagebleCount. Exception: "+e.getMessage());
			return 0;
		}
	}
	@GetMapping("/pbByDatePageble") 
	@Transactional
	public List<PartBoxReq> getPartBoxByDatePageble(@RequestParam(value="date", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId,
			@RequestParam(value="page", defaultValue = "0") int page) throws RuntimeException{
		System.out.println("pbByDatePageble: "+date+". Requested page: " +String.valueOf(page));
		List<PartBoxReq> result = new ArrayList<>();
		
		try {
			Pageable paging = PageRequest.of(page, pageSize);
	
			for (PartBox pb : partBoxRepository.findByDateGreaterThanEqualOrderByDateAsc(date,paging).getContent()) {
				PartBoxReq pbReq = new PartBoxReq(pb.getId(), pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc().getId());
				result.add(pbReq);
			}
	
			//lgService.addLog(DeviceId, userId, result.size(), date, "getPartBoxByDatePageble. Page="+String.valueOf(page)+". Запрос обновления коробов с: ");

		}catch (Exception e) {
				e.printStackTrace();
				//lgService.addLog(DeviceId, userId, 0, date, "getPartBoxByDatePageble. Exception: "+e.getMessage());
		}
		return result;
	}

	@GetMapping("/pbByDate") 
	public List<PartBoxReq> getPartBoxByDate(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{

		List<PartBoxReq> result = new ArrayList<>();
		if (date==null) {			
			for (PartBox pb : partBoxRepository.findAll()) {
				PartBoxReq pbReq = new PartBoxReq(pb.getId(), pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc().getId());
				result.add(pbReq);
			}
		}
		else {
			for (PartBox pb : partBoxRepository.findByDateGreaterThanEqual(date)) {
				PartBoxReq pbReq = new PartBoxReq(pb.getId(), pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc().getId());
				result.add(pbReq);
			}
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getPartBoxByDate. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/pbByDateAndArchive") 
	public List<PartBoxReq> getPartBoxByDateAndArchive(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{

		List<PartBoxReq> result = new ArrayList<>();
		for (PartBox pb : partBoxService.findAllWhereArchive(date)) {
			PartBoxReq pbReq = new PartBoxReq(pb.getId(), pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc().getId());
			result.add(pbReq);
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getPartBoxByDateAndArchive. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/pbByDateUser") 
	public List<PartBoxReq> getPartBoxByDateUser(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{

		List<PartBoxReq> result = new ArrayList<>();
		if (date==null) {			
			for (PartBox pb : partBoxRepository.findAll()) {
				PartBoxReq pbReq = new PartBoxReq(pb.getId(), pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc().getId());
				result.add(pbReq);
			}
		}
		else {
			for (PartBox pb : partBoxRepository.findByDateGreaterThanEqual(date)) {
				PartBoxReq pbReq = new PartBoxReq(pb.getId(), pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc().getId());
				result.add(pbReq);
			}
		}
		
		//lgService.addLog(DeviceId, userId, result.size(), date, "getPartBoxByDateUser. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/bmByDateUser") 
	public List<MoveReq> getBoxMovesByDateUser(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date, 
			@RequestParam(value="userId", required=false) Long userId, @RequestParam(value= "deviceId", required=false) String DeviceId) throws RuntimeException{
		List<MoveReq> result = new ArrayList<>();

		if (date==null) { 
			for (BoxMove bm : boxMovesRepository.findAll()) {
				MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
				result.add(moveReq);
			}
		}
		else {
			for (BoxMove bm : boxMovesRepository.findByDateGreaterThan(date)) {
				MoveReq moveReq = new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), bm.getDate(),bm.getReceivedFromMobileDate());
				result.add(moveReq);
			}
		}

		//lgService.addLog(DeviceId, userId, result.size(), date, "getBoxMovesByDateUser. Запрос обновления коробов с: ");
		return result;
	}
	@GetMapping("/adDevice") public long adDevice (@RequestParam(value= "deviceId", required=true) String DeviceId) throws Exception {
		Device device = new Device();
		try {
			device = deviceService.saveAndGet(DeviceId);
		}catch (Exception e) {
		e.printStackTrace();
		}
		return device.getId();		
	}
}
