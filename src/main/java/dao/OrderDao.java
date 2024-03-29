package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.Order;

public class OrderDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void addOrder(Order o){
			em.persist(o);
	}
	
	@Transactional
	public void updateOrder(Order o){
		long ID = o.getId();
		Order oldOrder = em.find(Order.class, ID);
		oldOrder.copyOrder(o);
	}
	
	public List<Order> retrieveAllOrders(){
		List<Order> tmp = new ArrayList<>();
		try{
			TypedQuery<Order> query = em.createQuery("from Order ", Order.class);
			tmp = query.getResultList();
		} catch (Exception e){
			e.printStackTrace();
		}
		return tmp;
	}

	@Transactional
	public void deleteOrders(){
		try{
			Query query = em.createQuery(
				"DELETE FROM Order");
			query.executeUpdate();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}	
	
	@Transactional
	public int deleteOrderByID(long ID){
		int numDeleted = 0;
		try{
//			em.joinTransaction();
			Query query = em.createQuery(
				"DELETE FROM Order o WHERE o.id = :id")
				.setParameter("id", ID);
			numDeleted = query.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		}
		return numDeleted;
	}	
	@Transactional
	public Order retrieveOrderByUserId(long ID){
		Order retrievedOrder = null;		
		try{
			TypedQuery<Order> query = em.createQuery(
				"from Order o WHERE o.buyer.id = :id"
				,Order.class)
				.setParameter("id", ID);
			retrievedOrder = query.getSingleResult();
		} catch (Exception e){
			e.printStackTrace();
		}
		return retrievedOrder;
	}


}
