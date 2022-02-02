package hello.User;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;


public class UserRequest {
	@NotNull
	public List<UserReq> userReqList = new ArrayList<>();
}
