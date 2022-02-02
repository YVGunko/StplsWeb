package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class EmployeeRequest {
	@NotNull
	public List<EmployeeReq> employeeReqList = new ArrayList<>();
}
