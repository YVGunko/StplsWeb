package hello.Cut;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Cut.Cut;
import hello.Cut.CutRepository;
import hello.Cut.CutService;

@RestController
public class CutController {
	@Autowired
	private CutRepository cRepository;
	@Autowired
	private CutService cService;
	
	@GetMapping("/cutId") 
	public List<Cut> getCutById(@RequestParam(value="id", required=false) String id) throws RuntimeException{
		if (id==null)
			return cRepository.findAllByOrderByIdAsc();
		else
			return cRepository.findByid(id);
	}
	
	@GetMapping("/cutName") 
	public List<Cut> getCutByName(@RequestParam(value="name", required=false) String name) throws RuntimeException{
		if (name==null)
			return cRepository.findAllByOrderByNameAsc();
		else
			return cRepository.findByNameOrderByName(name);
	}
	
	@PostMapping("/newCut") 
	public Cut newCut(@RequestBody Cut cut) throws Exception{
		if (cut==null)
			return null;
		else
			return cService.saveOrUpdate(cut);
	}
}
