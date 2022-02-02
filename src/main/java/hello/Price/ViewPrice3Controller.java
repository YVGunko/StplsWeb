package hello.Price;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	    return repositoryPT.findByIdGreaterThanOrderByName(0);
	}
	@ModelAttribute("priceRoots")
	public List<PriceRoot> populatePriceRoots(Integer pt) {
	    return servicePR.findActualListPriceRootByPriceTypeId(pt, priceFilter.getSample());
	}
	@GetMapping
	public ModelAndView get(@RequestParam(value = "sample", required = false) Boolean sample) throws ParseException {
		if (sample != null) priceFilter.setSample(sample); else priceFilter.setSample(false);
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
		priceFilter.setPriceType(servicePT.checkForNullAndReplaceWithTop(priceFilter.getPriceType()));
		
		if (prevPriceTypeId == null) {//предыдущего значения нет, значит первый раз загружается страница.
			pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(priceFilter.getPriceType().getId(), priceFilter.getSample());
			if (pr == null) priceFilter.setPriceRoot(new PriceRoot());
			else 	priceFilter.setPriceRoot(pr);
		}else {
			if (prevPriceTypeId == priceFilter.getPriceType().getId()) {			//тип прайса не менялся
				if (prevSample == priceFilter.getSample()) {						//образец не менялся
					if (servicePR.checkForNull(priceFilter.getPriceRoot())) priceFilter.setPriceRoot(new PriceRoot()); 
					else priceFilter.setPriceRoot(repositoryPR.findOneById(priceFilter.getPriceRoot().getId()));
				}else { 			//Образец изменился, получите максимальную дату прайса
					if (servicePR.checkForNull(priceFilter.getPriceRoot())) priceFilter.setPriceRoot(new PriceRoot()); 
					else priceFilter.setPriceRoot(repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(priceFilter.getPriceType().getId(), priceFilter.getSample()));	
				}
			}else {							//тип прайса изменялся
				pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(priceFilter.getPriceType().getId(), priceFilter.getSample());
				if (pr == null) priceFilter.setPriceRoot(new PriceRoot());
				else 	priceFilter.setPriceRoot(pr);
			}
		}
		
		if (!priceFilter.getName().equals("")) 
			price = repositoryPrice.findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName (priceFilter.getPriceType().getId(),
					priceFilter.getPriceRoot().getId(),
					priceFilter.getName());
		else 
			price = repositoryPrice.findByPriceTypeIdAndPriceRootIdOrderByName (priceFilter.getPriceType().getId(),
					priceFilter.getPriceRoot().getId());
		
		if (price!=null) 
			for (Price b : price) 
				//PriceWeb(Integer id, String name, String priceType, Double cost, Double paint, Double rant,
				//Double shpalt, Double number_per_box, Double weight)
				priceWeb.add(new PriceWeb(b.getId(), b.getName(),  
						pcService.getPriceColumns(b), pcService.getHeader(b), b.getRant(), 
						b.getShpalt()));
		
		session.setAttribute("priceTypeId", priceFilter.getPriceType().getId());
		session.setAttribute("prevSample", priceFilter.getPriceRoot().getSample());
		mv.addObject("sample", priceFilter.getSample());
		mv.addObject("price", priceWeb);
		mv.addObject("priceRoots", populatePriceRoots(priceFilter.getPriceType().getId()));
		mv.addObject("priceFilter", priceFilter);
		mv.setViewName("viewPrice3");
		
		return mv;
	}
}
