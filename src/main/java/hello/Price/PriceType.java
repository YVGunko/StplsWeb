package hello.Price;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class PriceType {
	public PriceType(Integer id, @NotNull String name, Double def_costs, Double def_paint, Double def_rant,
			Double def_shpalt, Double def_extra) {
		super();
		this.id = id;
		this.name = name;
		this.def_costs = def_costs;
		this.def_extra = def_extra;
		this.def_paint = def_paint;
		this.def_rant = def_rant;
		this.def_shpalt = def_shpalt;
	}
	public PriceType() {
		super();
	}


	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("id")
	public Integer id;
	
	@JsonProperty("name")
	@NotNull
	public String name;
	
	@Column(name = "def_costs", columnDefinition="Decimal(10,2) default '0.00'")
	public Double def_costs;
	@Column(name = "def_extra", columnDefinition="Decimal(10,2) default '0.00'")
	public Double def_extra;
	
	@Column(name = "def_paint", columnDefinition="Decimal(10,2) default '0.00'")
	public Double def_paint;
	
	@Column(name = "def_rant", columnDefinition="Decimal(10,2) default '0.00'")
	public Double def_rant;
	
	@Column(name = "def_shpalt", columnDefinition="Decimal(10,2) default '0.00'")
	public Double def_shpalt;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public Double getDef_costs() {
		return def_costs;
	}
	public void setDef_costs(Double def_costs) {
		this.def_costs = def_costs;
	}
	public Double getDef_paint() {
		return def_paint;
	}
	public void setDef_paint(Double def_paint) {
		this.def_paint = def_paint;
	}
	public Double getDef_rant() {
		return def_rant;
	}
	public void setDef_rant(Double def_rant) {
		this.def_rant = def_rant;
	}
	public Double getDef_shpalt() {
		return def_shpalt;
	}
	public void setDef_shpalt(Double def_shpalt) {
		this.def_shpalt = def_shpalt;
	}
	public Double getDef_extra() {
		return def_extra;
	}
	public void setDef_extra(Double def_extra) {
		this.def_extra = def_extra;
	}
}
