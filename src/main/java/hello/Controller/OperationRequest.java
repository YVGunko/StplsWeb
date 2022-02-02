package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class OperationRequest {
	@NotNull
	public List<OperationReq> operationReqList = new ArrayList<>();
}
