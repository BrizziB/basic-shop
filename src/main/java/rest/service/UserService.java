package rest.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import bean.UserDetailsInsertionBean;
import bean.UserSessionBean;
import dao.UserDao;
import model.User;
import rest.bean.UserConversationBean;
import rest.controller.RestUserConversationController;


@Path("user")
public class UserService {
	
	@Inject
	private UserSessionBean loggedUser;
	
	@Inject
	private UserDao userDao;

	
	@Inject
	private UserConversationBean userDetails;
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/check-auth")
	@Produces( { "application/json", "text/plain" } )
	/* 
	 * comunica a un eventuale client se la sua sessione è ancora attiva sul server, in tal caso invia nuovamente lo userID, altrimenti null
	 * */
	public Response checkAuth() throws Exception{
		Gson gson = new Gson();
		try {
			Response resp;
			if(loggedUser.isLoggedIn()) {
				resp = Response.ok(gson.toJson(loggedUser.getUserId()), MediaType.APPLICATION_JSON).build();
			} else {
				resp = Response.ok(gson.toJson(null), MediaType.APPLICATION_JSON).build();
			}
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/info-conversation/data")
	@Produces( { "application/json", "text/plain" } )
	public Response getUserDataStateful() throws Exception{
		Gson gson = new Gson();
		try {
			Response resp;
			User user;
			System.out.println("rispondo a richiesta di getUserData");
			if(userDetails.getUserData() != null) {
				user = userDetails.getUserData();
				
			}
			else {
				user = userDao.findById(loggedUser.getUserId());
			}
			resp = Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/info-conversation/data/{userID}")
	@Produces( { "application/json", "text/plain" } )
	/* 
	 * comunica a un eventuale client se la sua sessione è ancora attiva sul server, in tal caso invia nuovamente lo userID, altrimenti null
	 * */
	public Response getUserDataStateless(@PathParam("userID") Long userID) throws Exception{
		//nel caso stateless non posso usare i bean di conversazione !
		Gson gson = new Gson();
		try {
			Response resp;
			User user = userDao.findById(userID);
			resp = Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Path("/info-conversation/check")
	@Produces(MediaType.TEXT_PLAIN)
	public Response checkConversationStatus() {
		try {
			if(userDetails.getCid() != null) {
				return Response.ok(true, MediaType.APPLICATION_JSON).build();
			}
			return Response.ok(false, MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			return Response.ok(false, MediaType.APPLICATION_JSON).build();
		}	
	}
	
	@GET
	@Path("/info-conversation/start")
	@Produces(MediaType.TEXT_PLAIN)
	public Response startInfoConversationStateful() { // inizia la nuova conversazione e ritorna il cid relativo
//		if(loggedUser.getUserId()== null) {
//			return Response.noContent().build();
//		}
		try {
			String cid = userDetails.initConversation();
			return Response.ok(cid, MediaType.APPLICATION_JSON).build();			
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@GET
	@Path("/info-conversation/start/{userID}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response startInfoConversationStateless(@PathParam("userID") Long userID) { // inizia la nuova conversazione e ritorna il cid relativo
//		if(userID == null) {
//			return Response.noContent().build();
//		}
		try {
			String cid = userDetails.initConversation();
			return Response.ok(cid, MediaType.APPLICATION_JSON).build();			
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	
	@DELETE
    @Path("/info-conversation/end")
	@Produces(MediaType.TEXT_PLAIN)
    public Response endConversationStateful() {
		if(this.loggedUser.isLoggedIn()) {
			this.userDetails.endConversation();
			return Response.ok(true, MediaType.APPLICATION_JSON).build();	
		}
		return Response.ok(false, MediaType.APPLICATION_JSON).build();	
	}
	
	@DELETE
    @Path("/info-conversation/end/{userID}")
	@Produces(MediaType.TEXT_PLAIN)
    public Response endConversationStateless(@PathParam("userID") Long userID) {
		if(userID != null) {
			this.userDetails.endConversation();
			return Response.ok(true, MediaType.APPLICATION_JSON).build();	
		}
		return Response.ok(false, MediaType.APPLICATION_JSON).build();	
	}
	
	@POST
	@Path("/info-conversation/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateConversationStateful(String requestBody) {
		Gson gson = new Gson();
		try {
			
			if(this.userDetails.setUserData(gson.fromJson(requestBody, User.class)) ) {
					return Response.ok(true).build();
			}
			else {
				return Response.ok(false).build();
			}
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
