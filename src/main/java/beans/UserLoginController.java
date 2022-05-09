package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import model.ModelFactory;
import model.User;

@Named
@RequestScoped
public class UserLoginController {

	private User userData;
	@Inject	private UserSessionBean userSession;
	
	public String login() {	
		User loggedUser = userDao.login(userData);
		if (loggedUser == null) {
			throw new RuntimeException("...");
		}
		userSession.setUserId(loggedUser.getId());
		shopStatus.getActiveUsers().add(loggedUser);
		return "home";
	}
	
	@Inject
	private UserDao userDao;
	@Inject
	private ShopStatusBean shopStatus;
	
	//... 
	public User getUserData() {
		return userData;
	}
	// ...

	public UserLoginController() {
		userData = ModelFactory.initializeUser();
	}

	
// ...
	
	public String logout() {
		User loggingOutUser = userDao.findById(userSession.getUserId());
		shopStatus.getActiveUsers().remove(loggingOutUser);
		userSession.setUserId(null);
		return "home";
	}


	public void setUserData(User userData) {
		this.userData = userData;
	}
}

