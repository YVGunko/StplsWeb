package hello.OutDoc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.utils;
import hello.Controller.OutDocReq;
import hello.Device.Device;
import hello.Device.DeviceService;
import hello.LogGournal.LogGournal;
import hello.LogGournal.LogGournalRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.User.User;
import hello.User.UserRepository;

@Service
public class OutDocService {
	@Autowired
	private OutDocRepository cRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LogGournalRepository lgRepository;
	@Autowired
	DeviceService deviceService;
	
	@Transactional
    public List<OutDoc> saveOrUpdate(List<OutDocReq> outDocList, Date currentDate, String DeviceId) throws Exception {
		System.out.println("OutDoc saveOrUpdate records number: " + (outDocList.size()));
		
		//Device device = deviceService.saveAndGet(DeviceId);
		
		List<OutDoc> toBesavedBoxList = new ArrayList<>();
		for (OutDocReq odReq: outDocList) {
			Optional<OutDoc> outdoc = cRepository.findById(odReq.id);
			if (!outdoc.isPresent()) {
				Optional<Operation> operation = operationRepository.findById(odReq.operationId);
				if (!operation.isPresent())
					throw new Exception("Error while saving OutDoc with id= " + odReq.id + ", no Operation with id= " + odReq.operationId);
				Optional<User> user = userRepository.findById(odReq.user_id);
				if (!user.isPresent())
					throw new Exception("Error while saving OutDoc with id= " + odReq.id + ", no user with id= " + odReq.user_id);
				OutDoc outDoc = new OutDoc(odReq.id, operation.get(), odReq.number, odReq.comment, odReq.date, currentDate, null, 
						odReq.getDivision_code(), user.get(), new Device("Неопределено"));
					toBesavedBoxList.add(outDoc);
			}
		}

		List<OutDoc> savedBoxList = cRepository.saveAll(toBesavedBoxList);
		
		/*if (!savedBoxList.isEmpty())	{
			LogGournal lg = new LogGournal((long)0, savedBoxList.get(0).getUser(), device, currentDate, savedBoxList.size(), "OutDocService.saveOrUpdate");
			if (lg != null) lgRepository.save(lg);
		}	*/
		
		return savedBoxList;
	}
	
	public List<OutDoc> selectOutDocByDateAndDivision (Date date, String division_code ) {
		List<OutDoc> result = new ArrayList<>();
		if (date==null) {
			if( utils.empty( division_code ) ) 
				result = cRepository.findAll();
			else 
				result = cRepository.findByDivision_code(division_code);
		}
		else {	
			if( utils.empty( division_code ) ) 
				result = cRepository.findByDateGreaterThan(date);
			else 
				result = cRepository.findByDateGreaterThanAndDivision_code(date, division_code);
		}
		return result;
	}

	public Integer selectNumberById(String id) {
		Integer number;
		
		Optional<OutDoc> outdoc = cRepository.findById(id);
		number = outdoc.get().getNumber();
		
		return number;
	};
	
	public Long selectOperationIdById(String id) {
		Long number;
		
		Optional<OutDoc> outdoc = cRepository.findById(id);
		number = outdoc.get().getOperation().getId();
		
		return number;
	};


	public String selectDepartmentById(String id) {
		String departmentName;
		
		Optional<OutDoc> outdoc = cRepository.findById(id);
		departmentName = outdoc.get().getUser().getEmployee().getDepartment().getName();
		
		return departmentName;
	}

	public String selectDateById(String id) {
		String dateOutDoc;
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		Optional<OutDoc> outdoc = cRepository.findById(id);
		dateOutDoc = mdyFormat.format(outdoc.get().getDate());
		
		return dateOutDoc;
	}


}
