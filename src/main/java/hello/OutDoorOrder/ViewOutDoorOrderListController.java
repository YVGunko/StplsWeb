package hello.OutDoorOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hello.utils;
import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;
import hello.OutDoorOrderRow.OutDoorOrderRowService;


@Controller
@RequestMapping(value= {"/login/viewOutDoorOrderList","/viewOutDoorOrderList"})
public class ViewOutDoorOrderListController {
	
	@Autowired
	OutDoorOrderRepository repository;
	@Autowired
	OutDoorOrderRowRepository rowRepository;
	@Autowired
	DivisionRepository divisionRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	OutDoorOrderRowService rowService;
	
	ViewOutDoorOrderListFilter orderFilter = new ViewOutDoorOrderListFilter();
	
	@ModelAttribute("divisions")
	public List<Division> populateDivisions() {
	    return divisionRepository.findAll();
	}
	
	@ModelAttribute("clients")
	public List<Client> populateClients() {
		List<Client> responce = new ArrayList<>();
		responce.add(new Client("0", "..."));
		responce.addAll(clientRepository.findAllByOrderByName());
	    return responce;
	}
	
	@GetMapping
	public ModelAndView get(@RequestParam(value = "sample", required = true) Boolean sample) throws ParseException {
		if (sample != null) orderFilter.setSample(sample);
		return mavPost(orderFilter);

	}
	/*
	 * , 
		@RequestParam(name = "sort-field") final String sortField,
            @RequestParam(name = "sort-dir") final String sortDir
	 * */
	@PostMapping(params="action=filter")
	public ModelAndView mavPost( @ModelAttribute("orderFilter") ViewOutDoorOrderListFilter orderFilter) throws ParseException {
		this.orderFilter = orderFilter;
		ModelAndView mv = new ModelAndView();
		List<OutDoorOrderListRep> orderWeb = new ArrayList<>();
		List<OutDoorOrder> masterData = new ArrayList<>();
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		if (orderFilter.getSample() == null) {
			System.out.println("orderFilter.getSample()==null");
			orderFilter.setSample(false);
		}
		if (orderFilter.getOrderText() == null) {
			System.out.println("orderFilter.getOrderText()==null");
			orderFilter.setOrderText("");
		}
		if (orderFilter.getDivision() == null) {
			System.out.println("orderFilter.getDivision()==null");
			orderFilter.setDivision(new Division("0"));
		}
		if (orderFilter.getClient() == null) {
			System.out.println("orderFilter.getClient()==null");
			orderFilter.setClient(new Client("0"));
		}
		if (orderFilter.getDateS() == null) {
			System.out.println("orderFilter.getDateS()==null");
			orderFilter.setDateS(utils.getStartOfDay(new Date()).toString());
		}
		if (orderFilter.getDateE() == null) {
			System.out.println("orderFilter.getDateE()==null");
			orderFilter.setDateE(utils.getEndOfDay(new Date()).toString());
		}
		
		if (!orderFilter.getSample()) {
			if ((orderFilter.getOrderText() == null)||(orderFilter.getOrderText().isEmpty())) {
				if ((orderFilter.getDivision() != null) & (orderFilter.getClient() != null)) {
					if ((!orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
						masterData = repository.findByDateBetweenAndDivision_codeAndClient_idAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode(),
								orderFilter.getClient().getId());
					}else {
						if ((orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByDateBetweenAndDivision_codeAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						} 
						if ((!orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByDateBetweenAndClient_idAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());						
						}
						if ((orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByDateBetweenAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));						
						}
					}
				} else {
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() != null)) {
						if (orderFilter.getClient().getId().equals("0")){
							masterData = repository.findByDateBetweenAndClient_idAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());
						} else {
							masterData = repository.findByDateBetweenOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						}
					}
					if ((orderFilter.getDivision() != null) & (orderFilter.getClient() == null)) {
						if (orderFilter.getDivision().getCode().equals("0")){
							masterData = repository.findByDateBetweenAndDivision_codeAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						}else {
							masterData = repository.findByDateBetweenAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						} 
					}
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() == null)) {
						masterData = repository.findByDateBetweenAndSampleIsFalseOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					}
				}
	
				if (masterData!=null) {
					for (OutDoorOrder b : masterData) {
						//String id, String sDate, String sClient, String sDivision, String sDesc, String sUser
						orderWeb.add(new OutDoorOrderListRep(b.getId(), mdyFormat.format(b.getDate()), b.getClient().getName(), b.getDivision().getName(),  rowService.getGoods(b.getId()), b.getUser().getName()));
					}
				}
			} else {
				if ((orderFilter.getDivision() != null) & (orderFilter.getClient() != null)) {
					if ((!orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
						masterData = repository.findByIdContainsAndDateBetweenAndDivision_codeAndClient_idAndSampleIsFalseOrderByDate(orderFilter.getOrderText(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode(),
								orderFilter.getClient().getId());
					}else {
						if ((orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByIdContainsAndDateBetweenAndDivision_codeAndSampleIsFalseOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						} 
						if ((!orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByIdContainsAndDateBetweenAndClient_idAndSampleIsFalseOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());						
						}
						if ((orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByIdContainsAndDateBetweenAndSampleIsFalseOrderByDate(orderFilter.getOrderText(), 
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));						
						}
					}
				} else {
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() != null)) {
						if (orderFilter.getClient().getId().equals("0")){
							masterData = repository.findByIdContainsAndDateBetweenAndClient_idAndSampleIsFalseOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());
						} else {
							masterData = repository.findByIdContainsAndDateBetweenAndSampleIsFalseOrderByDate(orderFilter.getOrderText(), 
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						}
					}
					if ((orderFilter.getDivision() != null) & (orderFilter.getClient() == null)) {
						if (orderFilter.getDivision().getCode().equals("0")){
							masterData = repository.findByIdContainsAndDateBetweenAndDivision_codeAndSampleIsFalseOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						}else {
							masterData = repository.findByIdContainsAndDateBetweenAndSampleIsFalseOrderByDate(orderFilter.getOrderText(), 
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						} 
					}
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() == null)) {
						masterData = repository.findByIdContainsAndDateBetweenAndSampleIsFalseOrderByDate(orderFilter.getOrderText(), 
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					}
				}
				
				if (masterData!=null) {
					for (OutDoorOrder b : masterData) {
						//String id, String sDate, String sClient, String sDivision, String sDesc, String sUser
						orderWeb.add(new OutDoorOrderListRep(b.getId(), mdyFormat.format(b.getDate()), b.getClient().getName(), b.getDivision().getName(), rowService.getGoods(b.getId()), b.getUser().getName()));
					}
				}
			}
		} else {
			if ((orderFilter.getOrderText() == null)||(orderFilter.getOrderText().isEmpty())) {
				if ((orderFilter.getDivision() != null) & (orderFilter.getClient() != null)) {
					if ((!orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
						masterData = repository.findByDateBetweenAndDivision_codeAndClient_idAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode(),
								orderFilter.getClient().getId());
					}else {
						if ((orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByDateBetweenAndDivision_codeAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						} 
						if ((!orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByDateBetweenAndClient_idAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());						
						}
						if ((orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByDateBetweenAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));						
						}
					}
				} else {
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() != null)) {
						if (orderFilter.getClient().getId().equals("0")){
							masterData = repository.findByDateBetweenAndClient_idAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());
						} else {
							masterData = repository.findByDateBetweenAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						}
					}
					if ((orderFilter.getDivision() != null) & (orderFilter.getClient() == null)) {
						if (orderFilter.getDivision().getCode().equals("0")){
							masterData = repository.findByDateBetweenAndDivision_codeAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						}else {
							masterData = repository.findByDateBetweenAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						} 
					}
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() == null)) {
						masterData = repository.findByDateBetweenAndSampleIsTrueOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					}
				}
	
				if (masterData!=null) {
					for (OutDoorOrder b : masterData) {
						//String id, String sDate, String sClient, String sDivision, String sDesc, String sUser
						orderWeb.add(new OutDoorOrderListRep(b.getId(), mdyFormat.format(b.getDate()), b.getClient().getName(), b.getDivision().getName(),  rowService.getGoods(b.getId()), b.getUser().getName()));
					}
				}
			} else {
				if ((orderFilter.getDivision() != null) & (orderFilter.getClient() != null)) {
					if ((!orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
						masterData = repository.findByIdContainsAndDateBetweenAndDivision_codeAndClient_idAndSampleIsTrueOrderByDate(orderFilter.getOrderText(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode(),
								orderFilter.getClient().getId());
					}else {
						if ((orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByIdContainsAndDateBetweenAndDivision_codeAndSampleIsTrueOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						} 
						if ((!orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByIdContainsAndDateBetweenAndClient_idAndSampleIsTrueOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());						
						}
						if ((orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
							masterData = repository.findByIdContainsAndDateBetweenAndSampleIsTrueOrderByDate(orderFilter.getOrderText(), 
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));						
						}
					}
				} else {
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() != null)) {
						if (orderFilter.getClient().getId().equals("0")){
							masterData = repository.findByIdContainsAndDateBetweenAndClient_idAndSampleIsTrueOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getClient().getId());
						} else {
							masterData = repository.findByIdContainsAndDateBetweenAndSampleIsTrueOrderByDate(orderFilter.getOrderText(), 
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						}
					}
					if ((orderFilter.getDivision() != null) & (orderFilter.getClient() == null)) {
						if (orderFilter.getDivision().getCode().equals("0")){
							masterData = repository.findByIdContainsAndDateBetweenAndDivision_codeAndSampleIsTrueOrderByDate(orderFilter.getOrderText(),
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
									orderFilter.getDivision().getCode());
						}else {
							masterData = repository.findByIdContainsAndDateBetweenAndSampleIsTrueOrderByDate(orderFilter.getOrderText(), 
									utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
									utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
						} 
					}
					if ((orderFilter.getDivision() == null) & (orderFilter.getClient() == null)) {
						masterData = repository.findByIdContainsAndDateBetweenAndSampleIsTrueOrderByDate(orderFilter.getOrderText(), 
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					}
				}
				
				if (masterData!=null) {
					for (OutDoorOrder b : masterData) {
						//String id, String sDate, String sClient, String sDivision, String sDesc, String sUser
						orderWeb.add(new OutDoorOrderListRep(b.getId(), mdyFormat.format(b.getDate()), b.getClient().getName(), b.getDivision().getName(), rowService.getGoods(b.getId()), b.getUser().getName()));
					}
				}
			}
		}
        // sorting parameters
		//mv.addObject("sortField", sortField);
		//mv.addObject("sortDir", sortDir);
        //mv.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
		mv.addObject("sample", orderFilter.getSample());
		mv.addObject("outDoorOrderList", orderWeb);
		mv.addObject("filter", orderFilter);
		mv.setViewName("viewOutDoorOrderList");
		
		return mv;
	}
}
