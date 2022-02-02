package hello.Web.OutDoc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hello.Division.Division;
import hello.Employee.Employee;
import hello.Operation.Operation;
import hello.User.User;

public class Command {
	private Division division ;
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	
	private Operation operation ;
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	private User user ;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	private Employee employee ;
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
	private Date dateS = new Date();
	private Date dateE = new Date();
	public String getDateS() {
		return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateS);
	}
	
	public void setDateS(String dateS) {
		try {
			this.dateS = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(dateS);
		}
		catch(Exception e) {
		}
	}
	
	public String getDateE() {
		return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateE);
	}
	
	public void setDateE(String dateE) {
		try {
			this.dateE = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(dateE);
		}
		catch(Exception e) {
		}
	}
	
	private int number;
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	private int boxNumberSum;
	public int getBoxNumberSum() {
		return boxNumberSum;
	}
	
	public void setBoxNumberSum(int boxNumberSum) {
		this.boxNumberSum = boxNumberSum;
	}
	
	private int quantitySum;
	public int getQuantitySum() {
		return quantitySum;
	}
	public void setQuantitySum(int quantitySum) {
		this.quantitySum = quantitySum;
	}
	
	private String summa;
	public String getSumma() {
		return summa;
	}
	public void setSumma(String summa) {
		this.summa = summa;
	}

}
