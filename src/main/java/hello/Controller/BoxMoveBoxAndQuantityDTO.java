package hello.Controller;

public class BoxMoveBoxAndQuantityDTO {
	public BoxMoveBoxAndQuantityDTO(String bmId, String boxId, Integer quantityBox) {
		super();
		this.bmId = bmId;
		this.boxId = boxId;
		this.quantityBox = quantityBox;
	}
	public BoxMoveBoxAndQuantityDTO() {
		// TODO Auto-generated constructor stub
	}
	private String bmId;
	private String boxId;
	private Integer quantityBox;
	public String getBmId() {
		return bmId;
	}
	public void setBmId(String bmId) {
		this.bmId = bmId;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public Integer getQuantityBox() {
		return quantityBox;
	}
	public void setQuantityBox(Integer quantityBox) {
		this.quantityBox = quantityBox;
	}
}
