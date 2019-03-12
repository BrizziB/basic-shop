package rest.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import bean.UserSessionBean;
import dao.UserDao;
import model.User;


@Path("user")
public class UserService {
	
	@Inject
	private UserSessionBean loggedUser;
	
	@Inject
	private UserDao userDao;
	
	
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
	@Path("/data")
	@Produces( { "application/json", "text/plain" } )
	/* 
	 * comunica a un eventuale client se la sua sessione è ancora attiva sul server, in tal caso invia nuovamente lo userID, altrimenti null
	 * */
	public Response getUserDataStateful() throws Exception{
		Gson gson = new Gson();
		try {
			Response resp;
			System.out.println("rispondo a richiesta di getUserData");
			User user = userDao.findById(loggedUser.getUserId());
			resp = Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/data/{userID}")
	@Produces( { "application/json", "text/plain" } )
	/* 
	 * comunica a un eventuale client se la sua sessione è ancora attiva sul server, in tal caso invia nuovamente lo userID, altrimenti null
	 * */
	public Response getUserDataStateless(@PathParam("userID") Long userID) throws Exception{
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


}
