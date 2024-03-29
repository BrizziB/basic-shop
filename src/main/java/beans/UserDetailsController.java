package beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import dao.UserDao;
import model.User;
import java.io.Serializable;
import java.util.UUID;


@Named
@ConversationScoped
public class UserDetailsController implements Serializable {
	private User userData;
	@Inject	private Conversation conversation;

	@Inject	private UserSessionBean userSession;
	
	public String startConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
			userData = userDao.findById(userSession.getUserId());
		}
		return "details-form-first?faces-redirect=true";
	}

	public void endConversation(){
		if(!conversation.isTransient()){
			conversation.end();
		}
	}
	
	
	
	
	@Inject 
	private UserDao userDao;

	
	private static final long serialVersionUID = 82485710L;
	private final static UUID uuid = UUID.randomUUID();


	public Conversation getConversation() {
		return conversation;
	}

	

	public String getID() {
		return conversation.getId().toString();
	}

	public User getUserData() {
		return userData;
	}

	public void setUserData(User convUser) {
		this.userData = convUser;
	}


	public String goToGeographicalInfo() {
		System.out.println("conversation is up with: " + getID());
		return "details-form-second?faces-redirect=true";
	}

	public String goToFurtherInfo() {
		System.out.println("conversation is up with: " + getID());
		return "details-form-third?faces-redirect=true";
	}

	public String goHomeAndSave() {
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
