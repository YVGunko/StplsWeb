package hello.Department;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Division.Division;
import hello.Operation.Operation;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Department{
	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("_id")
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
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date dt = new Date();
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Division division ;
	@JsonProperty("Id_o")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("1")
	public Operation operation ;
	
	
	public Department() {
		super();
	}
	public Department(Long id, String code, String name, Date dt, Division division, Operation operation) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.dt = dt;
		this.division = division;
		this.operation = operation;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
}
