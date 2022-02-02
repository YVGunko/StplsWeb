package hello.Web.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.MasterData.MasterData;
import hello.MasterData.MasterDataRepository;
import hello.utils;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;


@Controller
@RequestMapping("/viewOrdersGroup")
public class ViewOrdersGroupController {
	@Autowired
	DivisionRepository divisionRepository;
	@Autowired
	MasterDataRepository masterDataRepository;
	@Autowired
	BoxMovesRepository boxMovesRepository;
	
	OrdersGroupFilter ogFilter = new OrdersGroupFilter();
	String id, sDate;
	
	@ModelAttribute("divisions")
	public List<Division> populateDivisions() {
	    return divisionRepository.findAll();
	}
	
	@RequestMapping("id/{id}/date/{date}")
	public ModelAndView get(@PathVariable("id") String id, @PathVariable("date") String date) throws ParseException {
		this.id = id;
		this.sDate = date;
		return mavPost(ogFilter);

	}
	
	@PostMapping(params="action=filter")
	public ModelAndView mavPost(

	    @ModelAttribute("ogFilter") OrdersGroupFilter ogFilter) throws ParseException {
		this.ogFilter = ogFilter;


		ModelAndView mv = new ModelAndView();

		List<OrderGroupReq> result = new ArrayList<>();
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String orderText = "", customer = "";
		
		if (ogFilter.getOrderText() != null) {
			if (ogFilter.getOrderText().length() != 0) orderText = ogFilter.getOrderText(); else orderText = "";
		}
		if (ogFilter.getCustomer() != null) {
			if (ogFilter.getCustomer().length() != 0) customer = ogFilter.getCustomer(); else customer = "";
		}
		
	    		
    		List<String> tmp = new ArrayList<>();
    		List<BoxMove> bmList = new ArrayList<>();
    		
    		Date date = new Date();
    		try {
    			date = utils.sDateToDate (sDate, "yyyy-MM-dd");
    		}catch(Exception eta){
    		    eta.printStackTrace();
    		}
    		
    		if (id != null) {

	    		bmList = boxMovesRepository.findByOperationIdAndDateBetweenOrderByDateAsc(Long.parseLong(id),utils.atStartOfDay(date),utils.atEndOfDay(date));
	    		for (BoxMove bm : bmList) {
	    			if (masterDataRepository.findById(bm.getBox().getMasterData().getId()).isPresent()) {
	    				MasterData tmpOrder = masterDataRepository.findById(bm.getBox().getMasterData().getId()).get();
	    				if (!tmp.contains(tmpOrder.getOrderText())) {
	    					if ((orderText == "") & (customer == "")) {
		    					tmp.add(tmpOrder.getOrderText());
		    					//String orderText, String customer, Long sumProd, Long sumBox, int countProdInBox, String sDate, String division_code
		    					result.add(new OrderGroupReq(tmpOrder.getOrderText(), tmpOrder.getCustomer(), (long)0, (long)0, 0,
		    							utils.getStartOfDay(tmpOrder.getDate1C()), tmpOrder.getDivision().getName(), tmpOrder.getDivision(), tmpOrder.getClient()));
	    					}else {
	    						if ((orderText == "") & (customer != "")) {
	    							if (tmpOrder.getCustomer().contains(customer)) {
	    								tmp.add(tmpOrder.getOrderText());
				    					//String orderText, String customer, Long sumProd, Long sumBox, int countProdInBox, String sDate, String division_code
				    					result.add(new OrderGroupReq(tmpOrder.getOrderText(), tmpOrder.getCustomer(), (long)0, (long)0, 0,
				    							utils.getStartOfDay(tmpOrder.getDate1C()), tmpOrder.getDivision().getName(), tmpOrder.getDivision(), tmpOrder.getClient()));
	    							}
	    						}
	    						if ((orderText != "") & (customer == "")) {
	    							if (tmpOrder.getOrderText().contains(orderText))	{    							
	    								tmp.add(tmpOrder.getOrderText());
				    					//String orderText, String customer, Long sumProd, Long sumBox, int countProdInBox, String sDate, String division_code
				    					result.add(new OrderGroupReq(tmpOrder.getOrderText(), tmpOrder.getCustomer(), (long)0, (long)0, 0,
				    							utils.getStartOfDay(tmpOrder.getDate1C()), tmpOrder.getDivision().getName(), tmpOrder.getDivision(), tmpOrder.getClient()));
	    							}
	    						}
	    						if ((orderText != "") & (customer != "")) {
	    							if (tmpOrder.getCustomer().contains(customer)&tmpOrder.getOrderText().contains(orderText)) {
	    								tmp.add(tmpOrder.getOrderText());
				    					//String orderText, String customer, Long sumProd, Long sumBox, int countProdInBox, String sDate, String division_code
				    					result.add(new OrderGroupReq(tmpOrder.getOrderText(), tmpOrder.getCustomer(), (long)0, (long)0, 0,
				    							utils.getStartOfDay(tmpOrder.getDate1C()), tmpOrder.getDivision().getName(), tmpOrder.getDivision(), tmpOrder.getClient()));
	    							}
	    						} 
	    					}
	    				}
	    			}
	    		}

    		}
		
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
		
		mv.addObject("orders", result);
		mv.addObject("ogFilter", ogFilter);
		mv.setViewName("viewOrdersGroup");
		
		return mv;
	}
}
