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
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.utils;

@Entity
public class PriceRoot {
	public PriceRoot(Integer id, Date dateOfChange, String note, Integer plusValue, PriceType priceType, Boolean sample) {
		super();
		this.id = id;
		this.dateOfChange = dateOfChange;
		this.note = note;
		this.plusValue = plusValue;
		this.priceType = priceType;
		this.sample = sample;
	}
	public PriceRoot() {
		super();
	}
	
	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("id")
	public Integer id;
	
	@JsonProperty("dateOfChange")
	@JsonFormat(pattern="dd.MM.yyyy",timezone="Europe/Moscow")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dateOfChange", columnDefinition="DATE")
	public Date dateOfChange;
	
	@JsonProperty("priceTypeId")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	public PriceType priceType ;
	
	@JsonProperty("plusValue")
	public Integer plusValue;
	
	@JsonProperty("note")
	public String note;
	
	@JsonProperty("sample")
	@Column(name = "sample", nullable = false, columnDefinition="bit default 0")
	public Boolean sample=false;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public Date getDateOfChange() {
		return utils.getStartOfDay(dateOfChange);
	}
	public void setDateOfChange(Date dateOfChange) {
		this.dateOfChange = dateOfChange;
	}
	public Integer getPlusValue() {
		return plusValue;
	}
	public void setPlusValue(Integer plusValue) {
		this.plusValue = plusValue;
	}
	public PriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
	public String getDesc() {
		return utils.toStringOnlyDate(utils.toLocalDate(dateOfChange));
	}
	public Boolean getSample() {
		return sample;
	}
	public void setSample(Boolean sample) {
		this.sample = sample;
	}
}
