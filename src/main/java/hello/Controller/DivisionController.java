package hello.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Division.DivisionRepository;
import hello.Division.Division;

@RestController
public class DivisionController {
	@Autowired
	DivisionRepository divisionRepository;
	@GetMapping("/division") 
	public List<Division> getDivision(@RequestParam(value="code", required=false) String code) throws RuntimeException{
		if (code==null)
			return divisionRepository.findAll();
		else
			return divisionRepository.findBycode(code);
	}
	
	@GetMapping("/divisionByName") 
	public List<Division> getDivisionByName(@RequestParam(value="name", required=false) String name) throws RuntimeException{
		if (name==null)
			return (List<Division>) divisionRepository.findAll();
		else
			return divisionRepository.findByNameStartingWith(name);
	}
}
