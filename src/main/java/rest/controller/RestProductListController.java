package rest.controller;

import java.util.List;

import javax.inject.Inject;

import controller.UserSessionBean;
import dao.ProductDao;
import dao.UserDao;
import model.Product;

public class RestProductListController { // � identico a ProductListController
	
	@Inject
	private ProductDao productDao;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private UserSessionBean loggedUser;
	
	private List<Product> products;
	
	public List<Product> getProducts() {
		if(loggedUser == null) {
			return null;
		}
		if(products == null) {
			products = productDao.retrieveAllProducts();
		}
		return products;
	}
	
	public List<Product> getProductsStateless(Long userID) {
		if(userDao.findById(userID) == null) {
			return null;
		}
		else{
			products = productDao.retrieveAllProducts();
		}
		return products;
	}
}
