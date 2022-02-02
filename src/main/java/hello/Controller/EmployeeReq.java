package hello.Controller;

import java.util.Date;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeReq {
	@SuppressWarnings("unused")
	private EmployeeReq() {
		super();
	}
	@Id
	@JsonProperty("_id")
	@NotNull
	public Long id;
	
	@JsonProperty("_tn_Sotr")
	@NotNull
	String code;
	
	@JsonProperty("_Sotr")
	@NotNull
	String name;
	
	@JsonProperty("_DT")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date dt = new Date();
	
	public String division_code;
	
	@JsonProperty("Id_o")
	@NotNull
	public Long operation_id ;
	
	@JsonProperty("Id_d")
	@NotNull
	public Long department_id ;

	public EmployeeReq (Long id, String code, String name, Date dt, String division_code, Long operation_id, Long department_id)	{
		this.id = id;
		this.code = code;
		this.name = name;
		this.dt = dt;
		this.division_code = division_code;
		this.operation_id = operation_id;
		this.department_id = department_id;
	}

}
