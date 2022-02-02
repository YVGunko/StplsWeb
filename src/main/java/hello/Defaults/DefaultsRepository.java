package hello.Defaults;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultsRepository extends JpaRepository<Defaults,String>{

}
