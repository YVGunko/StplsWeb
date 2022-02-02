package hello.LogGournal;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Device.Device;
import hello.Device.DeviceService;
import hello.User.User;
import hello.User.UserService;

@Service
public class LogGournalService {
	@Autowired
	private LogGournalRepository logGournalRepository;
	@Autowired
	DeviceService deviceService;	
	@Autowired
	UserService uService;	
	
	//@Transactional
	public void addLog(String name, Long userId, int size, Date date, String message) {
		try {
			Device device = deviceService.saveAndGet(name);
			User user = uService.findUserById(userId);
			LogGournal lg = new LogGournal((long)0, user, device, new Date(), size, message + date);
			if (lg != null) logGournalRepository.save(lg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
