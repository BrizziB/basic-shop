package rest.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import bean.UserSessionBean;
import dao.UserDao;
import model.ModelFactory;
import model.User;

@Model
public class RestUserLoginController {

	
	@Inject
	private UserDao userDao;
	
	@Inject
	private UserSessionBean userSession;
	
	private User userData;
	
	public RestUserLoginController() {
		userData = ModelFactory.initializeUser();
	}

	
	public Long login(User loggingUser) {
		User loggedUser = userDao.login(loggingUser);
		if( loggedUser == null ) {
			throw new RuntimeException("User with email \"" + getUserData().getEmail() + "\" not found");
		}
		userSession.setUserId(loggedUser.getId()); // mantiene uno stato tramite il bean userSession
		System.out.println("user session: " + userSession.getUserId() );
		return loggedUser.getId();
	}
	
	public Long loginStateless(User loggingUser) {
		User loggedUser = userDao.login(loggingUser);
		if( loggedUser == null ) {
			return null;
		} // qua invece non viene mantenuto alcuno stato relativo alla sessione dello user
		return loggedUser.getId();
		
	}
	
	
	public Boolean logout() {
		userSession.setUserId(null);
		System.out.println("user logging out");
		return true;
	}
	
	public Boolean logoutStateless(Long userID) { // praticamente non serve..
		System.out.println("user logging out " + userID);
		return true;
	}
	
	public User getUserData() {
		return userData;
	}
	
}
