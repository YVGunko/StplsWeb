package hello.Web.OutDoc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.utils;
import hello.Box.BoxRepository;
import hello.BoxMoves.BoxMovesRepository;
import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.MasterData.MasterDataRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.OutDoc.OutDoc;
import hello.OutDoc.OutDocRepository;
import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;

@Controller
@RequestMapping("/viewOutDocCalc")
public class ViewOutDocSummaController {
	@Autowired	
	OperationRepository operationRepository;
	@Autowired	
	DivisionRepository divisionRepository;
	@Autowired
	OutDocRepository outdocRepository;
	@Autowired
	PartBoxRepository partBoxRepository;
	@Autowired
	PartBoxService partBoxService;
	@Autowired
	BoxMovesRepository boxMovesRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	MasterDataRepository masterDataRepository;
	@Autowired
	BoxRepository boxRepository;
	
	Command command = new Command();
	
	@ModelAttribute("operations")
	public List<Operation> populateOperations() {
	    return operationRepository.findAll();
	}
	@ModelAttribute("divisions")
	public List<Division> populateDivisions() {
	    return divisionRepository.findAll();
	}
	@ModelAttribute("employees")
	public List<Employee> populateEmployee() {
	    return employeeRepository.findAll();
	}

	@GetMapping
	public ModelAndView get() throws ParseException {

		return mavPost(command);

	}
	@PostMapping(params="action=filter")
	public ModelAndView mavPost(

	    @ModelAttribute("command") Command command ) throws ParseException {
			this.command = command;
			ModelAndView mv = new ModelAndView();
			List<OutDoc> outDoc = new ArrayList<>();

			List<OutDocWeb> outDocWeb = new ArrayList<>();
			int tableBoxNumberSum = 0;
			int tableQuantitySum = 0;
			double tableOperationSum = 0;
			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			if (command.getOperation() == null) System.out.println("command.getOperation()==null");
			if (command.getDivision() ==null) System.out.println("command.getDivision()==null");
			if (command.getEmployee() ==null) System.out.println("command.getEmployee()==null");
			
			if (command.getOperation() == null ){
				outDoc = outdocRepository.findByDateBetweenOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
				if (outDoc!=null) {
					for (OutDoc b : outDoc) {
						long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
						
						int quantitySum = 0;
						double dSumma = 0;
						String sEmployee = "";

						List<MdWeb> lsMS = boxMovesRepository.findMasterDataByBoxMovePartBoxOutDoc(b);
						if (lsMS!=null) {
							boxCount = lsMS.size();
							for (MdWeb ms : lsMS) { // тут вот хрень. Как выбрать нужный атрибут, кут или глю ?
								if (ms.operation.getId() == b.getOperation().getId()) {
									if (ms.operation.getId() == 4)
										dSumma = dSumma+ms.cut.getCost() *ms.pb_quantity;
									if (ms.operation.getId() == 5)
										dSumma = dSumma+ms.glue.getCost() *ms.pb_quantity;
									if (ms.operation.getId() == 6)
										dSumma = dSumma+ms.paint.getCost() *ms.pb_quantity;
									}
								quantitySum = quantitySum+ms.pb_quantity;
								if (sEmployee != ms.employee.getName())
									sEmployee = ms.employee.getName();

							}
						}
						outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),sEmployee,
								b.getNumber(),(int)(boxCount),quantitySum,String.valueOf(dSumma)));

						tableBoxNumberSum += boxCount;
						tableQuantitySum += quantitySum;
						tableOperationSum += dSumma;
					}
				}
			}
			else
			{
			//operation can't be 0 due to DB restrictions 
				if ((command.getDivision().getCode() != null) & (command.getEmployee().getId() != null)) {
					if (command.getDivision().getCode().equals("0") & command.getEmployee().getId() == 0){
						System.out.println("Division != null & Employee != 0 ");
						
						outDoc = outdocRepository.findByOperationIdAndDateBetweenOrderByDate(command.getOperation().getId(),utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						if (outDoc!=null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
								
								double dSumma = 0;
								String sEmployee = "";

								List<MdWeb> lsMS = boxMovesRepository.findMasterDataByBoxMovePartBoxOutDoc(b);
								if (lsMS!=null) {
									boxCount = lsMS.size();
									for (MdWeb ms : lsMS) { // тут вот хрень. Как выбрать нужный атрибут, кут или глю ?
										if (ms.operation.getId() == b.getOperation().getId()) {
											if (ms.operation.getId() == 4)
												dSumma = dSumma+ms.cut.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 5)
												dSumma = dSumma+ms.glue.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 6)
												dSumma = dSumma+ms.paint.getCost() *ms.pb_quantity;
											}
										quantitySum = quantitySum+ms.pb_quantity;
										if (sEmployee != ms.employee.getName())
											sEmployee = ms.employee.getName();
									}
								}
								BigDecimal d1 = BigDecimal.valueOf(dSumma);
								DecimalFormat df = new DecimalFormat("#0.00");

								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),sEmployee,
										b.getNumber(),(int)(boxCount),quantitySum,df.format(d1)));

								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
								tableOperationSum += dSumma;
							}
						}
					} 
					if (!command.getDivision().getCode().equals("0") & command.getEmployee().getId() == 0){
						System.out.println("Division!=0 & Employee == 0 ");
						
						outDoc = outdocRepository.findByDivisionCodeAndOperationIdAndDateBetweenOrderByDate(command.getDivision().getCode(), 
								command.getOperation().getId(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						if (outDoc!=null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
								
								double dSumma = 0;
								String sEmployee = "";

								List<MdWeb> lsMS = boxMovesRepository.findMasterDataByBoxMovePartBoxOutDoc(b);
								if (lsMS!=null) {
									boxCount = lsMS.size();
									for (MdWeb ms : lsMS) { // тут вот хрень. Как выбрать нужный атрибут, кут или глю ?
										if (ms.operation.getId() == b.getOperation().getId()) {
											if (ms.operation.getId() == 4)
												dSumma = dSumma+ms.cut.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 5)
												dSumma = dSumma+ms.glue.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 6)
												dSumma = dSumma+ms.paint.getCost() *ms.pb_quantity;
											}
										quantitySum = quantitySum+ms.pb_quantity;
										if (sEmployee != ms.employee.getName())
											sEmployee = ms.employee.getName();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),sEmployee,
										b.getNumber(),(int)(boxCount),quantitySum,String.valueOf(dSumma)));

								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
								tableOperationSum += dSumma;
							}
						}
					}
					if (command.getDivision().getCode().equals("0")  & command.getEmployee().getId() != 0){
						System.out.println("Division==0 & Employee != 0 ");
						
						outDoc = partBoxService.selectOutDocByEmployeeId(command.getEmployee(),
								command.getOperation(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						
						if (outDoc != null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocIdAndEmployeeId(b, command.getEmployee());
								
								double dSumma = 0;
								String sEmployee = "";

								List<MdWeb> lsMS = boxMovesRepository.findMasterDataByBoxMovePartBoxOutDoc(b);
								if (lsMS!=null) {
									boxCount = lsMS.size();
									for (MdWeb ms : lsMS) { // тут вот хрень. Как выбрать нужный атрибут, кут или глю ?
										if (ms.operation.getId() == b.getOperation().getId()) {
											if (ms.operation.getId() == 4)
												dSumma = dSumma+ms.cut.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 5)
												dSumma = dSumma+ms.glue.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 6)
												dSumma = dSumma+ms.paint.getCost() *ms.pb_quantity;
											}
										quantitySum = quantitySum+ms.pb_quantity;
										if (sEmployee != ms.employee.getName())
											sEmployee = ms.employee.getName();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),sEmployee,
										b.getNumber(),(int)(boxCount),quantitySum,String.valueOf(dSumma)));

								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
								tableOperationSum += dSumma;
							}
						}
					}
					if (!command.getDivision().getCode().equals("0") & command.getEmployee().getId() != 0){	
						System.out.println("Division!=0 & Employee != 0 ");
						
						outDoc = partBoxService.selectOutDocByDivisionIdAndEmployeeId(command.getDivision(), command.getEmployee(), command.getOperation(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
											
						if (outDoc != null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocIdAndEmployeeId(b, command.getEmployee());
								
								double dSumma = 0;
								String sEmployee = "";

								List<MdWeb> lsMS = boxMovesRepository.findMasterDataByBoxMovePartBoxOutDoc(b);
								if (lsMS!=null) {
									boxCount = lsMS.size();
									for (MdWeb ms : lsMS) { // тут вот хрень. Как выбрать нужный атрибут, кут или глю ?
										if (ms.operation.getId() == b.getOperation().getId()) {
											if (ms.operation.getId() == 4)
												dSumma = dSumma+ms.cut.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 5)
												dSumma = dSumma+ms.glue.getCost() *ms.pb_quantity;
											if (ms.operation.getId() == 6)
												dSumma = dSumma+ms.paint.getCost() *ms.pb_quantity;
											}
										quantitySum = quantitySum+ms.pb_quantity;
										if (sEmployee != ms.employee.getName())
											sEmployee = ms.employee.getName();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),sEmployee,
										b.getNumber(),(int)(boxCount),quantitySum,String.valueOf(dSumma)));

								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
								tableOperationSum += dSumma;
							}
						}
	
					}
				}
			} 
			
			mv.addObject("outdocs", outDocWeb);
			
			command.setBoxNumberSum(tableBoxNumberSum);
			command.setQuantitySum(tableQuantitySum);
			
			BigDecimal d1 = BigDecimal.valueOf(tableOperationSum);
			DecimalFormat df = new DecimalFormat("#0.00");
			
			command.setSumma(df.format(d1));
			mv.addObject("command", command);
			mv.setViewName("viewOutDocCalc");
			
			return mv;

	}
	
	@PostMapping(params="action=open")
		public ModelAndView mavOpen(
	
		    @ModelAttribute("command") Command command ) throws ParseException {
				this.command = command;
				ModelAndView mv = new ModelAndView();
		return mv;
	
	}	
	
}
