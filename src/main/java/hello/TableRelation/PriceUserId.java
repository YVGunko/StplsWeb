package hello.TableRelation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PriceUserId implements Serializable {
	private static final long serialVersionUID = 4176561328808668347L;

		@Column(name = "price_id")
	    private Integer priceId;
	 
	    @Column(name = "user_id")
	    private Long userId;
	    
	    @Column(name = "created_on")
	    private Date createdOn;
	    
	    private PriceUserId() {}
	 
	    public PriceUserId(
	        Integer priceId,
	        Long userId) {
	        this.priceId = priceId;
	        this.userId = userId;
	        this.createdOn = new Date();
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	 
	        if (o == null || getClass() != o.getClass())
	            return false;
	 
	        PriceUserId that = (PriceUserId) o;
	        return Objects.equals(priceId, that.priceId) &&
	               Objects.equals(userId, that.userId);
	    }
	 
	    @Override
	    public int hashCode() {
	        return Objects.hash(priceId, userId);
	    }

		public Integer getPriceId() {
			return priceId;
		}

		public void setPriceId(Integer priceId) {
			this.priceId = priceId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Date getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
		}
}
