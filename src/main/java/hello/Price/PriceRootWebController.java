package hello.Price;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.utils;
import hello.Web.Order.OrderGroupReq;

@Controller
public class PriceRootWebController {
	@Autowired
	PriceRootRepository repository;
	@Autowired
	PriceRootService service;
	@Autowired
	PriceService priceService;
	@Autowired
	PriceTypeRepository repositoryPT;
	@Autowired
	CrudeRepository crudeRepository;

	public CrudeChangeDTO showCreateForm() {
	    List<Crude> books = new ArrayList<>();
	    crudeRepository.findAll().iterator().forEachRemaining(books::add);
	    return new CrudeChangeDTO(books);
	}
	//TODO 
		@ModelAttribute("priceRoots")
		public List<PriceRoot> populatePR() {
		    return this.repository.findAll()
		    		.stream()
		    		.sorted(Comparator.comparing(PriceRoot::getDateOfChange).reversed())
		    		.collect(Collectors.toList());		
		}
		
		@ModelAttribute("priceTypes")
		public List<PriceType> populatePT() {
		    return this.repositoryPT.findByIdGreaterThanOrderByName(0);
		}
		
		@ModelAttribute("crudeList")
		public List<Crude> populateCrudes() {
		    return this.crudeRepository.findAll()
		    		.stream()
		    		.sorted(Comparator.comparing(Crude::getColumnName)).collect(Collectors.toList());
		}
		
	    @GetMapping("/getPriceRoot")
	    public String showGetAllPriceRoot() {
	        return "webPriceRoot";
	    }
	    
	    @GetMapping("/signPriceRoot")
	    public String signUpPriceRoot(PriceRoot e, Model model) {
    		e.setDateOfChange(utils.getStartOfDay(new Date()));
    		e.setNote("");
    		e.setPlusValue(0);
    		e.setSample(false);
    		//model.addAttribute("crudeList", populateCrudes());
	        return "addPriceRoot";
	    }
	    
	    @PostMapping("/addPriceRoot")
	    public String addPriceRoot(@Valid PriceRoot e, @RequestParam(value = "pts" , required = true) int[] pts,
	    		@RequestParam(value = "copy" , required = false) Boolean copy,
	    		BindingResult result, Model model, @ModelAttribute ArrayList<Crude> crudes) throws Exception {
	        if (result.hasErrors()) {
	            return "addPriceRoot";
	        }
	        //Сохранить выбранные разделы
	        for (int i = 0; i < pts.length; i++) {
	        	//Выбрать наименования priceType и подставить их перед e.note чтобы было видно раздел
	        	String priceTypeName = repositoryPT.getOneById(pts[i])
	        			.map(PriceType::getName)
	        			.orElse("").concat(e.note);

	        	service.save(new PriceRoot(e.id,e.dateOfChange,priceTypeName,e.plusValue,e.priceType,e.getSample()), pts[i], copy);
	        }
	        //model.addAttribute("crudeList", populateCrudes());
	        model.addAttribute("priceRoots", populatePR());
	        return "redirect:/getPriceRoot";
	    }
	    
	    @GetMapping("/editPriceRoot/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
	    		PriceRoot e = repository.findOneById(id);
	    		if (e != null) {
	    			model.addAttribute("priceRoot", e);
	        return "updPriceRoot";}
	    		else throw new Exception("Not found PriceRoot with id= " + id );
	    }

	    @PostMapping("/updPriceRoot/{id}")
	    public String updatePT(@PathVariable("id") Integer id, @Valid PriceRoot e, 
	      BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            e.setId(id);
	            return "updPriceRoot";
	        }
	        //repository.save(e);
	        model.addAttribute("priceRoots", populatePR());
	        return "redirect:/getPriceRoot";
	    }
	    
	    @PostMapping("/calcPriceRoot/{id}")
	    public String calcAll(@PathVariable("id") Integer id, @Valid PriceRoot pr, 
	      BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            pr.setId(id);
	            return "updPriceRoot";
	        }
	        priceService.reCalcPrice(pr.getId());
	        model.addAttribute("priceRoots", populatePR());
	        return "updPriceRoot";
	    }
}
