package hello.Web.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;

@RestController
public class OrderMoveRepController {
	@Autowired
	private BoxMovesRepository boxMovesRepository;
	@Autowired
	private PartBoxRepository partBoxRepository;
	@Autowired
	OperationRepository operationRepository;
	
	@GetMapping("/getOrderMoveRep") 
	public List<OrderMoveRep> getOrderMoveRep (@RequestParam(value="dateS", required=true) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date dateS,
			@RequestParam(value="dateE", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date dateE) {
		List<BoxMove> bmList = new ArrayList<>();
		List<Operation> opList = new ArrayList<>();
		List<OrderMoveRep> orderMoveRep = new ArrayList<>();
	
		Long prodNumber = (long)0;
		
		if (dateS != null ){
			if (dateE == null) dateE = dateS;
	    		opList.addAll(operationRepository.findAllByOrderByIdAsc());
	    		
	    		for (int i=0; i < opList.size(); i++) {
		    		bmList = boxMovesRepository.findByOperationIdAndDateBetweenOrderByDateAscOperationIdAsc(opList.get(i).getId(),
		    				dateS, dateE);
		    		if (!bmList.isEmpty()) {
					prodNumber = partBoxRepository.findByBoxMoveIdIn(bmList.stream().map(BoxMove::getId).collect(Collectors.toList())).stream().mapToLong(PartBox::getQuantity).sum();
					orderMoveRep.add(new OrderMoveRep(opList.get(i).getId() ,opList.get(i).getName(), (long)bmList.size(), prodNumber));	
				}
	    		}
		} 
		return orderMoveRep;
	}
}
