package rest.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import bean.UserSessionBean;
import model.Product;


@Path("user")
public class UserService {
	
	@Inject
	private UserSessionBean loggedUser;
	
	
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


}
