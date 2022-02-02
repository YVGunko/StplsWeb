package hello.OutDoorOrder;

public class ClientRep {
	public ClientRep(String id, String name, Long number) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
	}

	public String id;
	public String name;
	public Long number;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
}
