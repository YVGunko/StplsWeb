package hello.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hello.TableRelation.PriceUser;
import hello.User.User;
import hello.User.UserRepository;

@Controller
public class PriceWebController {
	private String referer;
	
	@Autowired
	PriceColumnRepository priceColumnRepository;
	@Autowired
	PriceColumnService priceColumnService;
	@Autowired
	PriceRepository repository;
	@Autowired
	PriceTypeService ptService;
	@Autowired
	PriceService priceService;
	@Autowired
	PriceRootRepository priceRootRepository;
	@Autowired
	PriceRootService priceRootService;
	@Autowired
	UserRepository userRepository;
	
	//								<label for="price.priceRoot.desc" class="col-form-label" th:field="*{priceRoot.id}" th:utext="${price.priceRoot.desc}"></label>
	@ModelAttribute("priceTypes")
	public List<PriceTypeWeb> populatePT() {
	    return this.ptService.getAllPriceTypeWeb();
	}

	@ModelAttribute("prices")
	public List<Price> populatePrices(@Nullable Integer priceTypeId) {
		if (priceTypeId == null) priceTypeId = 0;
	    return this.repository.findByPriceTypeIdOrderByName(priceTypeId);
	}
	
	@ModelAttribute("priceColumns")
	public List<PriceColumn> populateColumns(@Nullable Integer priceId) {
		List<PriceColumn> orElse = new ArrayList<PriceColumn>();
		List<PriceColumn> result = new ArrayList<>();
		if (priceId == null) priceId = 0;
		result = this.priceColumnRepository.findByPriceId(priceId);
		if (result != null)
			return result;
		else return orElse;
	}

	
	@GetMapping("/signPrice")
	public String signUpPrice(Model model, Price e, 
    		HttpServletRequest request) throws Exception {

    		referer = ((request.getHeader("Referer")==null)?"/viewPrice?sample=".concat(ViewPriceController.staticPriceFilter.getSample().toString()) : request.getHeader("Referer"));
		if ((ViewPriceController.staticPriceFilter != null)) {
			e.setPriceType(ViewPriceController.staticPriceFilter.getPriceType());
			e.setPriceRoot(priceRootRepository
					.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(e.getPriceType().getId(), ViewPriceController.staticPriceFilter.getSample())
					.orElseThrow(() -> new NoSuchElementException("signPrice. PriceRoot not found exception.")));
			e.setName(ViewPriceController.staticPriceFilter.getName());
			e.setCosts(ptService.repository.getOne(ViewPriceController.staticPriceFilter.getPriceType().getId()).getDef_costs());
			e.setPaint(ptService.repository.getOne(ViewPriceController.staticPriceFilter.getPriceType().getId()).getDef_paint());
			e.setRant((double)0);
			e.setShpalt((double)0);
			e.setNumber_per_box((double)1);
			
			List<PriceRoot> pr =  new ArrayList<>();
			pr.add(e.getPriceRoot());
			model.addAttribute("priceRoots", pr);
			model.addAttribute("priceRoot", e.getPriceRoot());
		}
	    return "addPrice";
	}
	@PostMapping("/addPrice")
	public String addPrice(@Valid Price e, BindingResult result, Model model) throws Exception {
	    if (result.hasErrors()) {
	        return "addPrice";
	    }
	    e.setDateOfLastChange(new Date());
		e.setCosts(ptService.repository.getOne(e.getPriceType().getId()).getDef_costs());
		e.setPaint(ptService.repository.getOne(e.getPriceType().getId()).getDef_paint());
	    	e.setRant(e.bRant ? ptService.repository.getOne(e.getPriceType().getId()).getDef_rant() : (double)0);
		e.setShpalt(e.bLiner ? ptService.repository.getOne(e.getPriceType().getId()).getDef_shpalt() : (double)0);
		e.setPriceRoot(priceRootService.repository
				.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(e.getPriceType().getId(), ViewPriceController.staticPriceFilter.getSample())
				.orElseThrow(() -> new NoSuchElementException("addPrice. PriceRoot not found exception.")));

	    if (priceService.save(e).getId() == 0) return "addPrice";
	    priceService.sendMail(e, "Добавлено наименование");
	    model.addAttribute("prices", populatePrices(e.getPriceType().getId()));
	    return "redirect:"+referer;
	}
    @GetMapping("/editPrice/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, 
    		HttpServletRequest request) throws Exception {

    		referer = ((request.getHeader("Referer")==null)?"/viewPrice?sample=".concat(ViewPriceController.staticPriceFilter.getSample().toString()) : request.getHeader("Referer"));
    		Price e = repository.findOneById(id);
    		if (e != null) {
    			model.addAttribute("price", e);
    			//PriceColumn;
    			//model.addAttribute("priceColumn", e);
        return "updPrice";}
    		else throw new Exception("Not found price with id= " + id );
    }

