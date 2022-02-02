package hello.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hello.Employee.Employee;

@Service
public class UserService  implements UserDetailsService  {
	@Autowired
	UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
	public long getTotalUsers() {
        return userRepository.count();
    }
    public Page<User> findPaginated(final int pageNumber, final int pageSize,
            final String sortField, final String sortDirection) {

		final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
		Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		return userRepository.findAll(pageable);
	}
	public List<User> findAll() {
		Iterable<User> iterable = this.userRepository.findAll();
		List<User> array = StreamSupport
			    .stream(iterable.spliterator(), false)
			    .collect(Collectors.toList());
        return array;
    }


	public String findNameById(Long id) {
		String name = null;
		if (id != null) name = userRepository.findById(id).get().getName();
	
		if (!(name).isEmpty())
			return name;
		else
			return "007";
		}

	public User findUserById(Long id) {
		User user = new User();
		
		if (id != null) user = userRepository.findById(id).get();
	
		if (user.getId() != null)
			return user;
		else
			//(Long id, String name, String pswd, Boolean superUser, Employee employee, Date dt)
			return new User((long)0, "", "", false, null, new Date(), false);
		}

	public User saveUser(@Valid User user, Integer[] roles) {	
		if (user.employee == null) user.setEmployee(new Employee());	
		
		List<Integer> iterableRoles = Arrays.asList(roles);
		user.setRoles(roleRepository.findAllById(iterableRoles).stream().collect(Collectors.toSet()));
		/*if (user.getRoles() == null) {
			if (user.getExternal())
				user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
			else
				user.setRoles(Collections.singleton(new Role(5, "ROLE_SCANER_USER")));
		}*/

		user.setPassword(bCryptPasswordEncoder.encode(user.getPswd()));
	    
		userRepository.save(user);
	    return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByName(username);
	
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found");
	    }
	
	    return user;
	}
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		userRepository.delete(user);
	}
}