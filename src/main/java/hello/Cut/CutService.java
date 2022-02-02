package hello.Cut;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import hello.Cut.Cut;
import hello.Cut.CutRepository;
@Service
public class CutService {
	@Autowired
	private CutRepository cRepository;
	@Transactional
    public Cut saveOrUpdate(@RequestBody Cut e) throws Exception {
    		if (!cRepository.findByid(e.getId()).isEmpty()) {
    			Cut oldE = cRepository.findByid(e.getId()).get(0);
    			if (cRepository.findByid(e.getId()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 Cut with Id : " + e.getId());
    			else
    				e.setId(oldE.getId());
    				return cRepository.save(e);
    		}
    		else {
    			return cRepository.save(e);
    		}
    }
}
