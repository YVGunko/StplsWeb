package hello.PartBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.Box.BoxRepository;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.BoxMoves.BoxMovesService;
import hello.Controller.PartBoxReq;
import hello.Department.Department;
import hello.Department.DepartmentRepository;
import hello.Division.Division;
import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.Operation.Operation;
import hello.OutDoc.OutDoc;
import hello.OutDoc.OutDocRepository;
import hello.OutDoc.OutDocService;

@Service
public class PartBoxService {
	public class OutDocIdAndPartBoxNumber {

	    public OutDocIdAndPartBoxNumber(String id, int quantity) {
			super();
			this.outDocId = id;
			this.number = quantity;
		}
		String outDocId;
	    Integer number;
	}
	
	@Autowired
	private PartBoxRepository partBoxRepository;	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;	
	@Autowired
	private BoxMovesRepository boxMovesRepository;	
	@Autowired
	private OutDocRepository outDocRepository;
	@Autowired
	private OutDocService oService;	
	@Autowired
	private BoxRepository boxRepository;	
	@Autowired
	private BoxMovesService boxMovesService;
	
	public List<PartBox> save(List<PartBoxReq> partBoxReqList, Date currentDate) throws RuntimeException{
		List<PartBox> toBeSavedPartBoxList = new ArrayList<>();
		for (PartBoxReq partBoxReq: partBoxReqList) {			
			Optional<BoxMove> boxMoves = boxMovesRepository.findById(partBoxReq.boxMovesId);
			Optional<Department> department = departmentRepository.findById(partBoxReq.departmentId);
			Optional<Employee> employee = employeeRepository.findById(partBoxReq.employeeId);
			Optional<OutDoc> outDoc = outDocRepository.findById(partBoxReq.outDocId);
			
			if ((boxMoves.isPresent()) & (department.isPresent()) & (employee.isPresent()) & (outDoc.isPresent())){
				PartBox partBox = new PartBox(partBoxReq.id, boxMoves.get(), department.get(), employee.get(),
						partBoxReq.quantity, partBoxReq.date, currentDate, outDoc.get());
				toBeSavedPartBoxList.add(partBox);
				//savedPartBoxList.add(partBoxRepository.save(partBox));
			}else {
				if (boxMoves == null) System.out.println("partBoxService.Save No partBoxReq.boxMovesId " + partBoxReq.boxMovesId);
				if (department == null) System.out.println("partBoxService.Save No partBoxReq.departmentId " + partBoxReq.departmentId);
				if (employee == null) System.out.println("partBoxService.Save No partBoxReq.employeeId " + partBoxReq.employeeId);
				if (outDoc == null) System.out.println("partBoxService.Save No partBoxReq.outDocId " + partBoxReq.outDocId);
			}
		}
		List<PartBox> savedPartBoxList = new ArrayList<>();
		long start = System.currentTimeMillis();
		savedPartBoxList.addAll(partBoxRepository.saveAll(toBeSavedPartBoxList));
		System.out.println("partBoxService.SaveAll " + (System.currentTimeMillis() - start));
		return savedPartBoxList;
	}
	
	public boolean updateSendToMasterDate (Date date, String bmId, long departmentId, int quantityBox) throws RuntimeException{
		List<PartBox> pbList = null;
		PartBox pb = null;
		
		if (departmentId != 0) {
			pb = partBoxRepository.findFirstByBoxMoveIdAndDepartmentIdAndQuantityAndSentToMasterDateOrderByDate(bmId, departmentId, quantityBox, null);
			if (pb != null) {
				pb.setSentToMasterDate(date) ;
				return true;
			} else return false;
		}
		else	 {
			pbList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNull(bmId);
			if (pbList != null) {
				for (PartBox pb9999:pbList) {
					pb9999.setSentToMasterDate(date);
				}
				return true;
			} else return false;
		}
	}
	//First of all, save partBox if is not null field Send..., then check if full box was received and save BoxMove if true.
	// For operation=9999 (means dep != 0) boxMove should be saved always.
	public void updSendToMasterDate (Date dateToSet, String bmId, long departmentId, int quantityBox) throws RuntimeException{
		if (departmentId != 0) {
			partBoxRepository.updateSentToMasterDateByBoxMoveIdAndDepartmentAndQuantity(dateToSet, bmId, departmentId, quantityBox);
			Integer q = 
					partBoxRepository.getQuantityByBoxMoveIdAndDepartmentAndQuantity(bmId, departmentId, quantityBox)
					.orElse(Arrays.asList(0))
					.stream().reduce(0,Integer::sum);
					
			if (q == quantityBox) boxMovesService.save(bmId); //BoxMove setSendToMaster... save
		}
		else {
			partBoxRepository.updateSentToMasterDateByBoxMoveId(dateToSet, bmId);
			boxMovesService.save(bmId); //BoxMove setSendToMaster... save
		}
	}
	
