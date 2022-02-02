package hello.User;

import java.util.Date;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UserReq {
	@Id
	@NotNull
	@JsonProperty("_id")
	public Long id;
	
	@JsonProperty("name")
	@NotNull
	public String name;
	
	@JsonProperty("pswd")
	@NotNull
	public String pswd;
	
	@JsonProperty("superUser")
	@NotNull
	public Boolean superUser;
	
	@JsonProperty("Id_s")
	public Long employee_id ;
	
	@JsonProperty("DT")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date dt = new Date();
	
	@SuppressWarnings("unused")
	private UserReq() {
		super();
	}
	public UserReq(Long id, String name, String pswd, Boolean superUser, Long employee_id, Date dt) {
		super();
		this.id = id;
		this.name = name;
		this.pswd = pswd;
		this.superUser = superUser;
		this.employee_id = employee_id;
		this.dt = dt;
	}
}
