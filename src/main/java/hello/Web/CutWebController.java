package hello.Web;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import hello.Cut.Cut;
import hello.Cut.CutRepository;


@Controller
public class CutWebController {
	@Autowired
	CutRepository cutRepository;
	
		@ModelAttribute("cuts")
		public List<Cut> populateCuts() {
		    return this.cutRepository.findByIdGreaterThanOrderByCost("0");
		}
		
	    @GetMapping("/getCut")
	    public String showGetAllCut() {
	        return "webCut";
	    }

}
