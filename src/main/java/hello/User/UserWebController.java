package hello.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;

@Controller
public class UserWebController {

		private Map<String, String> pageAttributeMap = new HashMap<>();
	
		@Autowired
		UserRepository userRepository;
		
		@Autowired
		UserService userService;
		 
		@Autowired
		EmployeeRepository employeeRepository;
		
		@Autowired
		RoleRepository roleRepository;
		
		@Autowired
	    private UserValidator userValidator;

		
		@ModelAttribute("roles")
		public List<RoleChecked> populateRoles() {
			List<Role> rList = roleRepository.findAll();
			List<RoleChecked> rpList = new ArrayList<RoleChecked>();
			for (Role r : rList) {
					rpList.add(new RoleChecked(r, false)) ;
				}
			return rpList;
		}
		
		@SuppressWarnings("unchecked")
		@ModelAttribute("employees")
		public List<Employee> populateEmployees(Model model) {
    			if (model.containsAttribute("employees")) 
    				return (List<Employee>) model.asMap().get("employees");
    			else
    				return employeeRepository.findAll();
		}
		
		@ModelAttribute("users")
		public List<User> populateUsers() {
		    return this.userRepository.findByIdGreaterThanOrderByName((long)0);
		}
		
	    @GetMapping("/admin/getUser")
	    public String viewIndexPage() {
	        //return "webuser";
	    		return "redirect:/admin/getUser/page/1?sort-field=id&sort-dir=asc";
	    }
	    @GetMapping(value = "/admin/getUser/page/{page-number}")
	    public String findPaginated(@PathVariable(name = "page-number") final int pageNo,
	                                @RequestParam(name = "sort-field") final String sortField,
	                                @RequestParam(name = "sort-dir") final String sortDir,
	                                final Model model) {
	    		System.out.println("Getting the users in a paginated way for page-number = "+pageNo+
	    				", sort-field = "+sortField+", and "
	                + "sort-direction = "+sortDir);
	        // Hardcoding the page-size to 15.
	        final int pageSize = 15;
	        final Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
	        final List<User> listUsers = page.getContent();
	 
	        // Creating the model response.
	        // Note for simplicity purpose we are not making the use of ResponseDto here.
	        // In ideal cases the response will be encapsulated in a class.
	        // pagination parameters
	        model.addAttribute("currentPage", pageNo);
	        model.addAttribute("totalPages", page.getTotalPages());
	        model.addAttribute("totalItems", page.getTotalElements());
	        // sorting parameters
	        model.addAttribute("sortField", sortField);
	        model.addAttribute("sortDir", sortDir);
	        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	        // employees
	        model.addAttribute("users", listUsers);
	        
	        pageAttributeMap.clear();
	        pageAttributeMap.put("currentPage", Integer.toString(pageNo));
	        pageAttributeMap.put("sortField", sortField);
	        pageAttributeMap.put("sortDir", sortDir);
	        pageAttributeMap.put("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	        
	        return "webuser";
	    }
	    @GetMapping("/admin/signUser")
	    public String showSignUpForm(User user) {
	        return "add-user";
	    }
	     
	    @PostMapping("/admin/addUser")
	    public String addUser(@Valid User user, BindingResult result, Model model, @RequestParam(value = "pts" , required = false) Integer[] roles) {
	    		userValidator.validate(user, result); 
	        if (result.hasErrors()) {
	            return "add-user";
	        }
	    		
	        userService.saveUser(user, roles);
	        //model.addAttribute("users", populateUsers());
	        return "redirect:/admin/getUser/page/"+
	        		pageAttributeMap.get("currentPage")+
	        		"?sort-field="+pageAttributeMap.get("sortField")+
	        		"&sort-dir="+pageAttributeMap.get("sortDir");
	    }

	    @SuppressWarnings("unchecked")
		private User getUserFromModel(long id, Model model) {

			ArrayList<User> arrayOfUsers = (ArrayList<User>) model.asMap().get("users");
			User user = arrayOfUsers.stream().filter(u -> u.getId().equals(id)).findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

			return user;	    	
	    }
	    @GetMapping("/admin/editUser/{id}")
	    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    		User user = getUserFromModel(id, model);
    		model.addAttribute("roles", populateUserRoles(user));
	        model.addAttribute("user", user);
	        return "update-user";
	    }
	    
	    @PostMapping("/admin/updateUser/{id}")
	    public String updateUser(@PathVariable("id") long id, @Valid User user, 
	      BindingResult result, Model model, @RequestParam(value = "pts" , required = false) Integer[] roles) {
    			userValidator.validate(user, result); 
    			if (result.hasErrors()) {
	            user.setId(id);
	            return "update-user";
	        }

    			userService.saveUser(user, roles);
	        
	        //model.addAttribute("users", populateUsers());
    	        return "redirect:/admin/getUser/page/"+
        		pageAttributeMap.get("currentPage")+
        		"?sort-field="+pageAttributeMap.get("sortField")+
        		"&sort-dir="+pageAttributeMap.get("sortDir");
	    }
	         
	    @GetMapping("/admin/deleteUser/{id}")
	    public String deleteUser(@PathVariable("id") long id, Model model) {
	    		User user = userRepository.findById(id)
	          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    		userService.deleteUser(user);
	        //model.addAttribute("users", populateUsers());
	        return "redirect:/admin/getUser/page/"+
		    		pageAttributeMap.get("currentPage")+
		    		"?sort-field="+pageAttributeMap.get("sortField")+
		    		"&sort-dir="+pageAttributeMap.get("sortDir");
	    }

	    private List<RoleChecked> populateUserRoles(User user) {
			List<Role> rList = roleRepository.findAll();
			List<RoleChecked> rpList = new ArrayList<RoleChecked>();

			for (Role r : rList) {
				if (user.getRoles().contains(r))
					rpList.add(new RoleChecked(r, true)) ;
				else rpList.add(new RoleChecked(r, false)) ;
			}
			return rpList;
		}
}
