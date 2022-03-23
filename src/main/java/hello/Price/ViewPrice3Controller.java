package hello.Price;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import hello.Client.ClientRepository;


@Controller
@RequestMapping(path = "/login/viewPrice3")

public class ViewPrice3Controller {

	public static ViewPriceFilter staticPriceFilter;
	
	@Autowired PriceRepository repositoryPrice;
	@Autowired PriceTypeRepository repositoryPT;
	@Autowired PriceRootRepository repositoryPR;
	@Autowired ClientRepository clientRepository;
	@Autowired PriceColumnService pcService;
	@Autowired PriceRootService servicePR;
	@Autowired PriceTypeService servicePT;
	@Autowired PriceService servicePrice;
	
	ViewPriceFilter priceFilter = new ViewPriceFilter();
	
	@ModelAttribute("priceTypes")
	public List<PriceType> populatePriceTypes() {
		ServletRequestAttributes attributes = 
		        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		if (session.getAttribute("editable") != null) 
			return repositoryPT.findByIdGreaterThanOrderByName(session.getAttribute("editable").equals(true) ? -1 : 0);
		else return repositoryPT.findByIdGreaterThanOrderByName(0);
	}
	@ModelAttribute("priceRoots")
	public List<PriceRoot> populatePriceRoots(Integer pt) {
	    return servicePR.findActualListPriceRootByPriceTypeId(pt, priceFilter.getSample());
	}
	@GetMapping
	public ModelAndView get(@RequestParam(value = "sample", required = false) Boolean sample) throws ParseException {
		if (sample != null) priceFilter.setSample(sample); else priceFilter.setSample(false);
		
		ServletRequestAttributes attributes = 
		        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		
		if (sample != null) session.setAttribute("prevSample", sample);
		else session.setAttribute("prevSample", (false));
		
		session.setAttribute("editable", (false));
		
		if (attributes.getRequest().isUserInRole("ROLE_TOP") || attributes.getRequest().isUserInRole("ROLE_ADMIN")) 
			session.setAttribute("allowedUser", (true));
		else session.setAttribute("allowedUser", (false));
		
		return mavPost(priceFilter);
	}

	@PostMapping(params="action=filter")
	public ModelAndView mavPost(@ModelAttribute("priceFilter") ViewPriceFilter priceFilter ) throws ParseException {
		staticPriceFilter = priceFilter;	
		this.priceFilter = priceFilter;
		ModelAndView mv = new ModelAndView();
		List<Price> price = new ArrayList<>();
		List<PriceWeb> priceWeb = new ArrayList<>();
		List<PriceRoot> prList = new ArrayList<>();
		List<PriceType> ptList = new ArrayList<>();
		Boolean editable = false;
		//PriceRoot pr = new PriceRoot();
		
		ServletRequestAttributes attributes = 
		        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		final Integer prevPriceTypeId = (Integer) session.getAttribute("priceTypeId");
		final Boolean prevSample = (Boolean) session.getAttribute("prevSample");
		//final Integer prevPriceRootId = (Integer) session.getAttribute("priceRootId");
		
		if (priceFilter.getEditable() == null) priceFilter.setEditable(false); 
		else editable = priceFilter.getEditable();
		if (priceFilter.getSample() == null) priceFilter.setSample(false);
		if (priceFilter.getName() == null) priceFilter.setName("");
		//return pt.id if found else if editable =false return MIN(pt.id) > 0 if editable = true return MIN(pt.id) > -1
		priceFilter.setPriceType(servicePT.checkForNullAndReplaceWithTop(priceFilter.getPriceType(), priceFilter.getEditable()));
		
		if (!editable) { //priceFilter.setPriceRoot
				priceFilter.setPriceRoot(servicePR.findActualPriceRootByPriceTypeIdAndSample(priceFilter.getPriceType(),
						prevPriceTypeId,
						priceFilter.getPriceRoot(),
						priceFilter.getSample(),
						prevSample));
		} //if editable PriceRoot should not be set.
		
		if (!editable) { //select price by priceRoot and possible filter by name
			if (!priceFilter.getName().equals("")) 
				price = repositoryPrice.findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName (priceFilter.getPriceType().getId(),
						priceFilter.getPriceRoot().getId(),
						priceFilter.getName());
			else //TODO sorting here
				price = repositoryPrice.findByPriceTypeIdAndPriceRootIdOrderByName (priceFilter.getPriceType().getId(),
						priceFilter.getPriceRoot().getId());
			
			if (price!=null) 
				for (Price b : price) 
					//PriceWeb(Integer id, String name, String priceType, Double cost, Double paint, Double rant,
					//Double shpalt, Double number_per_box, Double weight)
					priceWeb.add(new PriceWeb(b.getId(), b.getName(),  
							pcService.getPriceColumns(b), pcService.getHeader(b), b.getRant(), 
							b.getShpalt()));
		} else { //if editable then if priceType == 0 get last priceRoots by dateOfChanges
			//try to obtain the list of PriceTypes
			if (priceFilter.getPriceType().getId().equals(0)) 
				ptList = repositoryPT.findAllByIdGreaterThan(0).orElseThrow(() -> new NoSuchElementException("PriceType exception on findAllByIdGreaterThan(0)"));
			else
				ptList.add(priceFilter.getPriceType());
			
			//try to obtain the list of PriceRoots
			prList = ptList
					.stream()
					.map(ptl -> {
						PriceRoot prh = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(ptl.getId(), priceFilter.getSample())
						.orElseThrow(() -> new NoSuchElementException("PriceRoot exception when trying to obtain the list of PriceRoots."));
						return prh;
								}
						).collect(Collectors.toList());
			
			//prList = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(priceFilter.getPriceType().getId(), priceFilter.getSample());
			if (prList == null) throw new NoSuchElementException("PriceRoot exception when trying to obtain the list of PriceRoots.");

			for (PriceRoot prl : prList) {
				if (!priceFilter.getName().equals("")) 
					price = repositoryPrice.findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName (prl.getPriceType().getId(), prl.getId(),
							priceFilter.getName());
				else {
					price = repositoryPrice.findByPriceTypeIdAndPriceRootIdOrderByName (prl.getPriceType().getId(), prl.getId());
				}
				if (price!=null) 
					priceWeb.addAll(
							price
							.stream()
							.map(b -> {
									PriceWeb pw = new PriceWeb (b.getId(), b.getName(), b.getRant(), b.getShpalt(),
											b.getPriceRoot().getDateOfChange(), b.getPriceRoot().getPriceType().getName(), priceFilter.getSample());
											return pw;}
							).collect(Collectors.toList())
					);
			}
		
		}
		session.setAttribute("priceTypeId", priceFilter.getPriceType().getId());
		//if (!editable) session.setAttribute("priceRootId", priceFilter.getPriceRoot().getId());
		session.setAttribute("prevSample", priceFilter.getSample());
		session.setAttribute("editable", editable);
		session.setAttribute("referer", "/login/viewPrice3");
		session.setAttribute("viewPriceFilter", priceFilter);
		
		mv.addObject("priceTypes", populatePriceTypes());
		mv.addObject("sample", priceFilter.getSample());
		mv.addObject("price", priceWeb);
		if (!editable) mv.addObject("priceRoots", populatePriceRoots(priceFilter.getPriceType().getId()));
		mv.addObject("priceFilter", priceFilter);
		mv.setViewName("viewPrice3");
		
		return mv;
	}
}
