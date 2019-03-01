package controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import bean.UserSessionBean;
import dao.OrderDao;
import model.Order;
import model.Product;

@Model
public class OrderController {
	
	@Inject
	ProductListController productController;
	
	@Inject
	private OrderDao orderDao;
	
	@Inject
	private UserSessionBean loggedUser;
	
	private List<Product> products = new ArrayList<Product>();
	
	public List<Product> retrieveOrderProducts() {
		ArrayList<Product> prods = new ArrayList<>();
		prods.addAll(orderDao.retrieveOrderByUserID(loggedUser.getUserId()).getItems() );
		System.out.println("retrieving Order of user: " + loggedUser.getUserId());
		products = prods;
				
		return products;
	}
	
	public boolean removeProductFromOrder(Long productID) {
		System.out.println("Deleting product with id: " + productID);
		Order currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
		products = (List<Product>) currentOrder.getItems();
		if(! products.isEmpty()) {
			Product prod = products.stream().filter(product -> product.getId().equals(productID))
					.findFirst()
					.orElse(null);
			products.remove(prod);
			currentOrder.setItems(products);
			System.out.println("product removed");
			orderDao.updateOrder(currentOrder);
		}
		else return false;
		
		return true;
	}
	
	public boolean addProductToOrder(Long productID) {
		System.out.println("trying to add product with ID: " + productID + " to order");
		Product prod = productController.getProducts().stream()
				.filter(product -> product.getId().equals(productID))
				.findFirst()
				.orElse(null);
		products = retrieveOrderProducts();
		products.add(prod);
		Order currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
		currentOrder.setItems(products);
		orderDao.updateOrder(currentOrder);
		System.out.println("product " + prod.getProductName() + " added to your Order, current Order size: " + products.size());
		return true;
	}
	
	public String completeOrder() {
		Order currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
		currentOrder.completeOrder();
		orderDao.updateOrder(currentOrder);
		System.out.println("order completed successfully");
		return "home";
		
	}

}
