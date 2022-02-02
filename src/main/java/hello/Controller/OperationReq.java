package hello.Controller;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OperationReq {
	public OperationReq(Long id, String name, Date dt, String division_code) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
		this.division_code = division_code;
	}
	@SuppressWarnings("unused")
	private OperationReq() {
		super();
	}
	@Id
	@JsonProperty("_id")
	@NotNull
	Long id;
	
	@JsonProperty("_Opers")
	@NotNull
	String name;
	
	@JsonProperty("_dt")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date dt = new Date();
	
	@JsonProperty("division_code")
	@NotNull
	String division_code;
}
