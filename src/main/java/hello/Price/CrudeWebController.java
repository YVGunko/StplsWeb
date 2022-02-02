package hello.Price;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
public class CrudeWebController {
	String referer;
	
	@Autowired
	CrudeRepository repository;
	
		@ModelAttribute("crudes")
		public List<Crude> populateCrudes() {
		    return this.repository.findByIdGreaterThanOrderById(0);
		}
		
	    @GetMapping("/getCrude")
	    public String showGetAllCrude() {
	        return "webCrude";
	    }
	    @GetMapping("/singCrude")
	    public String signUpCrude(Crude e, 
	    		HttpServletRequest request) throws Exception {

	    		referer = ((request.getHeader("Referer")==null)?"/getCrude" : request.getHeader("Referer"));
	    		e.setDateOfLastChange(new Date());
	        return "addCrude";
	    }
	    @PostMapping("/addCrude")
	    public String addCrude(@Valid Crude e, BindingResult result, Model model) {
	    	//TODO При добавлении записи нужно добавлять строки в ПрайсКолумн ???
	        if (result.hasErrors()) {
	            return "addCrude";
	        }
	        e.setDateOfLastChange(new Date()); 
	        repository.save(e);
	        model.addAttribute("crudes", populateCrudes());
	        return "redirect:"+referer;
	    }
	    @GetMapping("/editCrude/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model, 
	    		HttpServletRequest request) throws Exception {

	    		referer = ((request.getHeader("Referer")==null)?"/getCrude" : request.getHeader("Referer"));
	    		Crude e = repository.findOneById(id);
	    		if (e != null) {
	    			model.addAttribute("crude", e);
	        return "updCrude";}
	    		else throw new Exception("Not found crude with id= " + id );
	    }

	    @PostMapping("/updCrude/{id}")
	    public String updateCrude(@PathVariable("id") Integer id, @Valid Crude e, 
	      BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            e.setId(id);
	            return "updCrude";
	        }
	        e.setDateOfLastChange(new Date());
	        repository.save(e);
	        model.addAttribute("crudes", populateCrudes());
	        return "redirect:"+referer;
	    }
}
