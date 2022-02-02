package hello.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository <Role, Integer>{

	public List<Role> findAll();

	//public List<Role> findAllByUsersId(long id);

}
