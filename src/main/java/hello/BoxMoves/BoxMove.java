package hello.BoxMoves;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Box.Box;
import hello.Operation.Operation;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class BoxMove {
	@Access(AccessType.PROPERTY)
	@JsonProperty("_id")
	@Id
	@NotNull
	private String id;
	
	@JsonProperty("_Id_b")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Box box;
	
	@JsonProperty("_Id_o")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Operation operation;
	
	@JsonProperty("_DT")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	private Date date = new Date();
	
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	private Date receivedFromMobileDate;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	private Date sentToMasterDate;
	
	

	public Date getReceivedFromMobileDate() {
		return receivedFromMobileDate;
	}
	
	public void setReceivedFromMobileDate(Date receivedFromMobileDate) {
		this.receivedFromMobileDate = receivedFromMobileDate;
	}

	public Date getSentToMasterDate() {
		return sentToMasterDate;
	}

	public void setSentToMasterDate(Date sentToMasterDate) {
		this.sentToMasterDate = sentToMasterDate;
	}
	
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
	
	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date dt) {
		if (dt==null) this.date = new Date();
		else this.date = dt;
	}
	
	public BoxMove(String id, Box box, Operation operation, Date date, Date receivedFromMobileDate) {
		super();
		this.id = id;
		this.box = box;
		this.operation = operation;
		this.setDate(date);
		this.receivedFromMobileDate = receivedFromMobileDate;
	}
	
	public BoxMove() {
		super();
	}

	
}
