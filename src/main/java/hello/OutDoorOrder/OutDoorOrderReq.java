package hello.OutDoorOrder;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OutDoorOrderReq {
	@JsonProperty("id")
	public String id;
	
	@JsonProperty("comment")
	public String comment;
	
	@JsonProperty("client_id")
	public String client_id ;
	
	@JsonProperty("client_id1c")
	public String client_id1c;
	
	@JsonProperty("client_name")
	public String client_name ;
	@JsonProperty("client_phone")
	public String client_phone ;
	@JsonProperty("client_email")
	public String client_email ;

	@JsonProperty("division_code")
	public String division_code ;
	
	@JsonProperty("division_name")
	public String division_name ;
	
	@JsonProperty("user_id")
	public String user_id ;
	
	@JsonProperty("sDate")
	public String sDate ;
	
	@JsonProperty("sample")
	Boolean sample;
	
	@JsonProperty("date")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date date ;
	
	
	public OutDoorOrderReq(String id, String comment, Date date, String sDate, 
			String division_code, String division_name, String user_id, String client_id, Boolean sample) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.comment = comment;
		this.date = date;
		this.sDate = sDate;
		this.division_code = division_code;
		this.division_name = division_name;
		this.client_id = client_id;
		this.sample = sample;
	}
	
	public OutDoorOrderReq(String id, String comment, Date date, String sDate, 
			String division_code, String user_id, String client_id, String client_name, String client_id1c,
			Boolean sample) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.comment = comment;
		this.date = date;
		this.sDate = sDate;
		this.division_code = division_code;
		this.client_id = client_id;
		this.client_name = client_name;
		this.client_id1c = client_id1c;
		this.sample = sample;
	}
	
	public OutDoorOrderReq(String id, String comment, Date date, String sDate, 
			String division_code, String division_name, String user_id, 
			String client_id, String client_name,String client_phone,String client_email, 
			Boolean sample) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.comment = comment;
		this.date = date;
		this.sDate = sDate;
		this.division_code = division_code;
		this.division_name = division_name;
		this.client_id = client_id;
		this.client_name = client_name;
		this.client_phone = client_phone;
		this.client_email = client_email;
		this.sample = sample;
	}
	
	public Boolean getSample() {
		return sample;
	}

	public void setSample(Boolean sample) {
		this.sample = sample;
	}

	public String getDivision_name() {
		return division_name;
	}

	public void setDivision_name(String division_name) {
		this.division_name = division_name;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getDivision_code() {
		return division_code;
	}
	public void setDivision_code(String division_code) {
		this.division_code = division_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_phone() {
		return client_phone;
	}

	public void setClient_phone(String client_phone) {
		this.client_phone = client_phone;
	}

	public String getClient_email() {
		return client_email;
	}

	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}

	public OutDoorOrderReq() {
		super();
	}
}
