package hello.Client;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Client {
	public Client() {
		super();
	}
	public Client(String id, String name, Date dt) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
	}
	public Client(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.dt = new Date();
	}
	public Client(String id, String name, String email, String phone, String id1c) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.dt = new Date();
		this.id1c = id1c;
	}
	public Client(String id) {
		super();
		this.id = id;
	}
	
	@Id
	@JsonProperty("id")
	@NotNull
	public String id;
	@JsonProperty("name")
	@NotNull
	public String name;
	@JsonProperty("dt")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date dt = new Date();
	
	@JsonProperty("email")
	public String email;
	@JsonProperty("phone")
	public String phone;
	@JsonProperty("id1c")
	public String id1c;
	
	public Date getDt() {
		return dt;
	}
	
	public void setDt() {
		this.dt = new Date();
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId1c() {
		return id1c;
	}
	public void setId1c(String id1c) {
		this.id1c = id1c;
	}
}
