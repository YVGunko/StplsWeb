package hello.Price;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Pt2CrudeRoot {
	public Pt2CrudeRoot(Integer id, Date dateOfChange) {
		super();
		this.id = id;
		this.dateOfChange = dateOfChange;
	}
	public Pt2CrudeRoot() {
		super();
	}

	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("id")
	public Integer id;
	
	@JsonProperty("dateOfChange")
	@JsonFormat(pattern="dd.MM.yyyy",timezone="Europe/Moscow")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dateOfChange", columnDefinition="DATE")
	public Date dateOfChange;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOfChange() {
		return dateOfChange;
	}

	public void setDateOfChange(Date dateOfChange) {
		this.dateOfChange = dateOfChange;
	}
}
