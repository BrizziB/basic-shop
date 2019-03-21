package rest.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import bean.UserSessionBean;
import dao.UserDao;
import model.ModelFactory;
import model.User;

@ConversationScoped
public class UserConversationBean implements Serializable{
	
	// questa classe è il bean conversation scoped che mantiene
	// lo stato durante la conversazione http col client remoto
	// nella fase di inserimento dati sulle pagine info-form remote
	// a livello logico è equivalente ad UserDetailsInsertionBean, 
	// il bean equivalente ma usato dalle pagine xhtml
	
	private static final long serialVersionUID = 39295900L;
	
	@Inject
	private UserSessionBean userSession;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private Conversation conversation;
	
	private UUID uuid = UUID.randomUUID();
	
	private User userData;
	
	public Conversation getConversation() {
		return conversation;
	}
	public String initConversation() {
		if (conversation.isTransient()) {
	            conversation.begin();
	    		System.out.println("user session: " + userSession.getUserId() );
	    		userData = userDao.findById(userSession.getUserId());
	            System.out.println("conversazione iniziata con uuid: " + uuid );
	            return conversation.getId();
	        }
		return null;
	}
    public void endConversation(){
        if(!conversation.isTransient()){
        	userDao.updateUser(userSession.getUserId(), userData);
        	System.out.println("chiusa conversazione con uuid: " + uuid );
            conversation.end();
        }
    }
    public String getID() {
    	return uuid.toString();
    }
    
    public String getCid() {
    	return conversation.getId();
    }
	
	public User getUserData() {
		return userData;
	}

	public boolean setUserData(User convUser) {
		System.out.println("continua conversazione con uuid: " + uuid );
		this.userData = convUser;
		return true;
	}
	
	
	

}
