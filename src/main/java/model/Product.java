package model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

		
	@Lob
	private String description;
	
	private String productName;
	
	private Double price;
	
	Product(){
		
	}
	
	public Product(String uuid) {
		super(uuid);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
