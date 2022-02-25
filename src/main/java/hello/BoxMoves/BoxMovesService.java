package hello.BoxMoves;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.Controller.BoxMoveBoxAndQuantityDTO;
import hello.Controller.MoveReq;
import hello.Department.DepartmentRepository;
import hello.MasterData.MasterDataRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;


@Service
public class BoxMovesService {
	@Autowired
	private BoxMovesRepository boxMovesRepository;
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private PartBoxRepository partBoxRepository;
	@Autowired
	MasterDataRepository masterDataRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private PartBoxService partBoxService;
	
	public int doCompareAndSaveIfEqual (String bmId, Integer quantityToCheck){
		Integer partBoxQuantity = partBoxRepository.getQuantityByBoxMoveId(bmId).stream().reduce(0,(x,y)->x+y);

		if (quantityToCheck == partBoxQuantity) {
			save(bmId); //BoxMove setSendToMaster... save
			return 1;
		}
		return 0;					
	}
	public void setSendToMasterDate(String qrd, long operationId) {
		long depId = 0;
		String orderId = null;
		final Date dateToSet = new Date();
		
		if (qrd == null || qrd.isEmpty()) return; 

		if (operationId != 9999) {
				final String sdepId = qrd.substring(qrd.indexOf(";")+1, qrd.length());
				if (sdepId == null) return;
				orderId = qrd.substring(0,qrd.indexOf(";"));
				depId = departmentRepository.findByCode(sdepId)
						.map(department -> department.getId())
						.orElseThrow(() -> new NoSuchElementException("Department: "+sdepId));
			}
		else orderId = qrd;

		if (orderId == null || orderId.isEmpty()) return; 
		
		final Integer boxNum = Integer.valueOf(orderId.substring(orderId.lastIndexOf(".")+1,orderId.length())); //номер коробки
		if (boxNum == null  || boxNum == 0) return; 
		
		orderId = orderId.substring(0, orderId.lastIndexOf("."));
		final Integer quantityBox = Integer.valueOf(orderId.substring(orderId.lastIndexOf(".")+1,orderId.length()));
		if (quantityBox == null  || quantityBox == 0) return; 
		
		final String ordId = orderId.substring(0, orderId.lastIndexOf("."));
		if (ordId == null || ordId.isEmpty()) return;
		//Was replaced with natural query because of high consumption
		final Long mdId = masterDataRepository.selectIdByOrderId(ordId)
				.orElseThrow(() -> new NoSuchElementException("Order: "+ordId)).longValue();

		final String boxId = boxRepository.getByMasterDataIdAndNumBox (mdId, boxNum)
				.map(box -> box.getId())
				.orElseThrow(() -> new NoSuchElementException("Order: "+ordId+", Box: "+boxNum));
		
		BoxMove bm = boxMovesRepository.getByOperationIdAndBoxId (operationId, boxId)
				.orElseThrow(() -> new NoSuchElementException("BoxMove. No such record. OperationId: "+operationId+", BoxId: "+boxId));
		//Write Date to field Send... of PartBox table and also BoxMove if quantity match 
		partBoxService.updSendToMasterDate(dateToSet, bm.getId(), depId, quantityBox);
	}
	//Save with native query with goal of reduce time consumption
	public void save(String boxMoveId) {
		//TODO save by native Query
		boxMovesRepository.updateSentToMasterDate(new Date(),boxMoveId);
	}
	//Normal one
	public List<BoxMove> save(List<MoveReq> boxMovesList, Date currentDate) throws Exception{
		System.out.println("boxMovesService.save start date: " + new Date());
		List<BoxMove> toBesavedBoxMoves = new ArrayList<>();

		for (MoveReq moveReq: boxMovesList) {
			Optional<Box> box = boxRepository.findById(moveReq.boxId);
			Optional <Operation> operation = operationRepository.findById(moveReq.operationId);
			if ((box.isPresent()) & (operation.isPresent())) {
					BoxMove boxMove = new BoxMove(moveReq.id, box.get(), operation.get(), moveReq.date, currentDate);
					toBesavedBoxMoves.add(boxMove);
			} else {
				System.out.println("boxMovesService. Operation= "+moveReq.operationId+", BoxMoveId= "+moveReq.id+" NO master records in the Box table, BoxId= " + moveReq.boxId);
			}
				
		}
		long start = System.currentTimeMillis();
		
		List<BoxMove> savedBoxMovesList =  boxMovesRepository.saveAll(toBesavedBoxMoves);
		System.out.println("boxMovesService.SaveAll took: " + (System.currentTimeMillis() - start)+" milis. for saving "+boxMovesList.size()+"records.");
		
		if (savedBoxMovesList.size() != toBesavedBoxMoves.size()) {
			throw new Exception("List<BoxMove> save: savedBoxMovesList is not equal to toBesavedBoxMoves");
		}
		return savedBoxMovesList;
	}
	
	public List<BoxMove> findAllWhereArhive(Date date) {
		int counter = 0;
		List<BoxMove> result = new ArrayList<>();
		if (date==null) { 
			for (BoxMove bm : boxMovesRepository.findAll()) {
				if (!boxRepository.findById(bm.getBox().getId()).get().getArchive()) {
					BoxMove boxMove = new BoxMove(bm.getId(), bm.getBox(), bm.getOperation(), bm.getDate(),bm.getReceivedFromMobileDate());
					result.add(boxMove);
				} 
				else 
					counter = counter + 1; 
			}
		}
		else {
			for (BoxMove bm : boxMovesRepository.findByDateGreaterThan(date)) {
				if (!boxRepository.findById(bm.getBox().getId()).get().getArchive()) {
					BoxMove boxMove = new BoxMove(bm.getId(), bm.getBox(), bm.getOperation(), bm.getDate(),bm.getReceivedFromMobileDate());
					result.add(boxMove);
				}
				else 
					counter = counter + 1; 
			}
		}
		System.out.println("BoxMove.findAllWhereArhive.Skiped rows: " + String.valueOf(counter));
		return result;
	}
	
}