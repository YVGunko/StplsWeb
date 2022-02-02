package hello.OutDoc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.Box.BoxService;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.BoxMoves.BoxMovesService;
import hello.Controller.BoxReq;
import hello.Controller.MoveReq;
import hello.Controller.OutDocReq;
import hello.Controller.OutDocWithBoxWithMovesWithPartsRequest;
import hello.Controller.PartBoxReq;
import hello.Device.Device;
import hello.Device.DeviceService;
import hello.MasterData.MasterData;
import hello.MasterData.MasterDataRepository;
import hello.MasterData.MasterDataService;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;
import hello.Web.OutDoc.OutDocWeb;
import hello.utils;

@RestController
public class OutDocController {
	int pageSize = 50;
	
	@Autowired
	private OutDocService Service;
	@Autowired
	private OutDocRepository cRepository;
	
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
	

	@GetMapping("/getOutDocByDateAndDivision") 
	public List<OutDocReq> getOutDocByDateAndDivision(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date,
			@RequestParam(value= "division_code", required=false) String division_code) throws Exception{
		List<OutDocReq> responce = new ArrayList<>();
		List<OutDoc> outDocList = new ArrayList<>();

		outDocList = Service.selectOutDocByDateAndDivision (date, division_code);
			
		for (OutDoc b : outDocList) {
				responce.add(new OutDocReq(b.getId(), b.getOperation().getId(), b.getNumber(), b.getComment(), b.getDate(),
						b.getReceivedFromMobileDate(),b.division.getCode(),b.user.getId()));
				}
		System.out.println("getOutDocByDateAndDivision records number: " + (responce.size()));
		return responce;
	}
	
	@PostMapping("/outDocSaveOrUpdate") 
	public List<OutDocReq> outDocSaveOrUpdate(@RequestBody @Valid List<OutDocReq> outDocRec, @RequestParam(value= "deviceId", required=false) String DeviceId) throws Exception{
		//OutDocReq responce = new OutDocReq();
		List<OutDocReq> responce = new ArrayList<>();
		Date currentDate = new Date();
			
		List<OutDoc> savedBoxList = Service.saveOrUpdate(outDocRec, currentDate, DeviceId);
		for (OutDoc b : savedBoxList) {
			responce.add(new OutDocReq(b.getId(), b.getOperation().getId(), b.getNumber(), b.getComment(), b.getDate(),
					b.getReceivedFromMobileDate(),b.division.getCode(),b.user.getId()));
		}
		return responce;
	}
	
	@PostMapping("/outDocPost") 
	public List<OutDocReq> outDocPost(@RequestBody @Valid ArrayList<String> outDocId) throws Exception{
		//OutDocReq responce = new OutDocReq();
		List<OutDocReq> responce = new ArrayList<>();
		List<OutDoc> savedBoxList = cRepository.findByIdNotIn(outDocId);
		for (OutDoc b : savedBoxList) {
			responce.add(new OutDocReq(b.getId(), b.getOperation().getId(), b.getNumber(), b.getComment(), b.getDate(),
					b.getReceivedFromMobileDate(),b.division.getCode(),b.user.getId()));
		}
		return responce;
	}
	
