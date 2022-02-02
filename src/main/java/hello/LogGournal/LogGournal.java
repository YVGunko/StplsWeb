package hello.LogGournal;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Device.Device;
import hello.User.User;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity

public class LogGournal {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public int getRecordsAffected() {
		return recordsAffected;
	}

	public void setRecordsAffected(int recordsAffected) {
		this.recordsAffected = recordsAffected;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public LogGournal(Long id, User user, Device device, Date dt,
			int recordsAffected, String source) {
		super();
		this.id = id;
		this.user = user;
		this.device = device;
		this.dt = dt;
		this.recordsAffected = recordsAffected;
		this.source = source;
	}
	public LogGournal() {
		super();

	}

	@Id
	@JsonProperty("id")
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	public Long id;
	
	@JsonProperty("idUser")
	@NotNull
	//@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.MERGE)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public User user ;
	
	@JsonProperty("idDevice")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@ColumnDefault("0")
	public Device device ;
	
	@JsonProperty("dt")
	@NotNull
	@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Moscow")
	@Column(name = "dt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date dt = new Date();
	
	@JsonProperty("recordsAffected")
	public int recordsAffected;
	
	@JsonProperty("source")
	public String source;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
