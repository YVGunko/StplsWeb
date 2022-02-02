package hello.OutDoc;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Device.Device;
import hello.Division.Division;
import hello.Operation.Operation;
import hello.User.User;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity

public class OutDoc {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
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
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}

	public OutDoc(String id, Operation operation, int number, String comment, 
			Date date, Date receivedFromMobileDate, Date sentToMasterDate, 
			String division_code, User user, Device device) {
		super();
		this.id = id;
		this.operation = operation;
		this.user = user;
		this.number = number;
		this.comment = comment;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.sentToMasterDate = sentToMasterDate;
		this.division = new Division (division_code,"");
		this.device = device;
	}
	
	public OutDoc(String id, Operation operation, int number, String comment, 
			Date date, Date receivedFromMobileDate, Date sentToMasterDate, 
			String division_code, User user) {
		super();
		this.id = id;
		this.operation = operation;
		this.user = user;
		this.number = number;
		this.comment = comment;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.sentToMasterDate = sentToMasterDate;
		this.division = new Division (division_code,"");
	}
	
	public OutDoc(String id, Operation operation, int number, Date date, String division_code) {
		super();
		this.id = id;
		this.operation = operation;
		this.number = number;
		this.date = date;
		this.division = new Division (division_code,"");
	}
	
	public OutDoc() {
		super();
	}
	@Access(AccessType.PROPERTY)
	@JsonProperty("_id")
	@Id
	@NotNull
	String id;
	
	@JsonProperty("_Id_o")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="operation" )
	private Operation operation;
	
	@JsonProperty("_number")
	@NotNull
	int number;
	
	@JsonProperty("_comment")
	@NotNull
	String comment;
	
	@JsonProperty("_DT")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date date ;
	
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date receivedFromMobileDate;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date sentToMasterDate;
	
	public Date getSentToMasterDate() {
		return sentToMasterDate;
	}
	public void setSentToMasterDate(Date sentToMasterDate) {
		this.sentToMasterDate = sentToMasterDate;
	}
	public Date getReceivedFromMobileDate() {
		return receivedFromMobileDate;
	}
	public void setReceivedFromMobileDate(Date receivedFromMobileDate) {
		this.receivedFromMobileDate = receivedFromMobileDate;
	}
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Division division ;
	
	@JsonProperty("idUser")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public User user ;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonProperty("deviceId")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public Device device ;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
