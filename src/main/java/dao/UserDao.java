package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


import model.User;

public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	public void save(User user) {
		em.persist(user);
	}
	
	public List<User> retrieveAllUsers(){
		TypedQuery<User> query = em.createQuery("from User ", User.class);
		List<User> tmp = query.getResultList();
		return tmp;
	}
	
	public User findById(Long userId) {
		return em.find(User.class, userId);
	}
	
	@Transactional
	public void deleteUsers(){
		try{
			Query query = em.createQuery(
				"DELETE FROM User");
			query.executeUpdate();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}	
	
	public User login(User user) {
		List<User> result = em
					.createQuery("from User "
								+ "where email = :email "
								+ "and password = :pass", User.class)
					.setParameter("email", user.getEmail())
					.setParameter("pass", user.getPassword())
					.setMaxResults(1)
					.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}

}
