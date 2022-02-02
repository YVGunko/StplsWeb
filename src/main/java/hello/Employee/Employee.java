package hello.Employee;

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

import hello.Department.Department;
import hello.Division.Division;
import hello.Operation.Operation;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Employee {
	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("_id")
	Long id;
	@JsonProperty("_tn_Sotr")
	@NotNull
	String code;
	@JsonProperty("_Sotr")
	@NotNull
	String name;
	
	@JsonProperty("_DT")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date dt = new Date();
	
	@JsonProperty("division_code")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public Division division ;
	
	@JsonProperty("Id_o")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("1")
	public Operation operation ;
	
	@JsonProperty("Id_d")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public Department department ;
	
	public Employee() {
		super();
	}
	public Employee(Long id, String code, String name, Date dt, Division division, Operation operation, Department department) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.dt = dt;
		this.division = division;
		this.operation = operation;
		this.department = department;
	}
	
	public Employee(Long id) {
		super();
		this.id = id;
		this.code = "...";
		this.name = "...";
		//this.dt = dt;
		this.division = new Division ("0");
		this.operation = new Operation();
		this.department = new Department();
	}
	
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
