package hello.Price;

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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(uniqueConstraints=@UniqueConstraint(columnNames={"price_id","price_type2crude_id"}))
@Entity
public class PriceColumn {
	public PriceColumn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOfLastChange() {
		return dateOfLastChange;
	}

	public void setDateOfLastChange(Date dateOfLastChange) {
		this.dateOfLastChange = dateOfLastChange;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public PriceType2Crude getPriceType2Crude() {
		return priceType2Crude;
	}

	public void setPriceType2Crude(PriceType2Crude priceType2Crude) {
		this.priceType2Crude = priceType2Crude;
	}

	public Double getColumnPrice() {
		return columnPrice;
	}

	public void setColumnPrice(Double columnPrice) {
		this.columnPrice = columnPrice;
	}

	public Double getColumnCosts() {
		return columnCosts;
	}

	public void setColumnCosts(Double columnCosts) {
		this.columnCosts = columnCosts;
	}
	
	public PriceColumn(Integer id, Date dateOfLastChange, @NotNull Price price, @NotNull PriceType2Crude priceType2Crude,
			Double columnPrice, Double columnCosts) {
		super();
		this.id = id;
		this.dateOfLastChange = dateOfLastChange;
		this.price = price;
		this.priceType2Crude = priceType2Crude;
		this.columnPrice = columnPrice;
		this.columnCosts = columnCosts;
	}



	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("id")
	public Integer id;
	
	@JsonProperty("dateOfLastChange")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dateOfLastChange", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date dateOfLastChange;
	
	@JsonProperty("price_id")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@ColumnDefault("0")
	public Price price ;
	
	@JsonProperty("priceType2CrudeId")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@ColumnDefault("0")
	public PriceType2Crude priceType2Crude ;
	
	@JsonProperty("columnPrice")
	@ColumnDefault("0")
	public Double columnPrice;
	
	@JsonProperty("columnCosts")
	@ColumnDefault("0")
	public Double columnCosts;
}
