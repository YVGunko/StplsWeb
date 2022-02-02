package hello.Division;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity

public class Division {
	@Access(AccessType.PROPERTY)
	@Id
	String code;

	@NotNull
	String name;
	
	public Division() {
		super();
	}
	public Division(String division_code) {
		super();
		this.code = division_code;
		this.name = "";
	}
	public Division(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