    @PostMapping("/updPrice/{id}")
    public String updatePrice(@PathVariable("id") Integer id, @Valid Price e, 
      BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            e.setId(id);
            return "updPrice";
        }
        e.setDateOfLastChange(new Date());
        if (priceService.save(e).getId() == 0) return "updPrice";
        model.addAttribute("prices", populatePrices(e.getPriceType().getId()));
        //model.addAttribute("priceColumns", populateColumns(e.getId()));
        return "redirect:"+referer;
    }
    
//***** Price2
	@GetMapping("/signPrice2")
	public String signUpPrice2(Model model, Price e, 
    		HttpServletRequest request) throws Exception {
    		referer = "/viewPrice2?sample=".concat(ViewPrice2Controller.staticPriceFilter.getSample().toString());
		if ((ViewPrice2Controller.staticPriceFilter != null)) {
			e.setPriceType(ViewPrice2Controller.staticPriceFilter.getPriceType());
			e.setPriceRoot(priceRootService.repository.
					findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(e.getPriceType().getId(), 
							ViewPrice2Controller.staticPriceFilter.getSample())
					.orElseThrow(() -> new NoSuchElementException("signPrice2. PriceRoot not found exception.")));
			//e.setPriceRoot(ViewPrice2Controller.staticPriceFilter.getPriceRoot());
			e.setName(ViewPrice2Controller.staticPriceFilter.getName());
			e.setCosts(ptService.repository.getOne(ViewPrice2Controller.staticPriceFilter.getPriceType().getId()).getDef_costs());
			e.setPaint(ptService.repository.getOne(ViewPrice2Controller.staticPriceFilter.getPriceType().getId()).getDef_paint());
			e.setRant((double)0);
			e.setShpalt((double)0);
			e.setNumber_per_box((double)1);
			List<PriceRoot> pr =  new ArrayList<>();
			pr.add(e.getPriceRoot());
			model.addAttribute("priceRoots", pr);
			model.addAttribute("priceRoot", e.getPriceRoot());
		}		
	    return "addPrice2";
	}
	@PostMapping("/addPrice2")
	public String addPrice2(@Valid Price e, BindingResult result, Model model) throws Exception {
	    if (result.hasErrors()) {
	        return "addPrice2";
	    }

	    e.setDateOfLastChange(new Date());
	    try {
			e.setCosts(ptService.repository.getOne(e.getPriceType().getId()).getDef_costs());
			e.setPaint(ptService.repository.getOne(e.getPriceType().getId()).getDef_paint());
	    	e.setRant(e.bRant ? ptService.repository.getOne(ViewPrice2Controller.staticPriceFilter.getPriceType().getId()).getDef_rant() : (double)0);
			e.setShpalt(e.bLiner ? ptService.repository.getOne(ViewPrice2Controller.staticPriceFilter.getPriceType().getId()).getDef_shpalt() : (double)0);
			e.setPriceRoot(priceRootService.repository
					.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(e.getPriceType().getId(), ViewPrice2Controller.staticPriceFilter.getSample())
					.orElseThrow(() -> new NoSuchElementException("addPrice2. PriceRoot not found exception.")));

    	    if (priceService.save(e).getId() == 0) return "addPrice2";	    	    
    	    model.addAttribute("priceColumns", priceColumnService.calcAndSave(e.getId()));
    	    model.addAttribute("prices", populatePrices(e.getPriceType().getId()));
        } catch(Exception ex) {
            model.addAttribute("errorData", ex.getMessage());
        }
	    return "redirect:"+referer;
	}
    @GetMapping("/editPrice2/{id}")
    public String showUpdateForm2(@PathVariable("id") Integer id, Model model, 
    		HttpServletRequest request) throws Exception {
    	
    	referer = "/viewPrice2" ;
    	ServletRequestAttributes attributes = 
		        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		if (session.getAttribute("referer") != null) 
			referer = (String) session.getAttribute("referer");
		if (session.getAttribute("prevSample") != null) 
			referer = referer+"?sample=".concat( String.valueOf((Boolean) session.getAttribute("prevSample")));
    		
    		Price e = repository.findOneById(id);
    		if (e != null) {        		
    			model.addAttribute("price", e);
    			List<PriceColumn> pc = this.priceColumnRepository.findByPriceId(id);
    			int index = 0;
    			if (pc != null) for (PriceColumn b : pc) {
    				if (b.columnPrice == null) pc.get(index).setColumnPrice((double)0);
    				if (b.columnCosts == null) pc.get(index).setColumnCosts((double)0);
    				index++;
    			}
    			model.addAttribute("priceColumns", pc);
    			List<PriceRoot> pr =  new ArrayList<>();
    			pr.add(e.getPriceRoot());
    			model.addAttribute("priceRoots", pr);
    			model.addAttribute("priceRoot", e.getPriceRoot());
    			return "updPrice2";
        }
    		else throw new Exception("Not found price with id= " + id );
    }

    @PostMapping("/updPrice2/{id}")
    public String updatePrice2(@PathVariable("id") Integer id, @Valid Price e, 
      BindingResult result, Model model, 
      final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (result.hasErrors()) {
            e.setId(id);
            return "updPrice2";
        }
    	ServletRequestAttributes attributes = 
		        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		ViewPriceFilter priceFilter = new ViewPriceFilter();
		if (session.getAttribute("viewPriceFilter") != null) 			
			priceFilter = (ViewPriceFilter) session.getAttribute("viewPriceFilter");
		
        response.setHeader("Cache-Control", "no-cache");
        System.out.println(userRepository.findOneByName(request.getRemoteUser()));
        Price p = repository.findById(e.getId()).orElse(null);
        if (!e.equals(p))
        {
        	String strOfDifference = priceService.getListOfDifference(p, e).stream().map(Object::toString).collect(Collectors.joining(","));
        	if (!strOfDifference.equals("")) {
	    		e.setDateOfLastChange(new Date());
	            if (e.getPriceRoot().getId() == null) e.setPriceRoot(priceRootService.repository
	            		.findTopByPriceTypeIdAndSampleOrderByDateOfChangeDesc(e.getPriceType().getId()
	            				, priceFilter.getSample())
	            		.orElseThrow(() -> new NoSuchElementException("updPrice2. PriceRoot not found exception.")));        
	        	e.setPriceUsers(new PriceUser(userRepository.findOneByName(request.getRemoteUser())
	        			, "Изменено: "+strOfDifference));
		        if (priceService.save(e).getId() == 0) return "updPrice2";
        	}
        }
        
        model.addAttribute("prices", populatePrices(e.getPriceType().getId()));
                
        return "redirect:"+referer;
    }
    
    @GetMapping("/calcPrice2")
    public String calcForm2(Model model) throws Exception {

        return "updPrice2";

    }
    @PostMapping("/calcPrice2/{id}")
    public String calcForm21(@PathVariable("id") Integer id, @Valid Price e, 
    	      BindingResult result, Model model, 
      		HttpServletRequest request) throws Exception {
      		
        if (result.hasErrors()) {
            e.setId(id);
            return "updPrice2";
        }
        //TODO calc with directPrice column of the Crude entity
		model.addAttribute("priceColumns", priceColumnService.calcAndSave(id));
		List<PriceRoot> pr =  new ArrayList<>();
		e.setPriceRoot(priceRootRepository.findOneById(e.getPriceRoot().getId()));
		pr.add(e.getPriceRoot());
		model.addAttribute("priceRoots", pr);
		model.addAttribute("priceRoot", e.getPriceRoot());
        return "updPrice2";
    }
    @GetMapping("/deletePriceRow/{id}")
    public String deletePriceRowGet(@PathVariable("id") Integer id, @Valid Price e, 
    	      BindingResult result, Model model, 
      		HttpServletRequest request) throws Exception {
    		//referer = "/viewPrice2?sample=".concat(ViewPriceController.staticPriceFilter.getSample().toString()) ;
    		return "updPrice2";

    }
    
    @PostMapping("/deletePriceRow/{id}")
    public String deletePriceRowPost(@PathVariable("id") Integer id, @Valid Price e, 
  	      BindingResult result, Model model, 
    		HttpServletRequest request) throws Exception {

        if (result.hasErrors()) {
            e.setId(id);
            return "updPrice2";
        }
        
        //e.setDateOfLastChange(new Date());
        if (!priceService.delete(e)) return "updPrice2";
        model.addAttribute("prices", populatePrices(e.getPriceType().getId()));
        return "redirect:"+referer;

    }
    
    @GetMapping("/copyPricePaint")
    public String copyPricePaint(Model model) throws Exception {

        return "updPrice2";

    }
    
    @PostMapping("/copyPricePaint/{id}")
    public String copyPricePaint(@PathVariable("id") Integer id, @Valid Price e, 
    	      BindingResult result, Model model) throws Exception {
      		
        if (result.hasErrors()) {
            e.setId(id);
            return "updPrice2";
        }
        //TODO calc with directPrice column of the Crude entity
		model.addAttribute("priceColumns", priceColumnService.getPriceColumns(e));
        priceService.copyPricePaint(e.getPriceRoot().getId(), e.getPaint());
		List<PriceRoot> pr =  new ArrayList<>();
		e.setPriceRoot(priceRootRepository.findOneById(e.getPriceRoot().getId()));
		pr.add(e.getPriceRoot());
		model.addAttribute("priceRoots", pr);
		model.addAttribute("priceRoot", e.getPriceRoot());
        return "updPrice2";
    }
    //<td> <a type="hidden" th:href="@{/editPrice/{id}(id=${p.id})}" class="btn btn-primary"><i class="fas fa-edit ml-2" title ="Редактировать строку прайса"></i></a></td>
}
