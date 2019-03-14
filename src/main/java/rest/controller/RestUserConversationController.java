package rest.controller;

import javax.inject.Inject;

import bean.UserDetailsInsertionBean;
import model.User;

public class RestUserConversationController {
	
	@Inject
	private UserDetailsInsertionBean userDetails;
	
	public String startConversation() {
		return userDetails.startConversation();
	}
	
	public void endConversation() {
		userDetails.endConversation();
	}
	
	public User getStatus() {
		return userDetails.getUserData();
	}
	
	public boolean updateStatus(User updatedUser) {
		userDetails.setUserData(updatedUser);
		return true;
	}
	
}
