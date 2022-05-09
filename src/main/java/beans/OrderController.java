package beans;

import java.util.List;
import java.util.ArrayList;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


import dao.OrderDao;
import model.Order;
import model.Product;

//@Model // meglio sostituire lo stereotipo con le singole annotazioni (+ chiaro)

@Named
@RequestScoped
public class OrderController{
	

	@Inject
	ProductListController productController;
	
	@Inject
	private OrderDao orderDao;

	@Inject
	private UserSessionBean loggedUser;

	
	
	@Inject
	private ShopStatusBean shopStatus;

	
	public List<Product> retrieveOrderProducts() {
		ArrayList<Product> prods = new ArrayList<>();
		prods.addAll(orderDao.retrieveOrderByUserId(loggedUser.getUserId()).getItems() );
		System.out.println("retrieving Order of user: " + loggedUser.getUserId());

		return prods;
	}

	public String completeOrder() {
		Order currentOrder = orderDao.retrieveOrderByUserId(loggedUser.getUserId());
		currentOrder.completeOrder();
		orderDao.updateOrder(currentOrder);
		shopStatus.getCompletedOrderList().add(currentOrder);
		System.out.println("order completed successfully");
		return "home";

	}
	
	public boolean removeProductFromOrder(Long productID) {
		System.out.println("Deleting product with id: " + productID);
		Order currentOrder = orderDao.retrieveOrderByUserId(loggedUser.getUserId());
		List<Product> products = (List<Product>) currentOrder.getItems();
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
		List<Product> products = retrieveOrderProducts();
		products.add(prod);
		Order currentOrder = orderDao.retrieveOrderByUserId(loggedUser.getUserId());
		currentOrder.setItems(products);
		
		orderDao.updateOrder(currentOrder);
		
		System.out.println("product " + prod.getProductName() + " added to your Order, current Order size: " + products.size());
		return true;
	}
	


}
