package hello.Price;

public class ViewPriceFilter {
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
	public Boolean getShowSomeColumn() {
		return showSomeColumn;
	}
	public void setShowSomeColumn(Boolean showSomeColumn) {
		this.showSomeColumn = showSomeColumn;
	}
	private PriceType priceType ;
	private String name ;
	private Boolean showSomeColumn = false;
	private Boolean sample = false;
	private PriceRoot priceRoot ;
	
	public PriceRoot getPriceRoot() {
		return priceRoot;
	}
	public void setPriceRoot(PriceRoot priceRoot) {
		this.priceRoot = priceRoot;
	}
	public Boolean getSample() {
		return sample;
	}
	public void setSample(Boolean sample) {
		this.sample = sample;
	}

}
