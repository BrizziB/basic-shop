package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.Product;



public class ProductDao{
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void addProduct(Product newProduct){
		em.persist(newProduct);
		
	}
	
	public List<Product> retrieveAllProducts(){
		List<Product> tmp = new ArrayList<>();
		try{
			TypedQuery<Product> query = em.createQuery("from Product ", Product.class);
			tmp = query.getResultList();
		} catch (Exception e){
			// ... 
		}
		return tmp;
	}
	// ...



	@Transactional
	public void deleteProducts(){
		try{			Query query = em.createQuery(
				"DELETE FROM Product");
			query.executeUpdate();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}	
	
	public void save(Product prod) {
		if(prod.getId() != null) {
			em.merge(prod);
		} else {
			em.persist(prod);
		}
	}
	

	
}
