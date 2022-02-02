package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class BoxWithMovesWithPartsRequest {
	@NotNull
	public List<BoxReq> boxReqList = new ArrayList<>();
	@NotNull
	public List<PartBoxReq> partBoxReqList = new ArrayList<>();
	@NotNull
	public List<MoveReq> movesReqList = new ArrayList<>();
}
