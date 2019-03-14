package rest.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import model.User;
import rest.controller.RestUserLoginController;

@Path("log")
public class LoginService {
	
	@Inject 
	private RestUserLoginController restUserLoginController;

	
	@POST
	@Path("/in")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response loginUser(String requestBody) throws Exception{
		Gson gson = new Gson();
		try {
			Long response = restUserLoginController.loginWithState(gson.fromJson(requestBody, User.class));	
			Response resp = Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@DELETE
	@Path("/out")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response logout() throws Exception{
		try {
			Response resp = Response.ok(restUserLoginController.logout(), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
}
