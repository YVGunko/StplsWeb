package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class OutDocWithBoxWithMovesWithPartsRequest {
	@NotNull
	public List<OutDocReq> outDocReqList = new ArrayList<>();
	@NotNull
	public List<BoxReq> boxReqList = new ArrayList<>();
	@NotNull
	public List<PartBoxReq> partBoxReqList = new ArrayList<>();
	@NotNull
	public List<MoveReq> movesReqList = new ArrayList<>();
}
