package controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import bean.UserSessionBean;
import dao.UserDao;
import model.User;
import java.io.Serializable;
import java.util.UUID;


@Named
@ConversationScoped
public class UserDetailsController implements Serializable {
	private static final long serialVersionUID = 82485710L;
	private final static UUID uuid = UUID.randomUUID();

	@Inject
	private Conversation conversation;

	@Inject
	private UserSessionBean userSession;
	
	@Inject 
	private UserDao userDao;

	private User userData;
	

	public Conversation getConversation() {
		return conversation;
	}

	public String startConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
			System.out.println("conversazione iniziata");
			userData = userDao.findById(userSession.getUserId());
			System.out.println("conversation started with: " + getID());
		}
		return "details-form-first?faces-redirect=true";
	}

	public void endConversation(){
		if(!conversation.isTransient()){
			conversation.end();
		}
	}

	public String getID() {
		return uuid.toString();
	}

	public User getUserData() {
		return userData;
	}

	public void setUserData(User convUser) {
		this.userData = convUser;
	}


	public String savePersonalInfo() {
		return "details-form-second?faces-redirect=true";
	}

	public String saveGeoInfo() {
		System.out.println("conversation is up with: " + getID());
		return "details-form-third?faces-redirect=true";
	}

	public String saveFurtherInfoAndSave() {
		System.out.println("closing conversation with: " + getID());
		userDao.updateUser(userSession.getUserId(), userData);
		endConversation();
		return "home?faces-redirect=true";
	}

	public String goToFirst() {
		return "details-form-first?faces-redirect=true";
	}

	public String goToSecond() {
		return "details-form-second?faces-redirect=true";
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
