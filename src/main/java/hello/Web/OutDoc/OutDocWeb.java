package hello.Web.OutDoc;

import java.math.BigDecimal;
import java.util.Date;

public class OutDocWeb {
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
	public String getDivision_code() {
		return division_code;
	}
	public void setDivision_code(String division_code) {
		this.division_code = division_code;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public int getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String id;
	public Long operationId;
	public String operationName;
	public int number;
	public String comment;
	public Date date ;
	public String sdate ;
	public String divisionName;
	public String division_code ;
	public Long user_id;
	public String userName;
	public int boxNumber;
	public int quantity;
	public BigDecimal summa;
	public String sSumma;
	public String employeeName;
	public String employee_code ;
	
	public OutDocWeb(Date date, String sdate, String comment, int boxNumber, int quantity) {
		super();
		this.date = date;
		this.sdate = sdate;
		this.comment = comment;
		this.boxNumber = boxNumber;
		this.quantity = quantity;
	}
	public OutDocWeb(Date date, String operationName, String divisionName, String comment, int boxNumber, int quantity) {
		super();
		this.date = date;
		this.operationName = operationName;
		this.divisionName = divisionName;
		this.comment = comment;
		this.boxNumber = boxNumber;
		this.quantity = quantity;
	}
	public OutDocWeb(Date date, String operationName, String divisionName, String userName, int number, int boxNumber, int quantity) {
		super();
		this.date = date;
		this.operationName = operationName;
		this.divisionName = divisionName;
		this.userName = userName;
		this.number = number;
		this.boxNumber = boxNumber;
		this.quantity = quantity;
	}
	public OutDocWeb(String id, String sdate, String operationName, String divisionName, String userName, int number, int boxNumber, int quantity) {
		super();
		this.id = id;
		this.sdate = sdate;
		this.operationName = operationName;
		this.divisionName = divisionName;
		this.userName = userName;
		this.number = number;
		this.boxNumber = boxNumber;
		this.quantity = quantity;
	}
	public OutDocWeb(String id, String sdate, String operationName, String divisionName, String employeeName, int number, int boxNumber, int quantity,
			String sSumma) {
		super();
		this.id = id;
		this.sdate = sdate;
		this.operationName = operationName;
		this.divisionName = divisionName;
		this.employeeName = employeeName;
		this.number = number;
		this.boxNumber = boxNumber;
		this.quantity = quantity;
		this.sSumma = sSumma;
	}
	public OutDocWeb() {
		// TODO Auto-generated constructor stub
	}
	
}
