package hello.OutDoorOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hello.utils;
import hello.Client.ClientRepository;
import hello.OutDoorOrderRow.OutDoorOrderRow;
import hello.OutDoorOrderRow.OutDoorOrderRowRepository;


@Controller
//@RequestMapping(value={"/viewOutDoorOrder","/viewOutDoorOrder/{name}", "viewOutDoorOrderByClient", "/viewOutDoorOrderByClient/{name}"})

public class ViewOutDoorOrderController {
	@Autowired
	OutDoorOrderRepository repository;
	@Autowired
	OutDoorOrderRowRepository rowRepository;
	@Autowired ClientRepository cRepository;

	ViewOutDoorOrderFilter filter = new ViewOutDoorOrderFilter();
	private static final String internalClientUrl = "/viewOutDoorOrderByClient";
	private static final String externalClientUrl = "/login/viewOutDoorOrderByClient";
	private static final String internalProductUrl = "/viewOutDoorOrder";
	private static final String externalProductUrl = "/login/viewOutDoorOrder";
	
    @GetMapping(value={internalClientUrl, externalClientUrl})
	public ModelAndView getCl(@RequestParam(name="id", required=false) String id, @RequestParam(name="name", required=false) String name,
			@RequestParam(name="color", required=false) String color,
			@RequestParam(name="liner", required=false) String liner,
			@RequestParam(name="rant", required=false) String rant,
			@RequestParam(name="shpalt", required=false) String shpalt,
			@RequestParam(name = "sort-field") final String sortField,
            @RequestParam(name = "sort-dir") final String sortDir,
            final Model model) throws ParseException {
		//this.name = name;
		return mavPostCl(filter, sortField, sortDir, id, name, color, liner, rant, shpalt);

	}

