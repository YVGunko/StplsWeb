package hello.Price;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceWeb {

	public PriceWeb() {
		super();

	}

	public PriceWeb(Integer id, String name, Double costs, Double paint, Double rant,
			Double shpalt, Double number_per_box, Double weight,
			List <PriceColumn> column, Boolean bRant, Boolean bLiner, String note, Integer priceRootId, Boolean sample ) {
		super();
		this.id = id;
		this.name = name;
		this.note = note;
		this.costs = costs;
		this.paint = paint;
		this.rant = rant;
		this.shpalt = shpalt;
		this.number_per_box = number_per_box;
		this.weight = weight;
		this.column = column;
		this.bRant = bRant;
		this.bLiner = bLiner;
		this.priceRootId = priceRootId;
		this.sample = sample;
	}
	public PriceWeb(Integer id, String name, Double number_per_box, Double weight, Boolean bRant, Boolean bLiner, Integer priceRootId, Boolean sample) {
		super();
		this.id = id;
		this.name = name;
		this.number_per_box = number_per_box;
		this.weight = weight;
		this.bRant = bRant;
		this.bLiner = bLiner;
		this.priceRootId = priceRootId;
		this.sample = sample;
	}
	public PriceWeb(Integer id, String name,
			List <PriceColumn> column, List<String> strHeader, Double rant, Double shpalt) {
		super();
		this.id = id;
		this.name = name;
		this.column = column;
		this.strHeader = strHeader;
		this.rant = rant;
		this.shpalt = shpalt;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List <PriceColumn> getColumn() {
		return column;
	}
	public void setColumn(List <PriceColumn> column) {
		this.column = column;
	}
	public List<String> getStrHeader() {
		return strHeader;
	}
	public void setStrHeader(List<String> strHeader) {
		this.strHeader = strHeader;
	}
	public Integer id;
	public Integer priceRootId;
	public String name;	
	public String note;
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public Double costs;	
	public Double paint;	
	public Double rant;	
	public Double shpalt;	
	public Double number_per_box;	
	public Double weight;	
	public List <PriceColumn> column;
	public List <String> strHeader;	
	public Boolean bRant=false;
	public Boolean bLiner=false;
	public Boolean sample=false;
	
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

	public Integer getPriceRootId() {
		return priceRootId;
	}

	public void setPriceRootId(Integer priceRootId) {
		this.priceRootId = priceRootId;
	}

	public Boolean getSample() {
		return sample;
	}

	public void setSample(Boolean sample) {
		this.sample = sample;
	}

}
