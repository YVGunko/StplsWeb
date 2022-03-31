package hello.Price;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/viewPrice")

public class ViewPriceController {
	
	public static ViewPriceFilter staticPriceFilter;
	
	@Autowired PriceRepository repositoryPrice;
	@Autowired PriceTypeRepository repositoryPT;
	@Autowired PriceRootRepository repositoryPR;
	@Autowired PriceRootService servicePR;
	@Autowired PriceTypeService servicePT;
	@Autowired PriceService servicePrice;
	
	ViewPriceFilter priceFilter = new ViewPriceFilter();
	
	@ModelAttribute("priceTypes")
	public List<PriceType> populatePriceTypes() {
	    return repositoryPT.findByIdGreaterThanOrderByName(0);
	}
	@ModelAttribute("priceRoots")
	public List<PriceRoot> populatePriceRoots(Integer pt) {
		//if (this.priceFilter != null)
	    return servicePR.getTopPriceRootAsListByPriceTypeId(pt, this.priceFilter.getSample());
	}
	@GetMapping
	public ModelAndView get(@RequestParam(value = "sample", required = true) Boolean sample) throws ParseException {
		if (sample != null) priceFilter.setSample(sample);
		return mavPost(priceFilter);
	}
	@PostMapping(params="action=open")
	public ModelAndView mavOpen(@ModelAttribute("priceFilter") ViewPriceFilter priceFilter ) throws ParseException {
			this.priceFilter = priceFilter;
			ModelAndView mv = new ModelAndView();
			return mv;
	}	

	@PostMapping(params="action=filter")
	public ModelAndView mavPost(@ModelAttribute("priceFilter") ViewPriceFilter priceFilter ) throws ParseException {
			staticPriceFilter = priceFilter;
			this.priceFilter = priceFilter;
			ModelAndView mv = new ModelAndView();
			List<Price> price = new ArrayList<>();
			List<PriceWeb> priceWeb = new ArrayList<>();
			PriceRoot pr = new PriceRoot();
			ServletRequestAttributes attributes = 
			        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attributes.getRequest().getSession(true);
			Integer prevPriceTypeId = (Integer) session.getAttribute("priceTypeId");
			Boolean prevSample = (Boolean) session.getAttribute("prevSample");
			
			if (priceFilter.getSample() == null) priceFilter.setSample(false);
			if (priceFilter.getName() == null) priceFilter.setName("");
			priceFilter.setPriceType(servicePT.checkForNullAndReplaceWithTop(priceFilter.getPriceType(), false));
			
			pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc
					(priceFilter.getPriceType().getId(), priceFilter.getSample())
					.orElseThrow(() -> new NoSuchElementException("PriceRoot not found exception when trying to obtain a PriceRoot."));
			priceFilter.setPriceRoot(pr);
			
			if (!priceFilter.getName().equals("")) 
				price = repositoryPrice.findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName (priceFilter.getPriceType().getId(),
						priceFilter.getPriceRoot().getId(),
						priceFilter.getName())
				.orElseThrow(() -> 
						new NoSuchElementException("Price not found exception. PriceType="
						+priceFilter.getPriceType().getId()+", newPriceRoot="+priceFilter.getPriceRoot().getId()));
			else 
				price = repositoryPrice.findByPriceTypeIdAndPriceRootIdOrderByName (priceFilter.getPriceType().getId(),
						priceFilter.getPriceRoot().getId())
				.orElseThrow(() -> 
						new NoSuchElementException("Price not found exception. PriceType="
						+priceFilter.getPriceType().getId()+", newPriceRoot="+priceFilter.getPriceRoot().getId()));
			
			for (Price b : price) 
					//PriceWeb(Integer id, String name, Integer priceTypeId, Double cost, Double paint, Double rant,
					//Double shpalt, Double number_per_box, Double weight)
					priceWeb.add(new PriceWeb(b.getId(), b.getName(), b.getNumber_per_box(), b.getWeight(), b.getbRant(), b.getbLiner(), 
							priceFilter.getPriceRoot().getId(), priceFilter.getPriceRoot().getSample()));
			
			session.setAttribute("priceTypeId", priceFilter.getPriceType().getId());
			session.setAttribute("prevSample", priceFilter.getPriceRoot().getSample());
			mv.addObject("sample", priceFilter.getSample());
			mv.addObject("price", priceWeb);
			mv.addObject("priceRoots", populatePriceRoots(priceFilter.getPriceType().getId()));
			mv.addObject("priceFilter", priceFilter);
			mv.setViewName("viewPrice");
			return mv;
	}
}
