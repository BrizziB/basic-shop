package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.ProductDao;
import model.Product;

@Named
//@SessionScoped 
@RequestScoped
public class ProductListController implements Serializable {

	private static final long serialVersionUID = 12345710L;
	 
	@Inject
	private ProductDao productDao;

	@Inject
	private OrderController orderController;
	private List<Product> products;
	private List<Long> justBoughtProducts;


	public String addProductToOrder(Long productID){
		addToJustBought(productID);
		orderController.addProductToOrder(productID);
		return "";
	}

	public void addToJustBought(Long productID){
		this.justBoughtProducts.add(productID);
	}
	
	public boolean isProductJustBought(Long productID){
		return justBoughtProducts.contains(productID);
	}

	public List<Product> getProducts() {
		if(products == null) {
			products = productDao.retrieveAllProducts();
		}
		return products;
	}
	
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setJustBoughtProducts(List<Long> justBoughtProducts) {
		this.justBoughtProducts = justBoughtProducts;
	}

	public List<Long> getJustBoughtProducts() {
		return justBoughtProducts;
	}
	
	@PostConstruct
	private void init(){
		if(justBoughtProducts == null || justBoughtProducts.size()==0)
			justBoughtProducts = new ArrayList<>();
	}
}
