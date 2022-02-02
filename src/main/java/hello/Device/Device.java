package hello.Device;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity

public class Device {
	public Device() {
		super();
	}
	public Device(Long id, String name, Date dt, String description) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Device(Long id, String name, Date dt) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
		this.description = "";
	}
	
	public Device(String name) {
		super();
		this.id = (long) 0;
		this.name = name;
		this.dt = new Date();
		this.description = "";
	}
	
	public Device(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.dt = new Date();
		this.description = "";
	}
	@Id
	@JsonProperty("id")
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@JsonProperty("name")
	@NotNull
	String name;
	
	@JsonProperty("dt")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date dt = new Date();
	
	@JsonProperty("description")
	String description;
	
	public Date getDt() {
		return dt;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
