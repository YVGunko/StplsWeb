package hello.Web.Order;


public class OrderMoveRep {
	public OrderMoveRep(Long moveId, String moveName, Long box, Long prod) {
		super();
		this.moveId = moveId;
		this.moveName = moveName;
		this.box = box;
		this.prod = prod;
	}
	public OrderMoveRep() {
		// TODO Auto-generated constructor stub
	}
	public String getMoveName() {
		return moveName;
	}
	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}
	public Long getBox() {
		return box;
	}
	public void setBox(Long box) {
		this.box = box;
	}
	public Long getProd() {
		return prod;
	}
	public void setProd(Long prod) {
		this.prod = prod;
	}
	private String moveName;
    private Long box;
    private Long prod;
    private Long moveId;
    
	public Long getMoveId() {
		return moveId;
	}
	public void setMoveId(Long moveId) {
		this.moveId = moveId;
	}
    
}
