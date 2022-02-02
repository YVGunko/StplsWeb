package hello.Price;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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


@Controller
public class PriceColumnWebController {
	Price localPrice;
	PriceType2Crude localPt2C;
	String referer;

	
	@Autowired
	PriceColumnRepository repository;
	@Autowired
	PriceRepository priceRepository;
	@Autowired
	PriceType2CrudeRepository pt2CRepository;
	
	
		@ModelAttribute("priceColumns")
		public List<PriceColumn> populatePriceColumns(@Nullable Integer id) {
		    return this.repository.findByPriceId(id);
		}
		/*
	    @GetMapping("/getPriceColumn")
	    public String showGetAllPriceColumn() {
	        return "webPriceColumn";
	    }
	    @GetMapping("/singPriceColumn")
	    public String signUpPriceColumn(PriceColumn e) {
	    		e.setDateOfLastChange(new Date());
	        return "addPriceColumn";
	    }
	    @PostMapping("/addPriceColumn")
	    public String addPriceColumn(@Valid PriceColumn e, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            return "addPriceColumn";
	        }
	        e.setDateOfLastChange(new Date()); 
	        repository.save(e);
	        model.addAttribute("priceColumns", populatePriceColumns(null));
	        return "redirect:/getPriceColumn";
	    }*/
	    @GetMapping("/editPriceColumn/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model, 
	    		HttpServletRequest request) throws Exception {

	    		referer = ((request.getHeader("Referer")==null)?"/viewPrice2" : request.getHeader("Referer"));
	    		//referer = "/editPrice2/"+id;
	    		PriceColumn e = repository.findOneById(id);
	    		if (e != null) {
	    			e.setDateOfLastChange(new Date());
	    			localPrice = priceRepository.findOneById(e.price.id);
	    			localPt2C = pt2CRepository.findOneById(e.priceType2Crude.id);
	    			model.addAttribute("priceColumn", e);
	    			return "updPriceColumn";}
	    		else throw new Exception("Not found crude with id= " + id );
	    }

	    @PostMapping("/updPriceColumn/{id}")
	    public String updatePriceColumn(@PathVariable("id") Integer id, @Valid PriceColumn e, 
	      BindingResult result, Model model) {

	        if (result.hasErrors()) {
	        		e.setDateOfLastChange(new Date());
	        		e.setPrice(localPrice);
	        		e.setPriceType2Crude(localPt2C);
	            e.setId(id);
	        }

	        repository.save(e);
	        model.addAttribute("priceColumns", populatePriceColumns(id));
	        return "redirect:"+referer;
	        //return "updPrice2";

	    }
}
