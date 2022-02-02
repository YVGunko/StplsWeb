package hello.Web.Order;

import hello.Division.Division;


public class OrdersGroupFilter {
	public String getOrderText() {
		return orderText;
	}

	public void setOrderText(String orderText) {
		this.orderText = orderText;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	private Division division ;
	private String orderText ;
	private String customer ;
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
}
