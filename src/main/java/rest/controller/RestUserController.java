package rest.controller;

import javax.inject.Inject;

import bean.UserSessionBean;
import dao.UserDao;
import model.User;


public class RestUserController {

	@Inject
	private UserDao userDao;

	@Inject
	private UserSessionBean loggedUser;
	
	public Long checkAuth() {
		if(loggedUser.isLoggedIn()) {
			return loggedUser.getUserId();
		} else {
			return null;
		}
	}
	
	public User getLoggedUser() {
		return userDao.findById(loggedUser.getUserId());
	}
	
	public User getLoggedUser(Long userID) {
		return userDao.findById(userID);
	}
	
}
