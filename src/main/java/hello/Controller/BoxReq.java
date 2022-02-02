package hello.Controller;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BoxReq {
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	private Date receivedFromMobileDate;

	public BoxReq(String id, int quantityBox, int numBox, Long masterdataId, Date date, Date receivedFromMobileDate, Boolean archive) {
		super();
		this.id = id;
		this.quantityBox = quantityBox;
		this.numBox = numBox;
		this.masterdataId = masterdataId;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.archive = archive;
	}
	@SuppressWarnings("unused")
	private BoxReq() {
		super();
	}

	@JsonProperty("_id")
	@NotNull
	public String id;
	
	@JsonProperty("_Q_box")
	@NotNull
	public int quantityBox;
	
	@JsonProperty("_N_box")
	@NotNull
	public int numBox;
	
	@JsonProperty("_Id_m")
	@NotNull
	public Long masterdataId;
	
	@JsonProperty("_DT")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date date;
	
	@JsonProperty("_archive")
	@NotNull
	public
	Boolean archive;
}