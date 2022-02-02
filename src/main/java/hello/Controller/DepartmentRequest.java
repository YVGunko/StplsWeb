package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class DepartmentRequest {
	@NotNull
	public List<DepartmentReq> departmentReqList = new ArrayList<>();
}
