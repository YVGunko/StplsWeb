package hello.Web.OutDoc;

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
import hello.BoxMoves.BoxMovesRepository;
import hello.Division.Division;
import hello.Division.DivisionRepository;
import hello.Operation.Operation;
import hello.Operation.OperationRepository;
import hello.OutDoc.OutDoc;
import hello.OutDoc.OutDocRepository;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.User.User;
import hello.User.UserService;

@Controller
@RequestMapping("/viewOutDocs")
public class ViewOutDocController {
	@Autowired	
	OperationRepository operationRepository;
	@Autowired	
	DivisionRepository divisionRepository;
	@Autowired
	OutDocRepository outdocRepository;
	@Autowired
	PartBoxRepository partBoxRepository;
	@Autowired
	BoxMovesRepository boxMovesRepository;
	@Autowired
	UserService UserService;
	
	Command command = new Command();
	
	@ModelAttribute("operations")
	public List<Operation> populateOperations() {
	    return operationRepository.findAll();
	}
	@ModelAttribute("divisions")
	public List<Division> populateDivisions() {
	    return divisionRepository.findAll();
	}
	@ModelAttribute("users")
	public List<User> populateUsers() {
	    return UserService.findAll();
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
			if (command.getUser() ==null) System.out.println("command.getUser()==null");
			
			if (command.getOperation() == null ){
				outDoc = outdocRepository.findByDateBetweenOrderByDate(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
				if (outDoc!=null) {
					for (OutDoc b : outDoc) {
						int quantitySum = 0;
						long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);

						List<PartBox> partBox = partBoxRepository.findByOutDocId(b.getId());
						if (partBox!=null) {
							for (PartBox pb : partBox) {
								quantitySum = quantitySum+pb.getQuantity();
							}
						}
						outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),b.getUser().getName(), b.getNumber(),(int)(boxCount),quantitySum));
						tableBoxNumberSum += boxCount;
						tableQuantitySum += quantitySum;
					}
				}
			}
			else
			{
			//operation can't be null due to DB restrictions 
				if ((command.getDivision().getCode() != null) & (command.getUser().getId() != null)) {
					if (command.getDivision().getCode().equals("0") & command.getUser().getId() == 0){
						System.out.println("command.getDivision().getCode().equals(0) & command.getUser().getId() == 0 ");
						outDoc = outdocRepository.findByOperationIdAndDateBetweenOrderByDate(command.getOperation().getId(),utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						if (outDoc!=null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
								
								List<PartBox> partBox = partBoxRepository.findByOutDocId(b.getId());
								if (partBox!=null) {
									for (PartBox pb : partBox) {
										quantitySum = quantitySum+pb.getQuantity();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),b.getUser().getName(), b.getNumber(),(int)(boxCount),quantitySum));
								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
							}
						}
					} 
					if (!command.getDivision().getCode().equals("0") & command.getUser().getId() == 0){
						System.out.println("!command.getDivision().getCode().equals(0) & command.getUser().getId() == 0 ");
						outDoc = outdocRepository.findByDivisionCodeAndOperationIdAndDateBetweenOrderByDate(command.getDivision().getCode(), 
								command.getOperation().getId(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						if (outDoc!=null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
								List<PartBox> partBox = partBoxRepository.findByOutDocId(b.getId());
								if (partBox!=null) {
									for (PartBox pb : partBox) {
										quantitySum = quantitySum+pb.getQuantity();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),b.getUser().getName(), b.getNumber(),(int)(boxCount),quantitySum));
								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
							}
						}
					}
					if (command.getDivision().getCode().equals("0")  & command.getUser().getId() != 0){
						System.out.println("command.getDivision().getCode().equals(0) & command.getUser().getId() != 0 ");
						outDoc = outdocRepository.findByOperationIdAndUserIdAndDateBetweenOrderByDate( 
								command.getOperation().getId(), command.getUser().getId(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						if (outDoc!=null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
								List<PartBox> partBox = partBoxRepository.findByOutDocId(b.getId());
								if (partBox!=null) {
									for (PartBox pb : partBox) {
										quantitySum = quantitySum+pb.getQuantity();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),b.getUser().getName(), b.getNumber(),(int)(boxCount),quantitySum));
								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
							}
						}
					}
					if (!command.getDivision().getCode().equals("0") & command.getUser().getId() != 0){	
						System.out.println("!command.getDivision().getCode().equals(0) & command.getUser().getId() != 0 ");
						outDoc = outdocRepository.findByDivisionCodeAndOperationIdAndUserIdAndDateBetweenOrderByDate(command.getDivision().getCode(), 
								command.getOperation().getId(), command.getUser().getId(),
								utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
								utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
						if (outDoc!=null) {
							for (OutDoc b : outDoc) {
								int quantitySum = 0;
								long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
								List<PartBox> partBox = partBoxRepository.findByOutDocId(b.getId());
								if (partBox!=null) {
									for (PartBox pb : partBox) {
										quantitySum = quantitySum+pb.getQuantity();
									}
								}
								outDocWeb.add(new OutDocWeb(b.getId(), mdyFormat.format(b.getDate()),b.getOperation().getName(),b.getDivision().getName(),b.getUser().getName(), b.getNumber(),(int)(boxCount),quantitySum));
								tableBoxNumberSum += boxCount;
								tableQuantitySum += quantitySum;
							}
	
						}
	
					}
				}
			} 
			
			mv.addObject("outdocs", outDocWeb);
			
			command.setBoxNumberSum(tableBoxNumberSum);
			command.setQuantitySum(tableQuantitySum);
			mv.addObject("command", command);
			mv.setViewName("viewOutDocs");
			
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