	@PostMapping(value={internalClientUrl, externalClientUrl},
			params= {"action=filter", "sort-field", "sort-dir"})
	public ModelAndView mavPostCl(

	    @ModelAttribute("filter") ViewOutDoorOrderFilter filter, 
	    @RequestParam(value = "sort-field") String sortField, 
	    @RequestParam(value = "sort-dir") String sortDir,
	    @RequestParam(name="id", required=false) String id, @RequestParam(name="name", required=false) String name,
	    @RequestParam(name="color", required=false) String color,
	    @RequestParam(name="liner", required=false) String liner,
		@RequestParam(name="rant", required=false) String rant,
		@RequestParam(name="shpalt", required=false) String shpalt) throws ParseException {
		this.filter = filter;
		ModelAndView mv = new ModelAndView();
		List<ClientRep> rowList = new ArrayList<>();
		
		id = ((id != null) ? id : "null");
		name = ((name != null) ? name: "null");
		color = ((color != null) ? (color.isEmpty() ? "null" : color) : "null");
		liner = ((liner != null) ? (liner.isEmpty() ? "null" : liner) : "null"); 
		rant = ((rant != null) ? (rant.isEmpty() ? "null" : rant) : "null"); 
		shpalt = ((shpalt != null) ? (shpalt.isEmpty() ? "null" : shpalt) : "null"); 
		//shpalt = (shpalt.equals("..") ? "..." : shpalt);
		//name = (id.contains("name=") ? id : (name.contains("name=") ? name : ""));
		
		
		if (filter.getDateS() == null) filter.setDateS(utils.getStartOfDay(new Date()).toString());
		if (filter.getDateE() == null) filter.setDateE(filter.getDateS());

		if (!id.contains("null")){		
			name = ((name != null) ? name : "null");
			/*rowList = rowRepository.findByOutDoorOrderIdInGrouped(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
	    				utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())), 
	    				(id.contains("id=") ? id.substring(id.indexOf("=")+1, id.length()) : id));*/
		} else {
			if (!name.equals("null")) {
				if (color.equals("null"))
					rowList = rowRepository.findClientsByProductAndOutDoorOrderIdIn(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())),name);
				else
					rowList = rowRepository.findClientsByProductAndReq(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
							utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())),
							name,color,liner,rant,shpalt);
			}
			else
				rowList = rowRepository.findByOutDoorOrderIdInGroupByClient(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())));
		}
	    	if (!rowList.isEmpty()) {  	
	    		if (sortField.contains("name")) {
	    			if (sortDir.contains("asc")) {
		    			Collections.sort(rowList, new Comparator<ClientRep>() {
		    			    @Override
		    			    public int compare(ClientRep a1, ClientRep a2) {
		    			        return a1.getName().compareTo(a2.getName());
		    			    }
		    			});
	    			}else {
		    			Collections.sort(rowList, new Comparator<ClientRep>() {
		    			    @Override
		    			    public int compare(ClientRep a1, ClientRep a2) {
		    			        return a2.getName().compareTo(a1.getName());
		    			    }
		    			});
	    			}
	    		}
	    		if (sortField.contains("number")) {
	    			if (sortDir.contains("asc")) rowList.sort(Comparator.comparingLong(ClientRep::getNumber));
	    			else 	    		rowList.sort(Comparator.comparingLong(ClientRep::getNumber).reversed());
	    		}
	    	}


		mv.addObject("outDoorOrderByClient", rowList);
		mv.addObject("internalProductUrl", internalProductUrl);
		mv.addObject("externalProductUrl", externalProductUrl);
		mv.addObject("bDet", !id.equals("null"));
		mv.addObject("pName", (!name.equals("null") ? name : null));
		mv.addObject("pId", (!id.equals("null") ? id: null));
		mv.addObject("sId", (!id.equals("null")  ? cRepository.findOneByid(id).name: null));
		mv.addObject("pColor", color);
		mv.addObject("pLiner", liner);
		mv.addObject("pRant", rant);
		mv.addObject("pShpalt", shpalt);
		
		String pReq = (color.equals("null") ? "" : "Цвет: "+color)+" "+(liner.equals("null") ? "" : "Подклада: "+liner)+" "
				+(rant.equals("null") ? "" : "Рант: "+rant)+" "	+(shpalt.equals("null") ? "" : "Шпальт: "+shpalt);
		mv.addObject("pReq", pReq.trim().equals("") ? null : pReq);
		
		mv.addObject("filter", filter);
		mv.setViewName("viewOutDoorOrderByClient");
		mv.addObject("sortField", sortField);
		mv.addObject("sortDir", sortDir);
		mv.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		return mv;
	}
	
	
    @GetMapping(value={internalProductUrl,externalProductUrl})
	public ModelAndView get(
			@RequestParam(name = "sort-field") final String sortField,
            @RequestParam(name = "sort-dir") final String sortDir,
            @RequestParam(name="name", required=false) String name, 
            @RequestParam(name="id", required=false) String id,
            final Model model) throws ParseException {
		//this.name = name;
		return mavPost(filter, sortField, sortDir, name, id);

	}

	@PostMapping(value={internalProductUrl,externalProductUrl},
			params= {"action=filter", "sort-field", "sort-dir"})
	public ModelAndView mavPost(

	    @ModelAttribute("filter") ViewOutDoorOrderFilter filter, 
	    @RequestParam(value = "sort-field") String sortField, 
	    @RequestParam(value = "sort-dir") String sortDir,
	    @RequestParam(value="name", required=false) String name, 
	    @RequestParam(value="id", required=false) String id) throws ParseException {
		this.filter = filter;
		ModelAndView mv = new ModelAndView();
		List<OutDoorOrderRow> rowList = new ArrayList<>();
		List<OutDoorOrderRep> orderRep = new ArrayList<>();
		
		if (filter.getDateS() == null) filter.setDateS(utils.getStartOfDay(new Date()).toString());
		if (filter.getDateE() == null) filter.setDateE(filter.getDateS());
		
		name = ((name != null) ? name : "null");
		id = ((id != null) ? id : "null");
		
			
		if (!name.equals("null")){ //Есть подошва
			if (!id.equals("null"))// И клиент
				rowList = rowRepository.findByOutDoorOrderIdInGroupByProductAndClient(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
	    				utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())), 
	    				(name.contains("name=") ? name.substring(name.indexOf("=")+1, name.length()) : name), 
	    				(id.contains("id=") ? id.substring(id.indexOf("=")+1, id.length()) : id));
			else 	
				rowList = rowRepository.findByOutDoorOrderIdInGrouped(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
	    				utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())), 
	    				(name.contains("name=") ? name.substring(name.indexOf("=")+1, name.length()) : name));
		} else { //Нет подошвы
			if (!id.equals("null")) // Есть клиент нужно ограничить клиентом тут.
				rowList = rowRepository.findByClientAndOutDoorOrderIdInGroupByProduct(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())), 
	    				(id.contains("id=") ? id.substring(id.indexOf("=")+1, id.length()) : id));
			else
				rowList = rowRepository.findByOutDoorOrderIdInGroupByProduct(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDateE())));
		}
	    	if (!rowList.isEmpty()) {  	
	    		if (sortField.contains("name")) {
	    			if (sortDir.contains("asc")) {
		    			Collections.sort(rowList, new Comparator<OutDoorOrderRow>() {
		    			    @Override
		    			    public int compare(OutDoorOrderRow a1, OutDoorOrderRow a2) {
		    			        return a1.getProduct().getName().compareTo(a2.getProduct().getName());
		    			    }
		    			});
	    			}else {
		    			Collections.sort(rowList, new Comparator<OutDoorOrderRow>() {
		    			    @Override
		    			    public int compare(OutDoorOrderRow a1, OutDoorOrderRow a2) {
		    			        return a2.getProduct().getName().compareTo(a1.getProduct().getName());
		    			    }
		    			});
	    			}
	    		}
	    		if (sortField.contains("number")) {
	    			if (sortDir.contains("asc")) {
		    			Collections.sort(rowList, new Comparator<OutDoorOrderRow>() {
		    			    @Override
		    			    public int compare(OutDoorOrderRow a1, OutDoorOrderRow a2) {
		    			        return a1.number - a2.number;
		    			    }
		    			});
	    			} else 	    		rowList.sort(Comparator.comparingLong(OutDoorOrderRow::getNumber).reversed());
	    		}


		    	for (int i=0; i < rowList.size(); i++) {
			    			orderRep.add(new OutDoorOrderRep(rowList.get(i).getProduct().name, 
			    					((rowList.get(i).getColor() == null) ? "" : rowList.get(i).getColor().name), 
			    					((rowList.get(i).getLiner() == null) ? "" : rowList.get(i).getLiner().name),
			    					((rowList.get(i).getRant() == null) ? "" : rowList.get(i).getRant().name),
			    					((rowList.get(i).getShpalt() == null) ? "" : rowList.get(i).getShpalt().name),
			    					((rowList.get(i).attribute == null) ? "" : rowList.get(i).attribute),
			    					rowList.get(i).getNumber().toString(), (!name.contains("null"))));	
				}
	    	}

		
		mv.addObject("outDoorOrder", orderRep);
		mv.addObject("internalClientUrl", internalClientUrl);
		mv.addObject("externalClientUrl", externalClientUrl);
		mv.addObject("bDet", !name.contains("null"));
		mv.addObject("pName", (!name.contains("null") ? name : null));
		mv.addObject("pId", (!id.contains("null") ? id: null));
		mv.addObject("sId", (!id.contains("null") ? cRepository.findOneByid(id).name: null));
		
		mv.addObject("filter", filter);
		mv.setViewName("viewOutDoorOrder");
		mv.addObject("sortField", sortField);
		mv.addObject("sortDir", sortDir);
		mv.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		return mv;
	}
	
	public boolean containsName(final List<OutDoorOrderRep> list, final String name){
	    return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	
	public void perform(List<OutDoorOrderRep> list, final String name){
	    list.stream().filter(o -> o.getName().equals(name)).forEach(
	            o -> {
	            		o.setbDet(o.getbDet()==null ? true : (!o.getbDet()));
	            		System.out.println("o.setbDet with name " + o.name + " to " + o.getbDet());
	            }
	    );
	}
	
	/*
	 *                                 		<th:block th:if="${pName == null}">
<td> <a th:href="@{__${#httpServletRequest.requestURI}__?sort-field=__${sortField}__&sort-dir=__${sortDir}__&name=__${p.name}__&id=__${pId}__}" th:text="${p.name}"></a></td>			                    
				                    </th:block>
				                    <th:block th:if="${pName != null}">
				                    		<td th:text="${p.name}"></td>				                    
				                    </th:block>
	 * 
	 * 
<th:block th:if="(${pName != null}and${pId == null}and${bDet})">
<th> <a th:href="@{'/viewOutDoorOrderByClient?sort-field='+ ${sortField}+'&sort-dir=' +${sortDir}+'&name='+${p.name}+'&id='+${pId}+'&color='+${p.sColor}+'&liner='+${p.sLiner}+'&rant='+${p.sRant}+'&shpalt='+${p.sShpalt}}" th:text="Заказчики"></a></th>
									</th:block>
									<th:block th:if="(${pId == null}and${!bDet})">
<th> <a th:href="@{'/viewOutDoorOrderByClient?sort-field='+ ${sortField}+'&sort-dir=' +${sortDir}+'&name='+${p.name}}" th:text="Заказчики"></a></th>
									</th:block>
									

                        <p>Hello, [[${#httpServletRequest.remoteUser}]]!</p>
                        <p>Hello, [[${#httpServletRequest.requestURI}]]!</p>
                        <p>Hello, [[${#httpServletRequest.requestURL}]]!</p>
<h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>

                        <a th:href="@{__${#httpServletRequest.requestURI}__?sort-field=__${sortField}__&sort-dir=__${sortDir}__&name=__${pName}__&id=__${pId}__}">Click to More</a>

								                        	<th:block th:if="${pId!= null}">
	                       		<h5 class="my-3" th:text="'по заказчику: '+${sId}"></h5>
							</th:block>
									*/
}
