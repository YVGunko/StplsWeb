package hello.Price;

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

@Controller
public class PriceTypeWebController {
	@Autowired
	PriceTypeRepository repository;
	@Autowired
	PriceTypeService service;
	//TODO HTML addPriceType, updPriceType
		@ModelAttribute("pricetypes")
		public List<PriceTypeWeb> populatePT() {
		    return this.service.getAllPriceTypeWeb();
		}
		
	    @GetMapping("/getPriceType")
	    public String showGetAllPriceType() {
	        return "webPriceType";
	    }
	    @GetMapping("/singPriceType")
	    public String signUpPriceType(PriceType e) {
	        return "addPriceType";
	    }
	    @PostMapping("/addPriceType")
	    public String addPriceType(@Valid PriceType e, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            return "addPriceType";
	        }
	        repository.save(e);
	        model.addAttribute("pricetypes", populatePT());
	        return "redirect:/getPriceType";
	    }
	    @GetMapping("/editPriceType/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
	    		PriceType e = repository.findOneById(id).orElse(null);
	    		if (e != null) {
	    			model.addAttribute("pricetypes", e);
	        return "updPriceType";}
	    		else throw new Exception("Not found PriceType with id= " + id );
	    }

	    @PostMapping("/updPriceType/{id}")
	    public String updatePT(@PathVariable("id") Integer id, @Valid PriceType e, 
	      BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            e.setId(id);
	            return "updPriceType";
	        }
	        repository.save(e);
	        model.addAttribute("pricetypes", populatePT());
	        return "redirect:/getPriceType";
	    }
}
