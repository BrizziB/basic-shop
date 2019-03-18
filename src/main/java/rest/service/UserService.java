package rest.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import model.User;
import rest.controller.RestUserController;
import rest.controller.RestUserConversationController;


@Path("user")
public class UserService {
		
	@Inject
	private RestUserController userController;

	@Inject
	private RestUserConversationController conversationController;

	
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
			Long userID = userController.checkAuth();
			return Response.ok(gson.toJson(userID), MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/get")
	@Produces( { "application/json", "text/plain" } )
	public Response getUserDataStateful() throws Exception{
		Gson gson = new Gson();
		try {
			User user = userController.getLoggedUser();
			return Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/get/{userID}")
	@Produces( { "application/json", "text/plain" } )
	public Response getUserDataStateless(@PathParam("userID") Long userID) throws Exception{

		Gson gson = new Gson();
		try {
			User user = userController.getLoggedUser(userID);
			return Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/{userID}")
	@Produces( { "application/json", "text/plain" } )
	public Response updateUserStateless(@PathParam("userID") Long userID, String requestBody) throws Exception{

		Gson gson = new Gson();
		try {
			userController.updateUserData(userID, gson.fromJson(requestBody, User.class));
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	/*                                    --- --- ---  
	 * Da qui ci sono servizi per la conversazione, che usano RestUserConversationController
	 * Forse si potrebbe fare una classe a parte, anche se alla fine sono tutti servizi inerenti lo User
	 *                                    --- --- ---  
	 */
	
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/info-conversation/data")
	@Produces( { "application/json", "text/plain" } )
	public Response getUserConversationDataStateful() throws Exception{
		Gson gson = new Gson();
		try {
			Response resp;
			User user;			
			System.out.println("rispondo a richiesta di getUserData");
			user = conversationController.getUser();
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
			return Response.ok(conversationController.checkConversation(), MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			return Response.ok(false, MediaType.APPLICATION_JSON).build();
		}	
	}
	
	@GET
	@Path("/info-conversation/start")
	@Produces(MediaType.TEXT_PLAIN)
	public Response startInfoConversationStateful() { // inizia la nuova conversazione e ritorna il cid relativo
		try {
			String cid = conversationController.startConversation();
			return Response.ok(cid, MediaType.APPLICATION_JSON).build();			
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}

	}

	
	@DELETE
    @Path("/info-conversation/end")
	@Produces(MediaType.TEXT_PLAIN)
    public Response endConversationStateful() {
		boolean end = conversationController.endConversation();
		return Response.ok(end, MediaType.APPLICATION_JSON).build();	

	}
	
	
	@POST // questo credo dovrebbe essere un PUT, TODO modificalo qui e sul client
	@Path("/info-conversation/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateConversationStateful(String requestBody) {
		Gson gson = new Gson();
		try {
			
			if(conversationController.updateStatus(gson.fromJson(requestBody, User.class)) ) {
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
