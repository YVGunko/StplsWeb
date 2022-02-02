package hello.MasterData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Division.Division;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(indexes= {@Index(name = "indexByNameAndDivision", columnList="name,division_code", unique = true)})
@Entity
public class Product {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Product(String id, String name, String division_code) {
		super();
		this.id = id;
		this.name = name;
		this.division = new Division (division_code,"");
	}
	
	public Product() {
		super();
	}

	public Product(String id, String name, Division division) {
		super();
		this.id = id;
		this.name = name;
		this.division = division;
	}

	@Id
	@NotNull
	@JsonProperty("id")
	public String id;
	
	@JsonProperty("name")
	@NotNull
	public String name;
	
	@NotNull
	@ManyToOne(optional = false)
	public Division division ;
}
