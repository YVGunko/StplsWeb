package hello.OrderByOutDoc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import hello.OrderByOutDoc.OrderByOutDocService;
import hello.OrderByOutDoc.OrderByOutDoc;

@Controller

public class OrderByOutDocController {
	@Autowired
	private OrderByOutDocService orderByOutDocService;
	
	@GetMapping("/viewOrderByOutDoc/{id}")
	public String showOutDoc(@PathVariable("id") String id, Model model) {
		
		List<OrderByOutDoc> result = new ArrayList<>();
		
		result = orderByOutDocService.findByOutDocId(id);
	     
	    model.addAttribute("viewOrderByOutDoc", result);
	    model.addAttribute("numberOutDoc", orderByOutDocService.findNumberOutDoc(id));
	    model.addAttribute("departmentOutDoc", orderByOutDocService.getDepartmentOutDoc(id));
	    model.addAttribute("dateOutDoc", orderByOutDocService.getDateOutDoc(id));
	    model.addAttribute("lastBoxQuantitySum", result.get(0).getLastBoxQuantitySum());
	    model.addAttribute("lastPairQuantitySum", result.get(0).getLastPairQuantitySum());
			
	    return "viewOrderByOutDoc";
	}
}
