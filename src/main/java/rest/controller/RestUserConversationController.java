package rest.controller;

import javax.inject.Inject;

import bean.UserSessionBean;
import dao.UserDao;
import model.User;
import rest.bean.UserConversationBean;

public class RestUserConversationController {
	
	@Inject
	private UserSessionBean loggedUser;
	
	@Inject
	private UserConversationBean userDetails;
	
	@Inject
	private UserDao userDao;
	
	
	public String startConversation() {
		if (loggedUser.isLoggedIn()) {
			return userDetails.initConversation();
		}
		else return null;
	}

	
	public boolean endConversation() {
		if (loggedUser.isLoggedIn()) {
			userDetails.endConversation();
			return true;
		}
		else return false;
	}
	
	public User getStatus() {
		if (loggedUser.isLoggedIn()) {
			return userDetails.getUserData();
		}
		return null;
	}
	
	public boolean updateStatus(User updatedUser) {
		if (loggedUser.isLoggedIn()) {
			userDetails.setUserData(updatedUser);
			return true;
		}
		return false;		
	}
	
	public User getUser() {
		User user;
		if(userDetails.getUserData() != null) {
			user = userDetails.getUserData();
			
		}
		else {
			user = userDao.findById(loggedUser.getUserId());
		}
		return user;
	}
	
	public boolean checkConversation() {
		if(userDetails.getCid() != null) {
			return true;
		}
		else return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
