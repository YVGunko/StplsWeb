package hello.Web.Order;

import java.util.Date;


public class OrderWeb {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getNomenklature() {
		return nomenklature;
	}

	public void setNomenklature(String nomenklature) {
		this.nomenklature = nomenklature;
	}

	public String getAttrib() {
		return attrib;
	}

	public void setAttrib(String attrib) {
		this.attrib = attrib;
	}

	public int getCountProdInOrder() {
		return countProdInOrder;
	}

	public void setCountProdInOrder(int countProdInOrder) {
		this.countProdInOrder = countProdInOrder;
	}

	public int getCountProdInBox() {
		return countProdInBox;
	}

	public void setCountProdInBox(int countProdInBox) {
		this.countProdInBox = countProdInBox;
	}

	public int getCountBoxInOrder() {
		return countBoxInOrder;
	}

	public void setCountBoxInOrder(int countBoxInOrder) {
		this.countBoxInOrder = countBoxInOrder;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDivision_code() {
		return division_code;
	}

	public void setDivision_code(String division_code) {
		this.division_code = division_code;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public Long id;
	public String orderText;
	public String customer;
	public String nomenklature;
	public String attrib; //dop info
	public int countProdInOrder;
	public int countProdInBox;
	public int countBoxInOrder;	
	public Date dt ;
	public Date date1C ;
	public Date getDate1C() {
		return date1C;
	}

	public void setDate1C(Date date1c) {
		date1C = date1c;
	}

	public String sdate ;
	public String divisionName;
	public String division_code ;
	public String clientName;
	public String client_id ;
	
	public int produced_box;
	public int produced_pair;
	public int shiped_box;
	public int shiped_pair;
	public int last_produced_box;
	public int last_produced_pair;
	public int last_shiped_box;
	public int last_shiped_pair;
	
	public OrderWeb(Long id, String orderText, String customer, String nomenklature, String attrib, String sdate, String clientName, String divisionName, int countProdInOrder, int countProdInBox, int countBoxInOrder) {
		super();
		this.id = id;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.countProdInBox = countProdInBox;
		this.countBoxInOrder = countBoxInOrder;
		this.sdate = sdate;
		this.clientName = clientName;
		this.divisionName = divisionName;
	}
	public OrderWeb(Long id, String orderText, String customer, String nomenklature, String attrib, String sdate, String clientName, String divisionName,
			int countProdInOrder, int countBoxInOrder, int produced_box, int produced_pair, int shiped_box, int shiped_pair,
			int last_produced_box, int last_produced_pair, int last_shiped_box, int last_shiped_pair) {
		super();
		this.id = id;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.countBoxInOrder = countBoxInOrder;
		this.sdate = sdate;
		this.clientName = clientName;
		this.divisionName = divisionName;
		this.produced_box = produced_box;
		this.produced_pair = produced_pair;
		this.shiped_box = shiped_box;
		this.shiped_pair = shiped_pair;
		this.last_produced_box = last_produced_box;
		this.last_produced_pair = last_produced_pair;
		this.last_shiped_box = last_shiped_box;
		this.last_shiped_pair = last_shiped_pair;
	}

	public int getLast_produced_box() {
		return last_produced_box;
	}

	public void setLast_produced_box(int last_produced_box) {
		this.last_produced_box = last_produced_box;
	}

	public int getLast_produced_pair() {
		return last_produced_pair;
	}

	public void setLast_produced_pair(int last_produced_pair) {
		this.last_produced_pair = last_produced_pair;
	}

	public int getLast_shiped_box() {
		return last_shiped_box;
	}

	public void setLast_shiped_box(int last_shiped_box) {
		this.last_shiped_box = last_shiped_box;
	}

	public int getLast_shiped_pair() {
		return last_shiped_pair;
	}

	public void setLast_shiped_pair(int last_shiped_pair) {
		this.last_shiped_pair = last_shiped_pair;
	}

	public int getProduced_box() {
		return produced_box;
	}
	public void setProduced_box(int produced_box) {
		this.produced_box = produced_box;
	}
	public int getProduced_pair() {
		return produced_pair;
	}
	public void setProduced_pair(int produced_pair) {
		this.produced_pair = produced_pair;
	}
	public int getShiped_box() {
		return shiped_box;
	}
	public void setShiped_box(int shiped_box) {
		this.shiped_box = shiped_box;
	}
	public int getShiped_pair() {
		return shiped_pair;
	}
	public void setShiped_pair(int shiped_pair) {
		this.shiped_pair = shiped_pair;
	}
}
