package hello.Glue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Glue.Glue;
import hello.Glue.GlueRepository;
import hello.Glue.GlueService;

@RestController
public class GlueController {
	@Autowired
	private GlueRepository cRepository;
	@Autowired
	private GlueService cService;
	
	@GetMapping("/glueId") 
	public List<Glue> getGlueById(@RequestParam(value="id", required=false) String id) throws RuntimeException{
		if (id==null)
			return cRepository.findAllByOrderByIdAsc();
		else
			return cRepository.findByid(id);
	}
	
	@GetMapping("/glueName") 
	public List<Glue> getGlueByName(@RequestParam(value="name", required=false) String name) throws RuntimeException{
		if (name==null)
			return cRepository.findAllByOrderByNameAsc();
		else
			return cRepository.findByNameOrderByName(name);
	}
	
	@PostMapping("/newGlue") 
	public Glue newGlue(@RequestBody Glue glue) throws Exception{
		if (glue==null)
			return null;
		else
			return cService.saveOrUpdate(glue);
	}
}
