package hello.Price;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import hello.TableRelation.PriceUser;


@Controller
public class viewPriceSaveHistoryController {
	@Autowired
	PriceRepository priceRepository;
	
	@ModelAttribute("priceColumns")
	public List<PriceUser> populatePriceUsers(@Nullable Integer id) {
		
		List<PriceUser> pu = new ArrayList<>();
		if (id == null) return pu;
		
		if (priceRepository.findById(id).isPresent()) {
			pu = priceRepository.findById(id)
					.map(Price::getPriceUsers)
					.orElse(new HashSet<PriceUser>())
					.stream().sorted(Comparator.comparing(PriceUser::getCreatedOn).reversed())
					.collect(Collectors.toList());
		}
		return pu;
	}
	
    @GetMapping("/getPriceSaveHistory/{id}")
    public String show(@PathVariable("id") Integer priceId, Model model, 
	HttpServletRequest request) throws Exception {
    	model.addAttribute("priceUsers", populatePriceUsers(priceId));
        return "viewPriceSaveHistory";
    }

}
