package hello.Paint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Paint.Paint;
import hello.Paint.PaintRepository;
import hello.Paint.PaintService;
@RestController
public class PaintController {
	@Autowired
	private PaintRepository cRepository;
	@Autowired
	private PaintService cService;
	
	@GetMapping("/paintId") 
	public List<Paint> getPaintById(@RequestParam(value="id", required=false) String id) throws RuntimeException{
		if (id==null)
			return cRepository.findAllByOrderByIdAsc();
		else
			return cRepository.findByid(id);
	}
	
	@GetMapping("/paintName") 
	public List<Paint> getPaintByName(@RequestParam(value="name", required=false) String name) throws RuntimeException{
		if (name==null)
			return cRepository.findAllByOrderByNameAsc();
		else
			return cRepository.findByNameOrderByName(name);
	}
	
	@PostMapping("/newPaint") 
	public Paint newPaint(@RequestBody Paint paint) throws Exception{
		if (paint==null)
			return null;
		else
			return cService.saveOrUpdate(paint);
	}
}
