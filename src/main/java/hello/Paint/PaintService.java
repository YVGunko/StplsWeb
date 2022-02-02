package hello.Paint;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import hello.Paint.Paint;
import hello.Paint.PaintRepository;

@Service
public class PaintService {
	@Autowired
	private PaintRepository cRepository;
	@Transactional
    public Paint saveOrUpdate(@RequestBody Paint e) throws Exception {
    		if (!cRepository.findByid(e.getId()).isEmpty()) {
    			Paint oldE = cRepository.findByid(e.getId()).get(0);
    			if (cRepository.findByid(e.getId()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 Paint with Id : " + e.getId());
    			else
    				e.setId(oldE.getId());
    				return cRepository.save(e);
    		}
    		else {
    			return cRepository.save(e);
    		}
    }
}
