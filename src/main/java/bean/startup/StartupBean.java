package bean.startup;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import model.ModelFactory;
import model.Order;
import model.Product;
import model.User;

@Singleton // è un singleton EJB
@Startup
public class StartupBean {
	
	// ripreso molto dallo startupBean di Note-App

	@Inject
	private UserDao userDao;
	
	@Inject
	private ProductDao productDao;
	
	@Inject 
	private OrderDao orderDao;

	
	@PostConstruct
	@Transactional
	public void init(){
		orderDao.deleteOrders();
		userDao.deleteUsers();
		productDao.deleteProducts();
		
		
		System.out.println("initializing database.. ");
		User user1 = createUser("user1@example.com", "pass1");
		User user2 = createUser("user2@example.com", "pass2");
		
		userDao.save(user1);
		userDao.save(user2);
		productDao.save( createProduct(
				"Duck", 
				"A very socially skilled duck", 
				19.99)
				);
		productDao.save( createProduct(
				"Shoes", 
				"A fancy pair of shoes", 
				39.49)
			);
		productDao.save( createProduct(
				"Bike", 
				"The fastest bike you will ever see", 
				189.99)
			);
		productDao.save( createProduct(
				"Good boi", 
				"A very good boi, easily scared by nope ropes - unlimited borks included", 
				99.99)
			);
		productDao.save( createProduct(
				"Pencil", 
				"Great to write and draw, you should try this", 
				0.99)
			);		
		
		Order order1 = createOrder(user1);
		Order order2 = createOrder(user2);
		
		orderDao.addOrder(order1);	
		orderDao.addOrder(order2);
		
	}
	
	private Order createOrder(User buyer) {
		Order order = ModelFactory.initializeOrder();
		order.setBuyer(buyer);
		return order;
	}

	private User createUser(String email, String password) {
		User user = ModelFactory.initializeUser();
		user.setEmail(email);
		user.setPassword(password);
		
		return user;
	}
	
	private Product createProduct(String name, String description, Double price) {
		Product product = ModelFactory.initializeProduct();
		product.setProductName(name);
		product.setPrice(price);
		product.setDescription(description);
		return product;
	}
}
