package hello.TableRelation;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import hello.Price.Price;
import hello.User.User;

@Entity(name = "PriceUser")
@Table(name = "price_user")
public class PriceUser {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "priceId")
    private Price price;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;
 
    @Column(name = "created_on")
    private Date createdOn;
    
    @Column(name = "changes")
    private String changes ;
 
    public PriceUser() {}
 
    public PriceUser(User user, String changes) {
        this.user = user;
        this.createdOn = new Date();
        this.changes = changes;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        PriceUser that = (PriceUser) o;
        return Objects.equals(price, that.price) &&
               Objects.equals(user, that.user) &&
               Objects.equals(createdOn, that.createdOn) &&
               Objects.equals(changes, that.changes);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(price, user, createdOn, changes);
    }

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}
}
