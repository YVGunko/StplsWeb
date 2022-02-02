package hello.PartBox;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.BoxMoves.BoxMove;
import hello.Department.Department;
import hello.Employee.Employee;
import hello.OutDoc.OutDoc;

@Table(indexes= {@Index(name = "boxMoveAndsentToMasterDateIndex", columnList="boxMove,sentToMasterDate", unique = false)})

@Entity
public class PartBox{
	@JsonProperty("_id")
	@Id
	@NotNull
	String id;
	
	@JsonProperty("_Id_bm")	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn( name="boxMove" )
	BoxMove boxMove;
	
	@JsonProperty("_Id_d")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn( name="department" )
	Department department;
	
	@JsonProperty("_Id_s")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	Employee employee;
	
	@JsonProperty("_RQ_box")
	@NotNull
	int quantity;
	
	@JsonProperty("_P_date")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@JoinColumn( name="date" )
	Date date;
	
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date receivedFromMobileDate;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date sentToMasterDate;
	
	@JsonProperty("_idOutDocs")	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn( name="OutDoc" )
	OutDoc outDoc;

	public OutDoc getOutDoc() {
		return outDoc;
	}
	public void setOutDoc(OutDoc outDoc) {
		this.outDoc = outDoc;
	}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BoxMove getBoxMove() {
		return boxMove;
	}
	public void setBoxMove(BoxMove boxMoves) {
		this.boxMove = boxMoves;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		if (date==null) this.date = new Date();
		else this.date = date;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Department getDepartment() {
		return department;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Employee getEmployee() {
		return employee;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public PartBox(String id, BoxMove boxMove, Department department, Employee employee, int quantity, Date date, Date receivedFromMobileDate, OutDoc outDoc) {
		super();
		this.id = id;
		this.boxMove = boxMove;
		this.department = department;
		this.employee = employee;
		this.setQuantity(quantity);
		this.setDate(date);
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.outDoc = outDoc;
	}
	public PartBox() {
		super();
	}

}
