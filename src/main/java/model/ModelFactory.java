package model;

import java.util.UUID;

public class ModelFactory {

	private ModelFactory(){
	}
	
	public static User initializeUser() {
		return new User(UUID.randomUUID().toString());
	}
	
	public static User getMockUser() {
		return new User();
	}
	
	public static Product initializeProduct() {
		return new Product(UUID.randomUUID().toString());
	}
	
	public static Order initializeOrder() {
		return new Order(UUID.randomUUID().toString());
	}
}
