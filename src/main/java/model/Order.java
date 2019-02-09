package model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Orders")
public class Order extends BaseEntity{
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User buyer;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Product> items;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	public void copyOrder(Order otherOrder){
		this.buyer = otherOrder.getBuyer();
		this.items =  otherOrder.getItem();
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Collection<Product> getItem() {
		return items;
	}

	public void setItem(ArrayList<Product> items) {
		this.items =  items;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}



}
