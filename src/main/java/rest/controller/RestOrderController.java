package rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bean.UserSessionBean;
import controller.ProductListController;
import dao.OrderDao;
import model.Order;
import model.Product;

public class RestOrderController {
	
	@Inject
	private OrderDao orderDao;
	
	@Inject 
	private UserSessionBean loggedUser;
	
	@Inject 
	private ProductListController productController;

	
	public Order getOrderOfUser() {
		return orderDao.retrieveOrderByUserID(loggedUser.getUserId());
	}
	
	public Order getOrderOfUserStateless(long userID) {
		return orderDao.retrieveOrderByUserID(userID);
	}
	
	public List<Product> retrieveOrderProducts() {
		ArrayList<Product> prods = new ArrayList<>();
		prods.addAll(orderDao.retrieveOrderByUserID(loggedUser.getUserId()).getItems() );
		System.out.println("retrieving Order of user: " + loggedUser.getUserId());
		List<Product> products = prods;
				
		return products;
	}
	
	public Boolean addProductToOrderStateless(Long userID, Long productID) {
		System.out.println("trying to add product with ID: " + productID + " to order");
		Product prod = productController.getProducts().stream()
				.filter(product -> product.getId().equals(productID))
				.findFirst()
				.orElse(null);
		List<Product> products = retrieveOrderProducts();
		products.add(prod);
		Order currentOrder = orderDao.retrieveOrderByUserID(userID);
		currentOrder.setItems(products);
		orderDao.updateOrder(currentOrder);
		System.out.println("product " + prod.getProductName() + " added to your Order, current Order size: " + products.size());
		return true;
	}
	
	public Boolean addProductToOrderStateful(Long productID) {
		System.out.println("trying to add product with ID: " + productID + " to order");
		Product prod = productController.getProducts().stream()
				.filter(product -> product.getId().equals(productID))
				.findFirst()
				.orElse(null);
		List<Product> products = retrieveOrderProducts();
		products.add(prod);
		Order currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
		currentOrder.setItems(products);
		orderDao.updateOrder(currentOrder);
		System.out.println("product " + prod.getProductName() + " added to your Order, current Order size: " + products.size());
		return true;
	}
	
	public Boolean removeProductFromOrderStateful(Long productID){
		System.out.println("Deleting product with id: " + productID);
		Order currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
		List<Product> products = (List<Product>) currentOrder.getItems();
		if(! products.isEmpty()) {
			Product prod = products.stream().filter(product -> product.getId().equals(productID))
					.findFirst()
					.orElse(null);
			products.remove(prod);
			currentOrder.setItems(products);
			System.out.println("product removed");
			orderDao.updateOrder(currentOrder);
			return true;
		}
		return false;
	}
	
	public Boolean removeProductFromOrderStateless(Long productID, Long userID){
		System.out.println("Deleting product with id: " + productID);
		Order currentOrder = orderDao.retrieveOrderByUserID(userID);
		List<Product> products = (List<Product>) currentOrder.getItems();
		if(! products.isEmpty()) {
			Product prod = products.stream().filter(product -> product.getId().equals(productID))
					.findFirst()
					.orElse(null);
			products.remove(prod);
			currentOrder.setItems(products);
			System.out.println("product removed");
			orderDao.updateOrder(currentOrder);
			return true;
		}
		return false;
	}
	
	public Boolean completeOrderStateful() {
		Order currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
		currentOrder.completeOrder();
		orderDao.updateOrder(currentOrder);
		System.out.println("order completed successfully");
		return true;
		
	}
	
	public Boolean completeOrderStateless(Long userID) {
		Order currentOrder = orderDao.retrieveOrderByUserID(userID);
		currentOrder.completeOrder();
		orderDao.updateOrder(currentOrder);
		System.out.println("order completed successfully");
		return true;
		
	}
	
}
