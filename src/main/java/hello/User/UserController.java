package hello.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.User.User;
import hello.User.UserRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	
	@GetMapping("/user") 
	public List<UserReq> getUser(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		List<UserReq> result = new ArrayList<>();
		
		if (date==null) {
		for (User b : userRepository.findByExternalFalse()) {
			UserReq dReq = new UserReq(b.getId(), b.getName(), b.getPswd(), b.getSuperUser(), b.getEmployee().getId(), b.getDt());
			result.add(dReq);
		}}
	else
	{
		for (User b : userRepository.findByDtGreaterThanAndExternalFalse(date)) {
			UserReq dReq = new UserReq(b.getId(), b.getName(), b.getPswd(), b.getSuperUser(), b.getEmployee().getId(), b.getDt());
			result.add(dReq);
		}}
		return result;
	}
	
	@GetMapping("/usersList") 
	public List<User> getUsersList() throws RuntimeException{
		return userRepository.findByIdGreaterThanOrderByName((long)0);
	}
	
	@GetMapping("/userByName") 
	public List <User> getUserByName(@RequestParam(value="name", required=false) String name) throws RuntimeException{
		if (name==null)
			return userRepository.findByIdGreaterThanOrderByName((long)0);
		else
			return userRepository.findByIdGreaterThanAndNameStartingWithOrderByName((long)0,name);
	}
	
	
	@GetMapping("/userById") 
	public List<User> getUserById(@RequestParam(value="id", required=true) Long id) throws RuntimeException{
		List<User> result = new ArrayList<>();
		userRepository.findById(id).ifPresent(result::add);
		return result;
	}
	@GetMapping("/login/getAuth") 
	@CrossOrigin
	public User getAuth(@RequestParam(value="name", required=true) String name, @RequestParam(value="pswd", required=true) String pswd) throws RuntimeException{
		User responce = userRepository.findOneByNameAndPswdAndExternalTrue(name, pswd);
		return responce;
	}
    @PostMapping(path="user")
    public @ResponseBody User saveOrUpdate(@RequestBody User e) throws Exception {
    		return userRepository.save(e);
    }
    @DeleteMapping(path="user")
    public void delete(@RequestBody User e) throws Exception {
    		userRepository.delete(e);
    }
}