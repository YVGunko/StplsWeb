package hello.Box;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.MasterData.MasterData;

@Table(uniqueConstraints=@UniqueConstraint(columnNames={"master_data_id", "numBox"}),indexes=@Index(columnList = "receivedFromMobileDate"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity

public class Box{
	@Access(AccessType.PROPERTY)
	@JsonProperty("_id")
	@Id
	@NotNull
	String id;
	
	@JsonProperty("_Q_box")
	@NotNull
	int quantityBox;
	
	@JsonProperty("_N_box")
	@NotNull
	int numBox;
	
	@JsonProperty("_Id_m")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	MasterData masterData;
	
	@JsonProperty("_DT")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date date = new Date();
	
	@JsonProperty("_sentToMasterDate")
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date receivedFromMobileDate;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	Date sentToMasterDate;
	
	@JsonProperty("_archive")
	@NotNull
	Boolean archive;
	
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

	public MasterData getMasterData() {
		return masterData;
	}

	public void setMasterData(MasterData masterData) {
		this.masterData = masterData;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date dt) {
		if (dt==null) this.date = new Date();
		else this.date = dt;
	}

	public int getQuantityBox() {
		return quantityBox;
	}

	public void setQuantityBox(int quantityBox) {
		this.quantityBox = quantityBox;
	}

	public int getNumBox() {
		return numBox;
	}

	public void setNumBox(int numBox) {
		this.numBox = numBox;
	}
	
	public Boolean getArchive() {
		return archive;
	}
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	public Box(String id, int quantityBox, int numBox, MasterData masterData, Date date, Date receivedFromMobileDate, Date sentToMasterDate, Boolean archive) {
		super();
		this.id = id;
		this.quantityBox = quantityBox;
		this.numBox = numBox;
		this.masterData = masterData;
		this.setDate(date);
		this.receivedFromMobileDate = receivedFromMobileDate;
		this.sentToMasterDate = sentToMasterDate;
		this.archive = archive;
	}
	
	public Box() {
		super();
	}
	public void setArchiveAndSentToMasterDate(boolean archive, Date sentToMasterDate) {
		this.archive = archive;
		this.sentToMasterDate = sentToMasterDate;
	}

	
}
