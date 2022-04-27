package model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="orders")
public class Order extends BaseEntity{
	
	
	@OneToOne(fetch=FetchType.EAGER) //attenzione ad EAGER, va bene in questi casi toy, altrimenti può creare problemi di prestazioni
	private User buyer;
	
	@ManyToMany(fetch = FetchType.EAGER)//attenzione ad EAGER, va bene in questi casi toy, altrimenti può creare problemi di prestazioni
	private Collection<Product> items;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	Order(){
		
	}

	public boolean isOwner(Long id){
		return id.equals(buyer.getId());
	}
	
	public Order(String uuid) {
		super(uuid);
	}
	
	public void copyOrder(Order otherOrder){
		this.items =  otherOrder.getItems();
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Collection<Product> getItems() {
		return items;
	}

	public void setItems(Collection<Product> items) {
		this.items =  items;
	}
	
	public void completeOrder() {
		this.items = new ArrayList<Product>();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}



}
