package hello.Glue;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
public class Glue {
	public Glue() {
		super();
	}
	public Glue(String id, String name, Date dt, Double cost) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
		this.cost = cost;
	}
	@Id
	@JsonProperty("id")
	@NotNull
	public String id;
	@JsonProperty("name")
	@NotNull
	String name;
	@JsonProperty("dt")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date dt = new Date();
	@JsonProperty("cost")
	@NotNull
	@ColumnDefault("0")
	Double cost;
	
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Date getDt() {
		return dt;
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
}
