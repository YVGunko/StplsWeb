package hello.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.Employee.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService service;
	
	@GetMapping("/employee") 
	public List<EmployeeReq> getSotr(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		List<EmployeeReq> result = new ArrayList<>();
		
		if (date==null) {
			for (Employee b : employeeRepository.findAll()) {
				EmployeeReq dReq = new EmployeeReq(b.getId(), b.getCode(), b.getName(), b.getDt(), b.division.getCode(), b.operation.getId(), b.department.getId());
				result.add(dReq);
			}
		}
		else
		{
			for (Employee b : employeeRepository.findByDtGreaterThan(date)) {
				EmployeeReq dReq = new EmployeeReq(b.getId(), b.getCode(), b.getName(), b.getDt(), b.division.getCode(), b.operation.getId(), b.department.getId());
				result.add(dReq);
			}
		}
		return result;
	}
	
	
    @PostMapping(path="employee")
    public @ResponseBody Employee saveOrUpdate(@RequestBody Employee e) throws Exception {
    		return service.saveOrUpdate(e);
    }
}
