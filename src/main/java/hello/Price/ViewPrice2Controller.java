package hello.Price;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
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

import hello.Price.PriceRoot;
import hello.Price.PriceRootRepository;

@Controller
@RequestMapping(path = "/viewPrice2")

public class ViewPrice2Controller {

	public static ViewPriceFilter staticPriceFilter;
	@Autowired PriceRepository repositoryPrice;
	@Autowired PriceTypeRepository repositoryPT;
	@Autowired PriceRootRepository repositoryPR;
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
		
		if (prevPriceTypeId == null) {//предыдущего значения нет, значит первый раз загружается страница.
			pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc
					(priceFilter.getPriceType().getId(), priceFilter.getSample())
					.orElseThrow(() -> new NoSuchElementException("PriceRoot not found."));
			priceFilter.setPriceRoot(pr);
		}else {
			if (prevPriceTypeId == priceFilter.getPriceType().getId()) {			//тип прайса не менялся
				if (prevSample == priceFilter.getSample()) {						//образец не менялся
					if (servicePR.checkForNull(priceFilter.getPriceRoot())) priceFilter.setPriceRoot(new PriceRoot()); 
					else priceFilter.setPriceRoot(repositoryPR.findOneById(priceFilter.getPriceRoot().getId()));
				}else { 			//Образец изменился, получите максимальную дату прайса
					if (servicePR.checkForNull(priceFilter.getPriceRoot())) priceFilter.setPriceRoot(new PriceRoot()); 
					else {							//тип прайса изменялся
						pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc
								(priceFilter.getPriceType().getId(), priceFilter.getSample())
								.orElseThrow(() -> new NoSuchElementException("PriceRoot not found."));
						priceFilter.setPriceRoot(pr);
					}	
				}
			}else {							//тип прайса изменялся
				pr = repositoryPR.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc
						(priceFilter.getPriceType().getId(), priceFilter.getSample())
						.orElseThrow(() -> new NoSuchElementException("PriceRoot not found."));
				priceFilter.setPriceRoot(pr);
			}
		}
		
		if (!priceFilter.getName().equals("")) 
			price = repositoryPrice.findByPriceTypeIdAndPriceRootIdAndNameStartingWithOrderByName (priceFilter.getPriceType().getId(),
					priceFilter.getPriceRoot().getId(),
					priceFilter.getName())
			.orElse(null);
		else 
			price = repositoryPrice.findByPriceTypeIdAndPriceRootIdOrderByName (priceFilter.getPriceType().getId(),
					priceFilter.getPriceRoot().getId())
			.orElse(null);

		if (price != null)
			for (Price b : price) 
				priceWeb.add(new PriceWeb(b.getId(), b.getName(), b.getCosts(), b.getPaint(), b.getRant(), 
						b.getShpalt(), b.getNumber_per_box(), b.getWeight(), 
						pcService.getPriceColumns(b), b.getbRant(), b.getbLiner(), b.getNote(), 
						priceFilter.getPriceRoot().getId(), priceFilter.getPriceRoot().getSample()));
		
		session.setAttribute("priceTypeId", priceFilter.getPriceType().getId());
		session.setAttribute("prevSample", priceFilter.getPriceRoot().getSample());
		session.setAttribute("referer", "/viewPrice2");
		session.setAttribute("viewPriceFilter", priceFilter);
		
		mv.addObject("sample", priceFilter.getSample());
		mv.addObject("price", priceWeb);
		mv.addObject("priceRoots", populatePriceRoots(priceFilter.getPriceType().getId()));
		mv.addObject("priceFilter", priceFilter);
		mv.setViewName("viewPrice2");
		return mv;
	}
}
