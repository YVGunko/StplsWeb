package hello;

import java.util.List;
import java.util.Map;

import hello.Price.Price;
import hello.Price.PriceColumn;

public class Mail {

    
    private String from;
    private String mailTo;
    private String Bcc;
	private String subject;
	private String costs;
	private String paint;
	private String priceType;
	private Boolean bRant;
	private String rant;
	private Boolean bLiner;
	private String shpalt;
	private String weight;
	private String number_per_box;
	private String date;
	private String sign;
	
	private Price price;
    private List<PriceColumn> pcRows;
    private Map<String, Object> props;

    public Mail() {}

    public String getBcc() {
		return Bcc;
	}

	public void setBcc(String bcc) {
		Bcc = bcc;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getCosts() {
		return costs;
	}

	public void setCosts(String costs) {
		this.costs = costs;
	}

	public String getPaint() {
		return paint;
	}

	public void setPaint(String paint) {
		this.paint = paint;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Boolean getbRant() {
		return bRant;
	}

	public void setbRant(Boolean bRant) {
		this.bRant = bRant;
	}

	public String getRant() {
		return rant;
	}

	public void setRant(String rant) {
		this.rant = rant;
	}

	public Boolean getbLiner() {
		return bLiner;
	}

	public void setbLiner(Boolean bLiner) {
		this.bLiner = bLiner;
	}

	public String getShpalt() {
		return shpalt;
	}

	public void setShpalt(String shpalt) {
		this.shpalt = shpalt;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getNumber_per_box() {
		return number_per_box;
	}

	public void setNumber_per_box(String number_per_box) {
		this.number_per_box = number_per_box;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public List<PriceColumn> getPcRows() {
		return pcRows;
	}

	public void setPcRows(List<PriceColumn> pcRows) {
		this.pcRows = pcRows;
	}

}