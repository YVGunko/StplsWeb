package hello.Client;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class ClientRequest {

	public class OperationRequest {
		@NotNull
		public List<ClientReq> clientReqList = new ArrayList<>();
	}
}
