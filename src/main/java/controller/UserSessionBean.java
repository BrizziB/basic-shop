package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class UserSessionBean implements Serializable {	
	private Long userId;
	//lista dei prodotti visitati dal login
	private List<Long> browsedProducts;

	
	private static final long serialVersionUID = 23L;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public boolean isLoggedIn() {
		return userId != null;
	}
	public List<Long> getJustBoughtProducts() {
		return browsedProducts;
	}
	public void setJustBoughtProducts(List<Long> browsedProducts) {
		this.browsedProducts = browsedProducts;
	}

}
