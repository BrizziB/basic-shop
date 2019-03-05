package controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import bean.UserDetailsInsertionBean;
import bean.UserSessionBean;
import dao.UserDao;
import model.ModelFactory;
import model.User;


@Model
public class UserDetailsController {
	
	@Inject
	private UserSessionBean userSession;
	
	@Inject 
	private UserDao userDao;

	private User userData;
	
	public User getUserData() {
		return userData;
	}
		
	
	public String openDetails() {
		
		return "details-form-second?faces-redirect=true";
	}
	
	public String showInfo() {
		
		return "details-resume?faces-redirect=true";
	}
	
	@PostConstruct
	public void init() {
		userData = userDao.findById(userSession.getUserId());
		System.out.println("loaded user from session");
	}
	
	
	
	

}
