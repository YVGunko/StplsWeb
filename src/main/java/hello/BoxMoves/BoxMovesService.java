package hello.BoxMoves;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.Controller.MoveReq;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;


@Service
public class BoxMovesService {
	@Autowired
	private BoxMovesRepository boxMovesRepository;
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private OperationRepository operationRepository;
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