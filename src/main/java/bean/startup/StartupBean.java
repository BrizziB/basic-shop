package bean.startup;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;

import dao.UserDao;
import dao.ProductDao;
import model.ModelFactory;
import model.Product;
import model.User;

@Singleton // è un singleton EJB
@Startup
public class StartupBean {

	@Inject
	private UserDao userDao;
	
	@Inject
	private ProductDao productDao;

	
	@PostConstruct
	@Transactional
	public void init(){
		userDao.deleteUsers();
		productDao.deleteProducts();
		
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
				"Fastest bike you will ever see", 
				189.99)
			);
		productDao.save( createProduct(
				"Good boy", 
				"a very good boy, easily scared by nope ropes - unlimited borks included", 
				99.99)
			);
		productDao.save( createProduct(
				"Pencil", 
				"great to write and draw, you should try this", 
				0.99)
			);		
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
