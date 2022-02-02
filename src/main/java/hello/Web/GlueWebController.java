package hello.Web;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import hello.Glue.Glue;
import hello.Glue.GlueRepository;


@Controller
public class GlueWebController {
	@Autowired
	GlueRepository repository;
	
		@ModelAttribute("glues")
		public List<Glue> populateGlues() {
		    return this.repository.findByIdGreaterThanOrderByCost("0");
		}
		
	    @GetMapping("/getGlue")
	    public String showGetAllGlue() {
	        return "webGlue";
	    }

}
