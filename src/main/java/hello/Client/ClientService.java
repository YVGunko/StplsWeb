package hello.Client;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientService {
	@Autowired
	private ClientRepository cRepository;
	@Transactional
    public Client saveOrUpdate(@RequestBody Client e) throws Exception {
    		if (!cRepository.findByid(e.getId()).isEmpty()) {
    			Client oldE = cRepository.findByid(e.getId()).get(0);
    			if (cRepository.findByid(e.getId()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 Client with Id : " + e.getId());
    			else {
    				e.setId(oldE.getId());
    				if (e.getEmail() == null) e.setEmail(oldE.getEmail());
    				if (e.getPhone() == null) e.setPhone(oldE.getPhone());
    				if (e.getId1c() == null) e.setId1c(oldE.getId1c());
    			}
    			return cRepository.save(e);
    		}
    		else {
    			return cRepository.save(e);
    		}
    }
}
