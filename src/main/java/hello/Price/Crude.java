package hello.Price;

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
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.utils;

@Entity
public class Crude {
	public Crude(Integer id, String crudeName, String columnName, Double crudeCost,
			Date dateOfLastChange, Double crudeExtra, Double crudePlus) {
		super();
		this.id = id;
		this.crudeName = crudeName;
		this.columnName = columnName;
		this.crudeCost = crudeCost;
		this.crudeExtra = crudeExtra;
		this.crudePlus = crudePlus;
		this.dateOfLastChange = dateOfLastChange;
	}

	public Double getCrudePlus() {
		return crudePlus;
	}

	public void setCrudePlus(Double crudePlus) {
		this.crudePlus = crudePlus;
	}

	public Crude() {
		super();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCrudeName() {
		return crudeName;
	}

	public void setCrudeName(String crudeName) {
		this.crudeName = crudeName;
	}

	public Double getCrudeCost() {
		return crudeCost;
	}

	public void setCrudeCost(Double crudeCost) {
		this.crudeCost = crudeCost;
	}

	public Date getDateOfLastChange() {
		return dateOfLastChange;
	}

	public void setDateOfLastChange(Date dateOfLastChange) {
		this.dateOfLastChange = dateOfLastChange;
	}

	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("id")
	public Integer id;
	@JsonProperty("crudeName")
	public String crudeName;
	@JsonProperty("columnName")
	public String columnName;
	@JsonProperty("crudeCost")
	public Double crudeCost;
	@JsonProperty("crudeExtra")
	public Double crudeExtra;
	@JsonProperty("crudePlus")
	public Double crudePlus;
	
	public Double getCrudeExtra() {
		return crudeExtra;
	}

	public void setCrudeExtra(Double crudeExtra) {
		this.crudeExtra = crudeExtra;
	}

	@JsonProperty("dateOfLastChange")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dateOfLastChange", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date dateOfLastChange ;
	
	public String getStringDateOfLastChange() {
		return utils.toString(utils.toLocalDate(dateOfLastChange));
	}
	
}
