package bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class UserSessionBean implements Serializable {

	private static final long serialVersionUID = 238494809L;
	
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public boolean isLoggedIn() {
		return userId != null;
	}
}