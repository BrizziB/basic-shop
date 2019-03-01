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
	
	public void removeProductFromOrder(Long productID) {
		Order currentOrder = null;
		if(!products.isEmpty()) {
			Product prod = productController.getProducts().stream()
					.filter(product -> product.getId().equals(productID))
					.findFirst()
					.orElse(null);
			products.remove(prod);
			currentOrder = orderDao.retrieveOrderByUserID(loggedUser.getUserId());
			currentOrder.setItems(products);
			orderDao.updateOrder(currentOrder);
		}
	}
	
	public boolean addProductToOrder(Long productID) {
		System.out.println("trying to add product with ID: " + productID + " to order ");
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

}