	@GetMapping("/outDocGet") 
	public List<OutDocReq> outDocGet(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws Exception{
		List<OutDocReq> responce = new ArrayList<>();
		List<OutDoc> savedBoxList = new ArrayList<>();
		if (date==null) {	savedBoxList = cRepository.findAll();}
		else {	savedBoxList = cRepository.findByDateGreaterThan(date);}
			
		for (OutDoc b : savedBoxList) {
				responce.add(new OutDocReq(b.getId(), b.getOperation().getId(), b.getNumber(), b.getComment(), b.getDate(),
						b.getReceivedFromMobileDate(),b.division.getCode(),b.user.getId()));
				}
		return responce;
	}
	
	@GetMapping("/outDocSyncPageableCount") 
	public int getPartBoxCount(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		int		page = 0;
		Page p =  boxRepository.findByReceivedFromMobileDateGreaterThan(date, (new PageRequest(0, 1)));
		long recNumber = p.getTotalElements();
		page = (int)Math.ceil(recNumber/(double)pageSize); 
		System.out.println("Data syncronize... boxSyncPageableCount pages : " + page);
		return page;
	}
	
	@PostMapping("/outDocSync") 
	public OutDocWithBoxWithMovesWithPartsRequest outDocSync(@RequestBody @Valid ArrayList<String> outDocId) throws Exception{
		OutDocWithBoxWithMovesWithPartsRequest responce = new OutDocWithBoxWithMovesWithPartsRequest();

		long startSync = System.currentTimeMillis();	
		long start = 0;
		List<OutDoc> savedOutDocList = cRepository.findByIdNotIn(outDocId);
		for (OutDoc b : savedOutDocList) {
			responce.outDocReqList.add(new OutDocReq(b.getId(), b.getOperation().getId(), b.getNumber(), b.getComment(), b.getDate(),
					b.getReceivedFromMobileDate(),b.division.getCode(),b.user.getId()));
			//выбрать строки из partBox для этих накладных
			start = System.currentTimeMillis();
			List<PartBox> savedPartBoxList = partBoxRepository.findByOutDocId(b.getId());
			for (PartBox pb : savedPartBoxList) {
				responce.partBoxReqList.add(new PartBoxReq(pb.getId(),pb.getBoxMove().getId(), pb.getDepartment().getId(), pb.getEmployee().getId(), pb.getQuantity(),pb.getDate(), pb.getReceivedFromMobileDate(), pb.getOutDoc().getId()));

				Optional<BoxMove> savedBoxMove = boxMovesRepository.findById(pb.getBoxMove().getId());
				if (savedBoxMove.isPresent())
					responce.movesReqList.add(new MoveReq(savedBoxMove.get().getId(), savedBoxMove.get().getBox().getId(), 
							savedBoxMove.get().getOperation().getId(), savedBoxMove.get().getDate(), savedBoxMove.get().getReceivedFromMobileDate()));
				
				Optional<Box> savedBox = boxRepository.findById(savedBoxMove.get().getBox().getId());
				if (savedBox.isPresent())
					responce.boxReqList.add(new BoxReq(savedBox.get().getId(), savedBox.get().getQuantityBox(), 
							savedBox.get().getNumBox(), savedBox.get().getMasterData().getId(), savedBox.get().getDate(), 
							savedBox.get().getReceivedFromMobileDate(), savedBox.get().getArchive()));				
			}
			System.out.println("partBoxService.Add " + (System.currentTimeMillis() - start));
		}
		
		System.out.println("outDocService.Save " + (System.currentTimeMillis() - startSync));

		return responce;
	}
	
	@PostMapping("/outDocRequest")
	@Transactional
	public OutDocWithBoxWithMovesWithPartsRequest addOutDoc(@RequestBody @Valid OutDocWithBoxWithMovesWithPartsRequest theRequest) throws Exception{
		OutDocWithBoxWithMovesWithPartsRequest responce = new OutDocWithBoxWithMovesWithPartsRequest();
		Date currentDate = new Date();
		long start = System.currentTimeMillis();
		List<OutDoc> savedOutDocList = Service.saveOrUpdate(theRequest.outDocReqList, currentDate, null);
		for (OutDoc b : savedOutDocList) {
			responce.outDocReqList.add(new OutDocReq(b.getId(), b.getOperation().getId(), b.getNumber(), b.getComment(), b.getDate(),
					b.getReceivedFromMobileDate(),b.division.getCode(),b.user.getId()));
		}
		System.out.println("outDocService.Save " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		List<Box> savedBoxList = boxService.save(theRequest.boxReqList, currentDate);
		for (Box b : savedBoxList) {
			responce.boxReqList.add(new BoxReq(b.getId(), b.getQuantityBox(), b.getNumBox(), b.getMasterData().getId(), 
					b.getDate(), b.getReceivedFromMobileDate(), b.getArchive()));
		}
		System.out.println("boxService.Save " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		List<BoxMove> savedBoxMoveList = boxMovesService.save(theRequest.movesReqList, currentDate);	
		System.out.println("boxMovesService.Save " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		List<PartBox> savedPartBoxList = partBoxService.save(theRequest.partBoxReqList, currentDate);
		System.out.println("partBoxService.Save " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		for (PartBox pb : savedPartBoxList) {
			responce.partBoxReqList.add(new PartBoxReq(pb.getId(),pb.getBoxMove().getId(), pb.getDepartment().getId(), 
					pb.getEmployee().getId(), pb.getQuantity(),pb.getDate(), pb.getReceivedFromMobileDate(), pb.getOutDoc().getId()));
		};
		for (BoxMove bm : savedBoxMoveList) {
			if (bm.getOperation().getId() == 9999) {
				boxService.updateArchive(bm.getBox());
				
				int prodInOrderCount = masterDataRepository.findById(bm.getBox().getMasterData().getId()).get().getCountProdInOrder();
				List<String> boxIdList = boxRepository.findByMasterDataId(bm.getBox().getMasterData().getId()).stream().map(Box::getId).collect(Collectors.toList());
				List<String> boxMoveIdList = boxMovesRepository.findByBoxIdInAndOperationId(boxIdList, (long)9999).stream().map(BoxMove::getId).collect(Collectors.toList());
				List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdIn(boxMoveIdList).stream().map(PartBox::getQuantity).collect(Collectors.toList());
				int sum = partBoxQuantityList.stream().mapToInt(Integer::intValue).sum();
				
				if (sum == prodInOrderCount) {
					Optional<MasterData> md = masterDataRepository.findById(bm.getBox().getMasterData().getId());
					if (md.isPresent()) {
						md.get().setArchive(true);
						masterDataRepository.save(md.get());
					}
				}
			}
			responce.movesReqList.add(new MoveReq(bm.getId(), bm.getBox().getId(), bm.getOperation().getId(), 
					bm.getDate(), bm.getReceivedFromMobileDate()));
		}
		System.out.println("BoxMove cycle " + (System.currentTimeMillis() - start));
		return responce;
	}
	
	@GetMapping("/outDocOper2") 
	public OutDocWeb getOutDocWeb(@RequestBody @Valid ArrayList<String> outDocId) throws Exception{
		OutDocWeb responce = new OutDocWeb();

		long startSync = System.currentTimeMillis();	
		long start = 0;
		
		System.out.println("outDocService.Save " + (System.currentTimeMillis() - startSync));

		return responce;
	}
}
