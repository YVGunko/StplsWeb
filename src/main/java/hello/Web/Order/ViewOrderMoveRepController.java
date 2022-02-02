package hello.Web.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.utils;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;

@Controller
@RequestMapping("/viewProcessingOrders")
public class ViewOrderMoveRepController {
	@Autowired
	OperationRepository operationRepository;
	@Autowired
	BoxMovesRepository boxMovesRepository;
	@Autowired
	PartBoxRepository partBoxRepository;	
	OrderMoveRepFilter filter = new OrderMoveRepFilter();
	
	@GetMapping
	public ModelAndView get() throws ParseException {

		return mavPost(filter);

	}
	
	@PostMapping(params="action=filter")
	public ModelAndView mavPost(

	    @ModelAttribute("filter") OrderMoveRepFilter filter ) throws ParseException {
		this.filter = filter;
		ModelAndView mv = new ModelAndView();
		List<BoxMove> bmList = new ArrayList<>();
		List<Operation> opList = new ArrayList<>();
		List<OrderMoveRep> orderMoveRep = new ArrayList<>();

		Long prodNumber = (long)0;
		
		if (filter.getDateS() == null) System.out.println("orderFilter.getOrderText()==null");
		
		if (filter.getDateS() != null ){
			if (filter.getDateE() == null) filter.setDateE(filter.getDateS());
	    		opList.addAll(operationRepository.findAllByOrderByIdAsc());
	    		
	    		for (int i=0; i < opList.size(); i++) {
		    		bmList = boxMovesRepository.findByOperationIdAndDateBetweenOrderByDateAscOperationIdAsc(opList.get(i).getId(),
		    				utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
		    				utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())));
		    		if (!bmList.isEmpty()) {
					prodNumber = partBoxRepository.findByBoxMoveIdIn(bmList.stream().map(BoxMove::getId).collect(Collectors.toList())).stream().mapToLong(PartBox::getQuantity).sum();
					orderMoveRep.add(new OrderMoveRep(opList.get(i).getId() ,opList.get(i).getName(), (long)bmList.size(), prodNumber));	
				}
	    		}
		} 
		
		mv.addObject("orderMoveReps", orderMoveRep);
		mv.addObject("filter", filter);
		mv.setViewName("viewProcessingOrders");
		
		return mv;
	}
}
