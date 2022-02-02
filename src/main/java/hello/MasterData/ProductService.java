package hello.MasterData;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	
	@Transactional
    public Product saveOrUpdate(Product product) throws Exception {
		
		if ( (product.getDivision().getCode().equals("00-000025")) || (product.getDivision().getCode().equals("00-000002")) ){
				
			Optional<Product> tmp = productRepository.findById(product.getId());
			
	    		if (tmp.isPresent()) {
	    				return tmp.get();
	    		}else {
	    			tmp = productRepository.findOneByNameAndDivisionCode(product.getName(),product.getDivision().getCode());
	    			if (tmp.isPresent()) return tmp.get();
			}
	
			return productRepository.save(product);
	    } else return product;
	}
}
