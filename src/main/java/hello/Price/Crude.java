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
			Date dateOfLastChange, Double crudeExtra, Double crudePlus, Double directCosts,
			Double copyAddCosts, Double copyAddPrice, Double copyAddRant, Double crudeCurs) {
		super();
		this.id = id;
		this.crudeName = crudeName;
		this.columnName = columnName;
		this.crudeCost = crudeCost;
		this.crudeCurs = crudeCurs;
		this.crudeExtra = crudeExtra;
		this.crudePlus = crudePlus;
		this.directCosts = directCosts;
		this.copyAddCosts = copyAddCosts;
		this.copyAddPrice = copyAddPrice;
		this.copyAddRant = copyAddRant;
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
	@JsonProperty("crudeCurs")
	@Column(name = "crude_curs", columnDefinition="Decimal(10,2) default '1.00'")
	public Double crudeCurs;
	@JsonProperty("crudeExtra")
	public Double crudeExtra;
	@JsonProperty("crudePlus")
	public Double crudePlus;
	@JsonProperty("directCosts")
	public Double directCosts;
	@JsonProperty("copyAddCosts")
	public Double copyAddCosts;
	@JsonProperty("copyAddPrice")
	public Double copyAddPrice;
	@JsonProperty("copyAddRant")
	public Double copyAddRant;
	
	@JsonProperty("dateOfLastChange")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dateOfLastChange", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date dateOfLastChange ;
	
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
	public Double getCrudeExtra() {
		return crudeExtra;
	}

	public void setCrudeExtra(Double crudeExtra) {
		this.crudeExtra = crudeExtra;
	}
	
	public String getStringDateOfLastChange() {
		return utils.toString(utils.toLocalDate(dateOfLastChange));
	}

	public Double getDirectCosts() {
		return directCosts;
	}

	public void setDirectCosts(Double directCosts) {
		this.directCosts = directCosts;
	}

	public Double getCopyAddCosts() {
		return copyAddCosts;
	}

	public void setCopyAddCosts(Double copyAddCosts) {
		this.copyAddCosts = copyAddCosts;
	}

	public Double getCopyAddPrice() {
		return copyAddPrice;
	}

	public void setCopyAddPrice(Double copyAddPrice) {
		this.copyAddPrice = copyAddPrice;
	}

	public Double getCopyAddRant() {
		return copyAddRant;
	}

	public void setCopyAddRant(Double copyAddRant) {
		this.copyAddRant = copyAddRant;
	}

	public Double getCrudeCurs() {
		return crudeCurs;
	}

	public void setCrudeCurs(Double crudeCurs) {
		this.crudeCurs = crudeCurs;
	}
	
}
