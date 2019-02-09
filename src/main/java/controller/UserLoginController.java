package controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import bean.UserSessionBean;
import dao.UserDao;
import model.ModelFactory;
import model.User;

@Model
public class UserLoginController {

	@Inject
	private UserSessionBean userSession;
	
	@Inject
	private UserDao userDao;

	private User userData;
	
	public UserLoginController() {
		userData = ModelFactory.initializeUser();
	}
	
	public String login() {
		User loggedUser = userDao.login(getUserData());
		if( loggedUser == null ) {
			throw new RuntimeException("User with email \"" + getUserData().getEmail() + "\" not found");
		}
		
		userSession.setUserId(loggedUser.getId());
		return "home?faces-redirect=true";
	}
	
	public String logout() {
		userSession.setUserId(null);
		return "home?faces-redirect=true";
	}
	
	public User getUserData() {
		return userData;
	}
	
}
