package hello.Controller;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartBoxReq {
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	private Date receivedFromMobileDate;

	public PartBoxReq(String id, String boxMovesId, Long departmentId, Long employeeId, int quantity, Date date, Date receivedFromMobileDate, String outDocId) {
		super();
		this.id = id;
		this.boxMovesId = boxMovesId;
		this.departmentId = departmentId;
		this.employeeId = employeeId;
		this.quantity = quantity;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.outDocId = outDocId;
	}
	private PartBoxReq() {
		super();
	}
	@JsonProperty("_id")
	@NotNull
	public String id;
	
	@JsonProperty("_Id_bm")
	@NotNull
	public String boxMovesId;
	
	@JsonProperty("_Id_d")
	@NotNull
	public Long departmentId;
	
	@JsonProperty("_Id_s")
	@NotNull
	public Long employeeId;
	
	@JsonProperty("_RQ_box")
	@NotNull
	public int quantity;
	
	@JsonProperty("_P_date")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date date;
	
	@JsonProperty("_idOutDocs")
	@NotNull
	public String outDocId;
	
}
