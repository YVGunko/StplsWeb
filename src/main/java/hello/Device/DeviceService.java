package hello.Device;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hello.Device.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	private DeviceRepository dRepository;
	
	//@Transactional
	public Device saveAndGet(String name) throws Exception {

		Device device = new Device("Пусто");
		
		if (name != null) {

			device = dRepository.findByName(name); //try to find
			
			if (device==null) { //not found, add
				device = new Device(dRepository.getMaxId()+1, name);
				device = dRepository.save(device);
				if (device.getId() == (long)0)
					throw new Exception("Error while saving new Device with DeviceId= " + name) ;
			}
		}
		return device;
	}

	public List<Device> findAll() {
		Iterable<Device> iterable = this.dRepository.findAll();
		List<Device> array = StreamSupport
			    .stream(iterable.spliterator(), false)
			    .collect(Collectors.toList());
        return array;
    }
}
