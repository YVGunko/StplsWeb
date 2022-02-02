package hello.MasterData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorReq {

	public ColorReq(String id, String name, String division_code) {
		super();
		this.id = id;
		this.name = name;
		this.division_code = division_code;
	}

	public ColorReq() {
		super();
	}
	
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

	public String getDivision_code() {
		return division_code;
	}

	public void setDivision_code(String division_code) {
		this.division_code = division_code;
	}

	@JsonProperty("id")
	public String id;
	
	@JsonProperty("name")
	public String name;
	
	@JsonProperty("division_code")
	public String division_code;
}
