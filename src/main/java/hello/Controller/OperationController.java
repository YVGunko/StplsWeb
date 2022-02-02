package hello.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Operation.Operation;
import hello.Controller.OperationReq;
import hello.Operation.OperationRepository;

@RestController
public class OperationController {
	@Autowired
	OperationRepository operationRepository;
	@GetMapping("/operation") 
	public List<OperationReq> getOperation(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss") Date date) throws RuntimeException{
		List<OperationReq> result = new ArrayList<>();
		
		if (date==null) {
			for (Operation b : operationRepository.findAll()) {
				OperationReq dReq = new OperationReq(b.getId(), b.getName(), b.getDt(), b.division.getCode());
				result.add(dReq);
			}
		}
		else
		{
			for (Operation b : operationRepository.findByDtGreaterThan(date)) {
				OperationReq dReq = new OperationReq(b.getId(), b.getName(), b.getDt(), b.division.getCode());
				result.add(dReq);
			}
		}
		return result;
		
	}
}