	public List<PartBox> selectByDateAndDivision (Date date, String division_code) throws RuntimeException{
		List<PartBox> result = new ArrayList<>();
		
		List<OutDoc> outDoc = oService.selectOutDocByDateAndDivision(date, division_code);
		
		for (OutDoc oList: outDoc) {			
			result.addAll(partBoxRepository.findByOutDocId(oList.getId()));
		}
		
		return result;
	}

	public List <PartBox> findAllWhereArchive(Date date) {
		List <PartBox> result = new ArrayList<>();
		if (date==null) {			
			for (PartBox pb : partBoxRepository.findAll()) {
				if (!boxRepository.findById(pb.getBoxMove().getBox().getId()).get().getArchive()) {
					PartBox pbReq = new PartBox(pb.getId(), pb.getBoxMove(), pb.getDepartment(), pb.getEmployee(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc());
					result.add(pbReq);
				}
			}
		}
		else {
			for (PartBox pb : partBoxRepository.findByDateGreaterThanEqual(date)) {
				if (!boxRepository.findById(pb.getBoxMove().getBox().getId()).get().getArchive()) {
					PartBox pbReq = new PartBox(pb.getId(), pb.getBoxMove(), pb.getDepartment(), pb.getEmployee(), pb.getQuantity(), pb.getDate(),pb.getReceivedFromMobileDate(),pb.getOutDoc());
					result.add(pbReq);
				}
			}
		}
		return result;
	}
	
	public List<OutDoc> selectOutDocByEmployeeId (Employee employee, Operation operation, Date DateS, Date DateE)					throws RuntimeException{
		List<OutDoc> result = new ArrayList<>();
		//Выбираем накладные по операции и диапазону дат
		List<OutDoc> outDoc = outDocRepository.findByOperationIdAndDateBetweenOrderByDate(operation.getId(), DateS, DateE);
		//Пользователь есть только в ПартБоксе
		for (OutDoc oList: outDoc) {	
			List<PartBox> partBox = partBoxRepository.findByOutDocIdAndEmployeeId(oList.getId(), employee.getId());

			if (!partBox.isEmpty()) {
				int quantity = 0;
				for (PartBox pList: partBox) {
					quantity = quantity + pList.getQuantity();
				}	
				//(String id, Operation operation, int number, Date date, String division_code)
				result.add( new OutDoc (oList.getId(), oList.getOperation(), quantity, oList.getDate(), oList.getDivision().getCode()));
			}
		}
		
		return result;
	}
	
	public List<OutDoc> selectOutDocByDivisionIdAndEmployeeId (Division division, Employee employee, Operation operation, Date DateS, Date DateE)					throws RuntimeException{
		List<OutDoc> result = new ArrayList<>();
		//Выбираем накладные по операции и диапазону дат
		List<OutDoc> outDoc = outDocRepository.findByDivisionCodeAndOperationIdAndDateBetweenOrderByDate(division.getCode(), operation.getId(), DateS, DateE);
		//Пользователь есть только в ПартБоксе
		for (OutDoc oList: outDoc) {	
			List<PartBox> partBox = partBoxRepository.findByOutDocIdAndEmployeeId(oList.getId(), employee.getId());
			if (!partBox.isEmpty()) {
				int quantity = 0;
				for (PartBox pList: partBox) {
					quantity = quantity + pList.getQuantity();
				}	
				//(String id, Operation operation, int number, Date date, String division_code)
				result.add( new OutDoc (oList.getId(), oList.getOperation(), quantity, oList.getDate(), oList.getDivision().getCode()));
			}
		}
		
		return result;
	}
}
