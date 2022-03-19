package hello.Web.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hello.Client.Client;
import hello.Division.Division;

public class OrderGroupReq {
	public OrderGroupReq(String orderText, String customer, Long sumProd, Long sumBox, int countProdInBox, Date date, String divisionName,
			Division division, Client client) {
		super();
		this.orderText = orderText;
		this.customer = customer;
		this.sumProd = sumProd;
		this.sumBox = sumBox;
		this.sdate = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(date);
		this.date = date;
		this.divisionName = divisionName;
		this.division = division;
		this.client = client;
	}
	private OrderGroupReq() {
		super();
	}

	public String orderText;
	public String customer;
	public Long sumProd;
	public Long sumBox;
	public String sdate;
	public Date date;
	public String divisionName;
	public Division division ;
	public Client client ;
	
	//public String getDateS() {
	//	return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(DateS);
	//}
}
