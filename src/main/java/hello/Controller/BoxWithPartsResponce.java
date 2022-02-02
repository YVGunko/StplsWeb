package hello.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import hello.Box.Box;
import hello.PartBox.PartBox;

public class BoxWithPartsResponce {
	@NotNull
	public List<Box> boxList;
	@NotNull
	public List<PartBox> partBoxList;
	@NotNull
	public List<MoveReq> movesReqList = new ArrayList<>();
}
