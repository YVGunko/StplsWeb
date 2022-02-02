package hello.Employee;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Transactional
    public Employee saveOrUpdate(@RequestBody Employee e) throws Exception {
    		if (!employeeRepository.findBycode(e.getCode()).isEmpty()) {
    			Employee oldE = employeeRepository.findBycode(e.getCode()).get(0);
    			if (employeeRepository.findBycode(e.getCode()).size() > 1)
    				throw new Exception("ERROR: somehow there is more than 1 Employee with TN : " + e.getCode());
    			else
    				e.setId(oldE.getId());
    				return employeeRepository.save(e);
    		}
    		else {
    			return employeeRepository.save(e);
    		}
    }
}
