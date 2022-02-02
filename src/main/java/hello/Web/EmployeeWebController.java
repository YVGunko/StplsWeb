package hello.Web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hello.Department.Department;
import hello.Department.DepartmentRepository;
import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;

@Controller
public class EmployeeWebController {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired	
	OperationRepository operationRepository;
	@Autowired	
	DivisionRepository divisionRepository;
	@Autowired	
	DepartmentRepository departmentRepository;
	
	@ModelAttribute("employees")
	public List<Employee> populateEmployees() {
	    return employeeRepository.findByIdGreaterThanOrderByName((long)0);
	}
	@ModelAttribute("divisions")
	public List<Division> populateDivisions() {
	    return divisionRepository.findAll();
	}
	@ModelAttribute("departments")
	public List<Department> populateDepartments() {
	    return departmentRepository.findAll();
	}
	@ModelAttribute("operations")
	public List<Operation> populateOperations() {
	    return operationRepository.findAll();
	}
	
    @GetMapping("/admin/getEmployee")
    public String getEmployee() {
        return "webEmployee";
    }
    
    @GetMapping("/admin/singEmployee")
    public String signUpEmployee(Employee employee) {
    		employee.setCode("007");
        return "addEmployee";
    }
    @PostMapping("/addEmployee")
    public String addUser(@Valid Employee e, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addEmployee";
        }
         
        employeeRepository.save(e);
        model.addAttribute("employees", populateEmployees());
        return "redirect:/admin/getEmployee";
    }
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") long id, Model model) {
    		Employee e = employeeRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(e);
        model.addAttribute("employees", populateEmployees());
        return "redirect:/admin/getEmployee";
    }
    @GetMapping("/editEmployee/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    		Employee e = employeeRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
         
        model.addAttribute("employee", e);
        return "updateEmployee";
    }
    @PostMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") long id, @Valid Employee e, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            e.setId(id);
            return "updateEmployee";
        }
             
        employeeRepository.save(e);
        model.addAttribute("employees", populateEmployees());
        return "redirect:/admin/getEmployee";
    }
}
