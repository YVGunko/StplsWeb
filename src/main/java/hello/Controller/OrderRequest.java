package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class OrderRequest {
	@NotNull
	public List<OrderReq> orderReqList = new ArrayList<>();
}
