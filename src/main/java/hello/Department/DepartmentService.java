package hello.Department;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Transactional
    public Department saveOrUpdate(@RequestBody Department e) throws Exception {
    		if (!departmentRepository.findBycode(e.getCode()).isEmpty()) {
    			Department oldE = departmentRepository.findBycode(e.getCode()).get(0);
    			if (departmentRepository.findBycode(e.getCode()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 Department with ID : " + e.getCode());
    			else
    				e.setId(oldE.getId());
    				return departmentRepository.save(e);
    		}
    		else {
    			return departmentRepository.save(e);
    		}
    }
}
