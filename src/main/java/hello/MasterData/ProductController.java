package hello.MasterData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.Division.Division;
import hello.Division.DivisionRepository;

@RestController
public class ProductController {
	@Autowired ProductRepository repository;
	@Autowired ProductService service;
	@Autowired DivisionRepository dRep;
	
	@CrossOrigin
	@GetMapping("/products") 
	public List<Product> get() throws Exception{

		return repository.findAll();
	}
	
    @PostMapping(path="products")
    public @ResponseBody Product saveOrUpdate(@RequestBody Product product) throws Exception {
    		return service.saveOrUpdate(product);
    }
    
	@CrossOrigin
	@GetMapping("/login/products/getReqs") 
	public List<ProductReq> getReqs(@RequestParam(value="division_code", required=true) String division_code) throws Exception{	
		List<Product> tmp = repository.findByDivisionCodeOrderByName(division_code);
		List<ProductReq> responce = new ArrayList<>();
		if (!tmp.isEmpty()) {
			for (Product b : tmp) {
					responce.add (new ProductReq (b.id, b.name, b.division.getCode()));
				}
		}
		//TODO for debug purpose. remove
		if (responce != null) System.out.println("/products/getReqs responce: " + responce.get(0).name);
		return responce;
	}

	@CrossOrigin
	@GetMapping("/login/products/getReq") 
	public ProductReq getReq(@RequestParam(value="id", required=true) String id) throws Exception{
		//Long lId = Long.valueOf(id);
		Product tmp = repository.findOneById(id);
		ProductReq responce = new ProductReq();
		if (tmp != null) {
				responce = new ProductReq (tmp.id, tmp.name, tmp.division.getCode());
				//TODO for debug purpose. remove
				System.out.println("/products/getReq responce: " + tmp.name);
			}
		return responce;
	}
}
