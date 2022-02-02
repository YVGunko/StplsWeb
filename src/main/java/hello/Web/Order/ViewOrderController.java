package hello.Web.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.MasterData.MasterData;
import hello.MasterData.MasterDataRepository;
import hello.OutDoc.OutDocRepository;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.Client.ClientRepository;
import hello.utils;
import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.Client.Client;

@Controller
@RequestMapping(value = {"/viewOrder", "/login/viewOrder"})
public class ViewOrderController {
	@Autowired
	DivisionRepository divisionRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	MasterDataRepository masterDataRepository;
	@Autowired
	BoxRepository	boxRepository;
	@Autowired
	BoxMovesRepository	boxMovesRepository;
	@Autowired
	PartBoxRepository	partBoxRepository;
	@Autowired
	OutDocRepository	outDocRepository;
	
	
	OrderFilter orderFilter = new OrderFilter();
	
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
	public ModelAndView get() throws ParseException {

		return mavPost(orderFilter);

	}
	
	@PostMapping(params="action=filter")
	public ModelAndView mavPost(

	    @ModelAttribute("orderFilter") OrderFilter orderFilter ) throws ParseException {
		this.orderFilter = orderFilter;
		ModelAndView mv = new ModelAndView();
		List<MasterData> masterData = new ArrayList<>();
		List<OrderWeb> orderWeb = new ArrayList<>();
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		try {
		
		if ((orderFilter.getOrderText() == null)||(orderFilter.getOrderText().isEmpty())) {
			if ((orderFilter.getDivision() != null) & (orderFilter.getClient() != null)) {
				if ((!orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
					masterData = masterDataRepository.findByDate1CBetweenAndDivision_codeAndClient_idOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
							utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
							orderFilter.getDivision().getCode(),
							orderFilter.getClient().getId());
				}else {
					if ((orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
						masterData = masterDataRepository.findByDate1CBetweenAndDivision_codeOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode());
					} 
					if ((!orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
						masterData = masterDataRepository.findByDate1CBetweenAndClient_idOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getClient().getId());						
					}
					if ((orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
						masterData = masterDataRepository.findByDate1CBetweenOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));						
					}
				}
			} else {
				if ((orderFilter.getDivision() == null) & (orderFilter.getClient() != null)) {
					if (orderFilter.getClient().getId().equals("0")){
						masterData = masterDataRepository.findByDate1CBetweenAndClient_idOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getClient().getId());
					} else {
						masterData = masterDataRepository.findByDate1CBetweenOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					}
				}
				if ((orderFilter.getDivision() != null) & (orderFilter.getClient() == null)) {
					if (orderFilter.getDivision().getCode().equals("0")){
						masterData = masterDataRepository.findByDate1CBetweenAndDivision_codeOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode());
					}else {
						masterData = masterDataRepository.findByDate1CBetweenOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					} 
				}
				if ((orderFilter.getDivision() == null) & (orderFilter.getClient() == null)) {
					masterData = masterDataRepository.findByDate1CBetweenOrderByDate1C(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
							utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
				}
			}

			if (masterData!=null) {
				for (MasterData b : masterData) {
					//Long id, String orderText, String customer, String nomenklature, String attrib, String sdate, 
					//String clientName, String divisionName, int countProdInOrder, int countProdInBox, int countBoxInOrder
					orderWeb.add(new OrderWeb(b.getId(), b.getOrderText(), b.getCustomer(), b.getNomenklature(),b.getAttrib(),
							utils.toStringOnlyDate(utils.toLocalDate(b.getDate1C())),
							b.getClient().getName(), b.getDivision().getName().substring(0,3), b.getCountProdInOrder(), b.getCountProdInBox(), b.getCountBoxInOrder()));
				}
			}
		} else {
			if ((orderFilter.getDivision() != null) & (orderFilter.getClient() != null)) {
				if ((!orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
					masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenAndDivision_codeAndClient_idOrderByDate1C(orderFilter.getOrderText(),
							utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
							utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
							orderFilter.getDivision().getCode(),
							orderFilter.getClient().getId());
				}else {
					if ((orderFilter.getClient().getId().equals("0"))&(!orderFilter.getDivision().getCode().equals("0"))){
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenAndDivision_codeOrderByDate1C(orderFilter.getOrderText(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode());
					} 
					if ((!orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenAndClient_idOrderByDate1C(orderFilter.getOrderText(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getClient().getId());						
					}
					if ((orderFilter.getClient().getId().equals("0"))&(orderFilter.getDivision().getCode().equals("0"))){
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenOrderByDate1C(orderFilter.getOrderText(), 
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));						
					}
				}
			} else {
				if ((orderFilter.getDivision() == null) & (orderFilter.getClient() != null)) {
					if (orderFilter.getClient().getId().equals("0")){
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenAndClient_idOrderByDate1C(orderFilter.getOrderText(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getClient().getId());
					} else {
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenOrderByDate1C(orderFilter.getOrderText(), 
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					}
				}
				if ((orderFilter.getDivision() != null) & (orderFilter.getClient() == null)) {
					if (orderFilter.getDivision().getCode().equals("0")){
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenAndDivision_codeOrderByDate1C(orderFilter.getOrderText(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())),
								orderFilter.getDivision().getCode());
					}else {
						masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenOrderByDate1C(orderFilter.getOrderText(), 
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
					} 
				}
				if ((orderFilter.getDivision() == null) & (orderFilter.getClient() == null)) {
					masterData = masterDataRepository.findByOrderTextContainsAndDate1CBetweenOrderByDate1C(orderFilter.getOrderText(), 
							utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateS())),
							utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(orderFilter.getDateE())));
				}
			}
			
			if (masterData!=null) {
				for (MasterData b : masterData) {
					//Long id, String orderText, String customer, String nomenklature, String attrib, String sdate, 
					//String clientName, String divisionName, int countProdInOrder, int countProdInBox, int countBoxInOrder
					orderWeb.add(new OrderWeb(b.getId(), b.getOrderText(), b.getCustomer(), b.getNomenklature(),b.getAttrib(),
							utils.toStringOnlyDate(utils.toLocalDate(b.getDate1C())),
							b.getClient().getName(), b.getDivision().getName().substring(0,3), b.getCountProdInOrder(), b.getCountProdInBox(), b.getCountBoxInOrder()));
				}
			}
		}
		//TODO теперь вытащить для каждой строки информацию о произведенных и отгруженных количествах
		//
		if (!orderWeb.isEmpty()) {
			//int lastPairQuantitySum = 0;
			//int lastBoxQuantitySum = 0;
			for (OrderWeb m : orderWeb) {
				List<String> bIdList = boxRepository.findByMasterDataId(m.getId()).stream().map(Box::getId).collect(Collectors.toList());//id
				if (!bIdList.isEmpty()) {
					
					int[] opArray = {1, 9999};
					for (int i=0; i<opArray.length; i++)
					{
						//выбираем Ид для последующего выбора партбоксов
						List<String> bmIdList = boxMovesRepository.findByOperationIdAndBoxIdIn(opArray[i],bIdList).stream().map(BoxMove::getId).collect(Collectors.toList());
						
						if (!bmIdList.isEmpty()) {
							if (opArray[i] == 1) {
								m.setProduced_box(bIdList.size());
								m.setProduced_pair(partBoxRepository.findByBoxMoveIdIn(bmIdList).stream().mapToInt(PartBox::getQuantity).sum());	
								m.setLast_produced_box(m.getCountBoxInOrder()-m.getProduced_box());
								m.setLast_produced_pair(m.getCountProdInOrder()-m.getProduced_pair());
							} else {
								m.setShiped_box(bmIdList.size());
								m.setShiped_pair(partBoxRepository.findByBoxMoveIdIn(bmIdList).stream().mapToInt(PartBox::getQuantity).sum());
								m.setLast_shiped_box(m.getCountBoxInOrder()-m.getShiped_box());
								m.setLast_shiped_pair(m.getCountProdInOrder()-m.getShiped_pair());
							}
													
							//bmIdList.clear();	
							
						}else {
							m.setShiped_box(0);
							m.setShiped_pair(0);
							m.setLast_shiped_box(m.getCountBoxInOrder());
							m.setLast_shiped_pair(m.getCountProdInOrder());						
						}
					}
				}else {
					m.setProduced_box(0);
					m.setProduced_pair(0);	
					m.setLast_produced_box(m.getCountBoxInOrder());
					m.setLast_produced_pair(m.getCountProdInOrder());
					m.setShiped_box(0);
					m.setShiped_pair(0);
					m.setLast_shiped_box(m.getCountBoxInOrder());
					m.setLast_shiped_pair(m.getCountProdInOrder());
				}
			}
		}
		} catch (Exception e) {
			System.out.println ("Null exception"+e);
		}	
		mv.addObject("orders", orderWeb);
		mv.addObject("orderFilter", orderFilter);
		mv.setViewName("viewOrder");
		
		return mv;
	}
}
