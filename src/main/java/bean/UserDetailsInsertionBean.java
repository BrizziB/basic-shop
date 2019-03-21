package bean;

import java.io.Serializable;
import java.util.UUID;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import model.ModelFactory;
import model.User;

@ConversationScoped
@Named(value = "userDetails")
public class UserDetailsInsertionBean implements Serializable{
	
	// questa classe è il bean conversation scoped che mantiene lo stato durante la conversazione
	// di inserimento informazioni sulle pagine info-form
	// questo bean è fatto per essere usato dalle view xhtml
	// a livello logico è equivalente ad UserConversationBean, 
	// il bean equivalente ma esposto per le chiamate http
	
	private static final long serialVersionUID = 82485710L;
	
	private UUID uuid = UUID.randomUUID();
	
	private User userData;
	
	@Inject
	private UserSessionBean userSession;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private Conversation conversation;
	
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
	
}
