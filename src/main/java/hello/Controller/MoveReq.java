package hello.Controller;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveReq {
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	private Date receivedFromMobileDate;

	public MoveReq(String id, String boxId, Long operationId, Date date, Date receivedFromMobileDate) {
		super();
		this.id = id;
		this.boxId = boxId;
		this.operationId = operationId;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
	}

	private MoveReq() {
		super();
	}

	@JsonProperty("_id")
	@NotNull
	public String id;
	
	@JsonProperty("_Id_b")
	@NotNull
	public String boxId;
	
	@JsonProperty("_Id_o")
	@NotNull
	public Long operationId;
	
	@JsonProperty("_DT")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date date;
}
