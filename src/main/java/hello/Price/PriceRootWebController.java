package hello.Price;

import java.util.Date;
import java.util.List;

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

@Controller
public class PriceRootWebController {
	@Autowired
	PriceRootRepository repository;
	@Autowired
	PriceRootService service;
	@Autowired
	PriceTypeRepository repositoryPT;

	//TODO 
		@ModelAttribute("priceRoots")
		public List<PriceRoot> populatePR() {
		    return this.repository.findAll();
		}
		
		@ModelAttribute("priceTypes")
		public List<PriceType> populatePT() {
		    return this.repositoryPT.findByIdGreaterThanOrderByName(0);
		}
		
	    @GetMapping("/getPriceRoot")
	    public String showGetAllPriceRoot() {
	        return "webPriceRoot";
	    }
	    
	    @GetMapping("/signPriceRoot")
	    public String signUpPriceRoot(PriceRoot e) {
	    		e.setDateOfChange(utils.getStartOfDay(new Date()));
	    		e.setNote("...");
	    		e.setPlusValue(10);
	    		e.setSample(false);
	    		//System.out.println("/signPriceRoot date: " + e.getDateOfChange());
	        return "addPriceRoot";
	    }
	    
	    @PostMapping("/addPriceRoot")
	    public String addPriceRoot(@Valid PriceRoot e, @RequestParam(value = "pts" , required = false) int[] pts, BindingResult result, Model model) throws Exception {
	        if (result.hasErrors()) {
	            return "addPriceRoot";
	        }
	        //TODO Сохранить выбранные разделы
	        for (int i = 0; i < pts.length; i++) {
	        		service.save(new PriceRoot(e.id,e.dateOfChange,e.note,e.plusValue,e.priceType,e.getSample()), pts[i]);
	        }
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
	        repository.save(e);
	        model.addAttribute("priceRoots", populatePR());
	        return "redirect:/getPriceRoot";
	    }
}
