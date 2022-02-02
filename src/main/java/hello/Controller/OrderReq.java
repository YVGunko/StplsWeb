package hello.Controller;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderReq {
	public OrderReq(Long id, String orderId, String orderText, String customer, String nomenklature, 
			String attrib, int countProdInOrder, int countBoxInOrder, int countProdInBox, Date dt, String division_code) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.orderText = orderText;
		this.customer = customer;
		this.nomenklature = nomenklature;
		this.attrib = attrib;
		this.countProdInOrder = countProdInOrder;
		this.countBoxInOrder = countBoxInOrder;
		this.countProdInBox = countProdInBox;
		this.dt = dt;
		this.division_code = division_code;
	}
	@SuppressWarnings("unused")
	private OrderReq() {
		super();
	}

	@Id
	@JsonProperty("_id")
	@NotNull
	public Long id;
	@JsonProperty("_Ord_Id")
	@NotNull
	public String orderId;
	@JsonProperty("_Ord")
	@NotNull
	public String orderText;
	@JsonProperty("_Cust")
	@NotNull
	public String customer;
	@JsonProperty("_Nomen")
	@NotNull
	public String nomenklature;
	@JsonProperty("_Attrib")
	@NotNull
	public String attrib; //dop info
	@JsonProperty("_Q_ord")
	@NotNull
	public int countProdInOrder;
	@JsonProperty("_Q_box")
	@NotNull
	public int countProdInBox;
	@JsonProperty("_N_box")
	@NotNull
	public int countBoxInOrder;	
	@JsonProperty("_DT")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date dt;
	public String division_code;
}
