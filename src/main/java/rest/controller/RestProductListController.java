package rest.controller;

import java.util.List;

import javax.inject.Inject;

import bean.UserSessionBean;
import dao.ProductDao;
import model.Product;

public class RestProductListController { // è identico a ProductListController
	
	@Inject
	private ProductDao productDao;
	
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
}
