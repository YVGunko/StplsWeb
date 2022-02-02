package hello.Controller;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentReq {
	@SuppressWarnings("unused")
	private DepartmentReq() {
		super();
	}
	public DepartmentReq(Long id, String code, String name, Date dt, String division_code, Long operation_id) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.dt = dt;
		this.division_code = division_code;
		this.operation_id = operation_id;
	}
	@Id
	@JsonProperty("_id")
	@NotNull
	Long id;
	@JsonProperty("_Id_deps")
	@NotNull
	String code;
	@JsonProperty("_Name_Deps")
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
}
