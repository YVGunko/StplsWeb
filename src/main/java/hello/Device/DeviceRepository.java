package hello.Device;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device,String> {

	Device findByName(String name);
	Device findById(Long Id);
	
	@Query(value = "SELECT coalesce(max(id), 0) FROM Device") 
    public Long getMaxId();

}
