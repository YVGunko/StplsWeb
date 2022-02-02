package hello.Operation;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Operation{
	public Operation() {
		super();
	}
	public Operation(Long id, String name, Date dt, Division division) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
		this.division = division;
	}

	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("_id")
	Long id;
	@JsonProperty("_Opers")
	@NotNull
	String name;
	@JsonProperty("_dt")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date dt = new Date();
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Division division ;
	
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
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
}
