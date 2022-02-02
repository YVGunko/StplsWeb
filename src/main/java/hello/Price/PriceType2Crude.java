package hello.Price;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class PriceType2Crude {
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public Crude getCrude() {
		return crude;
	}

	public void setCrude(Crude crude) {
		this.crude = crude;
	}

	public PriceType2Crude() {
		super();
	}

	public PriceType2Crude(@NotNull Integer id, @NotNull PriceType priceType, @NotNull Crude crude) {
		super();
		this.id = id;
		this.priceType = priceType;
		this.crude = crude;
	}

	@Access(AccessType.PROPERTY)
	@Id
	@JsonProperty("id")
	@NotNull
	public Integer id;
	
	@JsonProperty("priceTypeId")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@ColumnDefault("0")
	public PriceType priceType ;
	
	@JsonProperty("crude_id")
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@ColumnDefault("0")
	public Crude crude ;
}
