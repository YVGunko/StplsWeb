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

@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name","price_type_id","b_liner","b_rant","price_root_id"}))
@Entity
public class Price {
	public Price(Integer id, String name, PriceType priceType, Double costs, Double paint,
			Double rant, Double shpalt, Double number_per_box, Double weight, Date dateOfLastChange, Boolean bRant,
			Boolean bLiner, String note, PriceRoot priceRoot) {
		super();
		this.id = id;
		this.name = name;
		this.note = note;
		this.priceType = priceType;
		this.priceRoot = priceRoot;
		this.costs = costs;
		this.paint = paint;
		this.rant = rant;
		this.shpalt = shpalt;
		this.number_per_box = number_per_box;
		this.weight = weight;
		this.dateOfLastChange = dateOfLastChange;
		this.bRant = bRant;
		this.bLiner = bLiner;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Price() {
		super();
	}

	public Date getDateOfLastChange() {
		return dateOfLastChange;
	}
	public void setDateOfLastChange(Date dateOfLastChange) {
		this.dateOfLastChange = dateOfLastChange;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
	public Double getCosts() {
		return costs;
	}
	public void setCosts(Double costs) {
		this.costs = costs;
	}
	public Double getPaint() {
		return paint;
	}
	public void setPaint(Double paint) {
		this.paint = paint;
	}
	public Double getRant() {
		return rant;
	}
	public void setRant(Double rant) {
		this.rant = rant;
	}
	public Double getShpalt() {
		return shpalt;
	}
	public void setShpalt(Double shpalt) {
		this.shpalt = shpalt;
	}
	public Double getNumber_per_box() {
		return number_per_box;
	}
	public void setNumber_per_box(Double number_per_box) {
		this.number_per_box = number_per_box;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("id")
	public Integer id;
	
	@JsonProperty("name")
	@NotNull
	public String name;
	
	@JsonProperty("priceTypeId")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@ColumnDefault("0")
	public PriceType priceType ;
	
	@JsonProperty("priceRootId")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	public PriceRoot priceRoot ;
	
	@JsonProperty("costs")
	public Double costs;
	@JsonProperty("paint")
	public Double paint;
	@JsonProperty("rant")
	public Double rant;
	@JsonProperty("shpalt")
	public Double shpalt;
	@JsonProperty("number_per_box")
	public Double number_per_box;
	@JsonProperty("weight")
	public Double weight;	
	@JsonProperty("dateOfLastChange")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dateOfLastChange", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date dateOfLastChange;
	@JsonProperty("bRant")
	@Column(name = "b_rant", nullable = false, columnDefinition="bit default 0")
	public Boolean bRant=false;
	@JsonProperty("bLiner")
	@Column(name = "b_liner", nullable = false, columnDefinition="bit default 0")
	public Boolean bLiner=false;
	
	public Boolean getbRant() {
		return bRant;
	}
	public void setbRant(Boolean bRant) {
		this.bRant = bRant;
	}
	public Boolean getbLiner() {
		return bLiner;
	}
	public void setbLiner(Boolean bLiner) {
		this.bLiner = bLiner;
	}
	
	@JsonProperty("note")
	public String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PriceRoot getPriceRoot() {
		return priceRoot;
	}

	public void setPriceRoot(PriceRoot priceRoot) {
		this.priceRoot = priceRoot;
	}
}
