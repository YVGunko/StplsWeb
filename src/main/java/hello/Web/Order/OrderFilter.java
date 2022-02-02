package hello.Web.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hello.Client.Client;
import hello.Division.Division;


public class OrderFilter {
	public String getOrderText() {
		return orderText;
	}

	public void setOrderText(String orderText) {
		this.orderText = orderText;
	}

	public String getNomenklature() {
		return nomenklature;
	}

	public void setNomenklature(String nomenklature) {
		this.nomenklature = nomenklature;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	private Division division ;
	private Client client ;
	private Date dateS = new Date();
	private Date dateE = new Date();
	private String orderText ;
	private String nomenklature ;
	private String customer ;
	
	public String getDateS() {
		return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateS);
	}

	public void setDateS(String dateS) {
		try {
			this.dateS = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(dateS);
		}
		catch(Exception e) {
		}
	}

	public String getDateE() {
		return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateE);
	}

	public void setDateE(String dateE) {
		try {
			this.dateE = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(dateE);
		}
		catch(Exception e) {
		}
	}
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
