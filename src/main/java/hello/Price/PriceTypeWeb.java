package hello.Price;


public class PriceTypeWeb {
	public PriceTypeWeb(Integer id, String name, String priceColumns, String def_costs, String def_paint,
			String def_rant, String def_shpalt, String def_extra) {
		super();
		this.id = id;
		this.name = name;
		this.priceColumns = priceColumns;
		this.def_costs = def_costs;
		this.def_extra = def_extra;
		this.def_paint = def_paint;
		this.def_rant = def_rant;
		this.def_shpalt = def_shpalt;
	}
	public PriceTypeWeb() {
		super();
	}

	public String getPriceColumns() {
		return priceColumns;
	}
	public void setPriceColumns(String priceColumns) {
		this.priceColumns = priceColumns;
	}

	public Integer id;

	public String name;

	public String priceColumns;
	
	public String def_costs;
	public String def_extra;
	
	public String def_paint;

	public String def_rant;

	public String def_shpalt;
	
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
	public String getDef_costs() {
		return def_costs;
	}
	public void setDef_costs(String def_costs) {
		this.def_costs = def_costs;
	}
	public String getDef_paint() {
		return def_paint;
	}
	public void setDef_paint(String def_paint) {
		this.def_paint = def_paint;
	}
	public String getDef_rant() {
		return def_rant;
	}
	public void setDef_rant(String def_rant) {
		this.def_rant = def_rant;
	}
	public String getDef_shpalt() {
		return def_shpalt;
	}
	public void setDef_shpalt(String def_shpalt) {
		this.def_shpalt = def_shpalt;
	}
	public String getDef_extra() {
		return def_extra;
	}
	public void setDef_extra(String def_extra) {
		this.def_extra = def_extra;
	}
}
