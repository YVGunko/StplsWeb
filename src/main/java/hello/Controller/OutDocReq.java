package hello.Controller;

import java.util.Date;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class OutDocReq {
	public OutDocReq(String id, Long operationId, int number, String comment, Date date, Date receivedFromMobileDate, String division_code, Long user_id) {
		super();
		this.id = id;
		this.operationId = operationId;
		this.user_id = user_id;
		this.number = number;
		this.comment = comment;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.division_code = division_code;
	}
	@SuppressWarnings("unused")
	public OutDocReq() {
		super();
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}	
	@JsonProperty("_id")
	@NotNull
	public String id;
	
	@JsonProperty("_Id_o")
	public Long operationId;
	
	@JsonProperty("_number")
	@NotNull
	public int number;
	
	@JsonProperty("_comment")
	@NotNull
	public String comment;
	
	@JsonProperty("_DT")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date date ;
	
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	public Date receivedFromMobileDate;
	
	@NotNull
	public String division_code ;
	
	@JsonProperty("idUser")
	public Long user_id;
	
	public String getDivision_code() {
		return division_code;
	}
	public void setDivision_code(String division_code) {
		this.division_code = division_code;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getOperationId() {
		return operationId;
	}
	
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getReceivedFromMobileDate() {
		return receivedFromMobileDate;
	}
	public void setReceivedFromMobileDate(Date receivedFromMobileDate) {
		this.receivedFromMobileDate = receivedFromMobileDate;
	}
}
