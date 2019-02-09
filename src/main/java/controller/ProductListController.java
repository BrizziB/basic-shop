package controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import bean.UserSessionBean;
import dao.ProductDao;
import model.Product;


@Model
public class ProductListController {

	
	@Inject
	private ProductDao productDao;
	
	private List<Product> products;
	
	public List<Product> getProducts() {
		if(products == null) {
			products = productDao.retrieveAllProducts();
		}
		
		return products;
	}
	
}
