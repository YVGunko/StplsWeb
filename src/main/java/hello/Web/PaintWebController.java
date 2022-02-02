package hello.Web;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import hello.Paint.Paint;
import hello.Paint.PaintRepository;


@Controller
public class PaintWebController {
	@Autowired
	PaintRepository repository;
	
		@ModelAttribute("paints")
		public List<Paint> populatePaint() {
		    return this.repository.findByIdGreaterThanOrderByCost("0");
		}
		
	    @GetMapping("/getPaint")
	    public String showGetAllPaint() {
	        return "webPaint";
	    }

}
