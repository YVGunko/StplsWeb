package hello.OutDoorOrder;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Client.Client;
import hello.Division.Division;
import hello.User.User;

@Entity
public class OutDoorOrder {

	
	@Access(AccessType.PROPERTY)
	@Id
	@JsonProperty("id")
	public String id;
	
	@JsonProperty("comment")
	String comment;
	
	@JsonProperty("sample")
	Boolean sample;
	
	@JsonProperty("client_id")
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Client client ;
	
	@JsonProperty("division_code")
	@NotNull
	@ManyToOne(optional = false)
	@ColumnDefault("0")
	public Division division ;
	
	@JsonProperty("user_id")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public User user ;
	
	@JsonProperty("date")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date date ;
	
	@JsonProperty("sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date sentToMasterDate;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date receivedFromMobileDate;
	
	public OutDoorOrder(String id, String comment, Date date, Date receivedFromMobileDate, Date sentToMasterDate, 
			Division division, User user, Client client, Boolean sample) {
		super();
		this.id = id;
		this.user = user;
		this.comment = comment;
		this.date = date;
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.sentToMasterDate = sentToMasterDate;
		this.division = division;
		this.client = client;
		this.sample = sample;
	}
	
	public OutDoorOrder(String id, String comment, Date date,  
			String division_code, Long idUser, String clientId, Boolean sample) {
		super();
		this.id = id;
		this.user = new User (idUser, "", "", null, null, date, null);
		this.comment = comment;
		this.date = date;
		this.receivedFromMobileDate = new Date();
		this.division = new Division (division_code,"");
		this.client = new Client (clientId, "", new Date());
		this.sample = sample;
	}
	
	public Boolean getSample() {
		return sample;
	}

	public void setSample(Boolean sample) {
		this.sample = sample;
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
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	
	public OutDoorOrder() {
		super();
	}
}
