package hello.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Box.Box;
import hello.Box.BoxRepository;
import hello.BoxMoves.BoxMove;
import hello.BoxMoves.BoxMovesRepository;
import hello.BoxMoves.BoxMovesService;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.PartBox.PartBoxService;

@RestController
public class ServiceController {
	private int pageSize = 10000;


	@Autowired
	private BoxMovesService boxMovesService;
	@Autowired
	private BoxMovesRepository boxMovesRepository;
	@Autowired
	private PartBoxRepository partBoxRepository;
	@Autowired
	private BoxRepository boxRepository;
/*
	public boolean containsName(final List<Object> list, final String name){
		return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	*/
	@PostMapping("/setSendToMasterDateInParallel")
	//@Transactional
	public boolean setSendToMasterDateInParallel(@RequestBody ArrayList<String> Qrd, 
			@RequestParam(value="operation", required=true) long operationId) throws Exception{
		//[	    "712000100000014545.13.0.50.14.3;000000028",
		//Нужно найти запись в MasterData где orderId = "712000100000014545.13.0.50"
		// Do it in a bunch
		//
		
		long start = System.currentTimeMillis();
		Holder<Integer> runCount = new Holder<>(0);
		runCount.value = 0;
		Qrd.parallelStream().forEach(qrd -> { boxMovesService.setSendToMasterDate(qrd, operationId); runCount.value++;});
		
		System.out.println("setSendToMasterDateInParallel run took: " 
				+ (System.currentTimeMillis() - start)
				+ ". Operation counter = "+runCount.value);		
		return true;
	}
	@GetMapping("/service3") 
	public ArrayList<String> service3() throws RuntimeException{

		ArrayList<String>  result =partBoxRepository.selectDataForService3().orElseThrow(() -> new NoSuchElementException("service3 return no rows"));
		 result.forEach(System.out::println);
		 return result;

	}
	@GetMapping("/service2") 
	public int service2(@RequestParam(value="pages", required=false) Integer totalPages) throws RuntimeException{
		long start = System.currentTimeMillis();
		Holder<Integer> runCount = new Holder<>(0);
		runCount.value = 0;
		System.out.println("numberOfPages - " + totalPages);
		if (totalPages == null)
			totalPages = boxMovesRepository.getIdAndBoxQuantityPageable(PageRequest.of(0, pageSize)).getTotalPages();
		
		for (int counterOfPages = 0; counterOfPages < totalPages; counterOfPages++) {
			Page<BoxMoveBoxAndQuantityDTO> boxMovePage = boxMovesRepository.getIdAndBoxQuantityPageable(PageRequest.of(counterOfPages, pageSize)); //BoxMove.id, Box.id, Box.boxQuantity
			List<BoxMoveBoxAndQuantityDTO> boxMoveList = boxMovePage.getContent();
			boxMoveList
				.parallelStream()
					.forEach(bm -> { boxMovesService.doCompareAndSaveIfEqual(bm.getBmId(),bm.getQuantityBox()); runCount.value++; });
		}
		System.out.println("service2 run took: " + (System.currentTimeMillis() - start));
		return runCount.value;
	}
	@GetMapping("/service1") 
	public int service1(@RequestParam(value="pages", required=false) Integer totalPages) throws RuntimeException{
		long start = System.currentTimeMillis();
		int counterOfRows = 0;
		System.out.println("numberOfPages - " + totalPages);
		if (totalPages == null)
			totalPages = boxMovesRepository.getIdAndBoxQuantityPageable(PageRequest.of(0, pageSize)).getTotalPages();
		
		for (int counterOfPages = 0; counterOfPages < totalPages; counterOfPages++) {
			Page<BoxMoveBoxAndQuantityDTO> boxMovePage = boxMovesRepository.getIdAndBoxQuantityPageable(PageRequest.of(counterOfPages, pageSize)); //BoxMove.id, Box.id, Box.boxQuantity
			for (BoxMoveBoxAndQuantityDTO bm : boxMovePage) {
				counterOfRows = counterOfRows + boxMovesService.doCompareAndSaveIfEqual(bm.getBmId(),bm.getQuantityBox());
				//System.out.println("BoxMove updated by PartBox - " + counterOfRows);			
			}
		}
				
		System.out.println("boxRepository.SaveAll " + (System.currentTimeMillis() - start));

		return counterOfRows;
	}
	
	@GetMapping("/serviceSetBoxMoveSentToMasterDate") 
	public int serviceSetBoxMoveSentToMasterDate(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy") Date orderDate) throws RuntimeException{
		int count = 0;
		int countBox = 0;
		Date dt = null;
		List<BoxMove> boxMoveList = boxMovesRepository.findBySentToMasterDateOrderByDate(null);
		System.out.println("BoxMove records to be updated by PartBox - " + boxMoveList.size());
		for (BoxMove boxMove:boxMoveList) {
			List<Integer> partBoxQuantityList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNotNull(boxMove.getId()).stream().map(PartBox::getQuantity).collect(Collectors.toList());
			List<Date> partBoxDateList = partBoxRepository.findByBoxMoveIdAndSentToMasterDateIsNotNull(boxMove.getId()).stream().map(PartBox::getDate).collect(Collectors.toList());
			if (partBoxDateList.size()>0) dt = partBoxDateList.get(0);
			if (partBoxQuantityList.size()>0) { //Если есть
				int quantityBox = boxMove.getBox().getQuantityBox();
				for (int i = 0; i < partBoxQuantityList.size(); i = i + 1 ) { 
					quantityBox = quantityBox - partBoxQuantityList.get(i);
				}
				if (quantityBox==0) {
					boxMove.setSentToMasterDate(dt);
					boxMovesRepository.save(boxMove);
					count = count + 1;
					System.out.println("BoxMove updated by PartBox - " + count);
				}
			}			
		}
		System.out.println("Total BoxMove updated by PartBox - " + count);
		List<Box> boxList = boxRepository.findBySentToMasterDateIsNotNullOrderByDate();
		for (Box box:boxList) {
			List<BoxMove> boxMoveList1 = boxMovesRepository.findByBoxIdAndSentToMasterDateIsNull(box.getId()).stream().collect(Collectors.toList());
			if (boxMoveList1.size() >0) { //Если есть
				dt = box.getSentToMasterDate();
				for (int i = 0; i < boxMoveList1.size(); i = i + 1 ) { 
					boxMoveList1.get(i).setSentToMasterDate(dt);
					boxMovesRepository.saveAll(boxMoveList1);
					countBox = countBox + 1;
					System.out.println("BoxMove updated by Box - " + countBox);
				}
			}
		}
		boxList = boxRepository.findByArchiveOrderByDate(true);
		System.out.println("BoxMove records to be updated by BoxArchive - " + boxList.size());
		for (Box box:boxList) {
			List<BoxMove> boxMoveList1 = boxMovesRepository.findByBoxIdAndOperationIdAndSentToMasterDateIsNull(box.getId(), 1).stream().collect(Collectors.toList());
			if (boxMoveList1.size() >0) { //Если есть
				dt = box.getReceivedFromMobileDate();
				for (int i = 0; i < boxMoveList1.size(); i = i + 1 ) { 
					boxMoveList1.get(i).setSentToMasterDate(dt);
					boxMovesRepository.saveAll(boxMoveList1);
					countBox = countBox + 1;
					System.out.println("BoxMove updated by Box - " + countBox);
				}
			}
		}
		System.out.println("Total BoxMove updated by Box - " + countBox);
		count = count + countBox;
		System.out.println("Total BoxMove updated - " + count);
		return count;
	}
}
