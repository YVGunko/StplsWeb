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

import hello.Department.Department;
import hello.Department.DepartmentRepository;
import hello.Department.DepartmentService;

@RestController
public class DepartmentController {
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	DepartmentService service;
	
	@GetMapping("/department") 
	public List<DepartmentReq> getDeps(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		List<DepartmentReq> result = new ArrayList<>();
		
		if (date==null) {
		for (Department b : departmentRepository.findAll()) {
			DepartmentReq dReq = new DepartmentReq(b.getId(), b.getCode(), b.getName(), b.getDt(), b.division.getCode(), b.operation.getId());
			result.add(dReq);
		}}
	else
	{
		for (Department b : departmentRepository.findByDtGreaterThan(date)) {
			DepartmentReq dReq = new DepartmentReq(b.getId(), b.getCode(), b.getName(), b.getDt(), b.division.getCode(), b.operation.getId());
			result.add(dReq);
		}}
		return result;
	}
	
    @PostMapping(path="department")
    public @ResponseBody Department saveOrUpdate(@RequestBody Department d) throws Exception {
    		return service.saveOrUpdate(d);
    }
}
