package hello.MasterData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.Division.DivisionRepository;


@RestController
public class ColorController {
	@Autowired ColorRepository repository;
	@Autowired ColorService service;
	@Autowired DivisionRepository dRep;
	
	@GetMapping("/colors") 
	public List<Color> get() throws Exception{

		return repository.findAll();
	}
	
    @PostMapping(path="colors")
    public @ResponseBody Color saveOrUpdate(@RequestBody Color color) throws Exception {
    		return service.saveOrUpdate(color);
    }
    
	@CrossOrigin
	@GetMapping("/login/colors/getReqs") 
	public List<ColorReq> getReqs(@RequestParam(value="division_code", required=true) String division_code,
			@RequestParam(value="occurrence", required=false) String occurrence) throws Exception{	
		System.out.println("/login/colors/getReqs division_code: " + division_code+", occurrence: "+occurrence);
		List<Color> tmp = new ArrayList<>();
		if (occurrence == null) 
			tmp = repository.findByDivisionCodeOrderByName(division_code);
		else 
			tmp = repository.findByDivisionCodeAndOccurrenceOrderByName(division_code, occurrence);
		List<ColorReq> responce = new ArrayList<>();
		if (!tmp.isEmpty()) {
			for (Color b : tmp) {
					responce.add (new ColorReq (b.id, b.name, b.division.getCode()));
				}
		}
		if (responce != null) System.out.println("login/colors/getReqs responce: " + responce.get(0).name);
		return responce;
	}

	
	@CrossOrigin
	@GetMapping("/login/colors/getReq") 
	public ColorReq getReq(@RequestParam(value="id", required=true) String id) throws Exception{
	
		Color tmp = repository.findOneById(id);
		ColorReq responce = new ColorReq();
		if (tmp != null) {
				responce = new ColorReq (tmp.id, tmp.name, tmp.division.getCode());
				//TODO for debug purpose. remove
				System.out.println("/login/colors/getReq responce: " + tmp.name);
			}
		return responce;
	}
	
}
