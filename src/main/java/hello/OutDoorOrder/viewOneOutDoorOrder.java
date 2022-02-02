package hello.OutDoorOrder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class viewOneOutDoorOrder {
	@Autowired
	private OutDoorOrderService service;
	@Autowired
	OutDoorOrderRepository repository;
	
	@GetMapping(value= {"/viewOneOutDoorOrder/{id}", "/login/viewOneOutDoorOrder/{id}"})
	public String showOutDoc(@PathVariable("id") String id, Model model) {
		
		List<OutDoorOrderRep> result = new ArrayList<>();
		
		result = service.findRows(id);
	     
	    model.addAttribute("viewOneOutDoorOrder", result);
	    model.addAttribute("numberOutDoc", id);
	    model.addAttribute("dateOutDoc", service.findDate(id));
	    model.addAttribute("clientName", repository.findOneById(id).client.name);
	    if (repository.findOneById(id).sample != null)
	    		model.addAttribute("sample", (repository.findOneById(id).sample ? "Образцы" : "Серия"));
	    else 	model.addAttribute("sample","");
	    return "viewOneOutDoorOrder";
	}
}
