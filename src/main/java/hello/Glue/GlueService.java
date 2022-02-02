package hello.Glue;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import hello.Glue.Glue;
import hello.Glue.GlueRepository;

@Service
public class GlueService {
	@Autowired
	private GlueRepository cRepository;
	@Transactional
    public Glue saveOrUpdate(@RequestBody Glue e) throws Exception {
    		if (!cRepository.findByid(e.getId()).isEmpty()) {
    			Glue oldE = cRepository.findByid(e.getId()).get(0);
    			if (cRepository.findByid(e.getId()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 Glue with Id : " + e.getId());
    			else
    				e.setId(oldE.getId());
    				return cRepository.save(e);
    		}
    		else {
    			return cRepository.save(e);
    		}
    }
}
