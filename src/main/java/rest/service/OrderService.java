package rest.service;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


import model.Order;
import model.Product;
import rest.controller.RestOrderController;

@Path("order")
public class OrderService {

	@Inject
	private RestOrderController restOrderController;
	

	@GET
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response getOrderStateful() throws Exception{
		Gson gson = new Gson();
		try {
			Order order = restOrderController.getOrderOfUser();
			Response resp = Response.ok(gson.toJson(order), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Path("/get/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response getOrderStateless(@PathParam("userID") Long userID) throws Exception{
		Gson gson = new Gson();
		try {
			Order order = restOrderController.getOrderOfUserStateless(userID);
			Response resp = Response.ok(gson.toJson(order), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response addProductToOrderStateful(String requestBody) throws Exception{
		Gson gson = new Gson();
		System.out.println("adding product : " + requestBody);
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = restOrderController.addProductToOrderStateful(productID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT
	@Path("/add/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response addProductToOrderStateless(@PathParam("userID") Long userID, String requestBody) throws Exception{
		Gson gson = new Gson();
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = restOrderController.addProductToOrderStateless(userID, productID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response removeProductFromOrderStateful(String requestBody) throws Exception{
		Gson gson = new Gson();
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = restOrderController.removeProductFromOrderStateful(productID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	
	@PUT // mi sembra adatto il Put perchè alla fine si modifica una risorsa già esistente, DELETE pareva troppo estremo
	@Path("/remove/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response removeProductFromOrderStateless(@PathParam("userID") Long userID, String requestBody) throws Exception{
		Gson gson = new Gson();
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = restOrderController.removeProductFromOrderStateless(productID, userID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT 
	@Path("/complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response completeOrderStateful() throws Exception{
		Gson gson = new Gson();
		try {
			Boolean resp = restOrderController.completeOrderStateful();
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT // mi sembra adatto il Put perchè alla fine si modifica una risorsa già esistente, DELETE pareva troppo estremo
	@Path("/complete/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response completeOrderStateless(@PathParam("userID") Long userID) throws Exception{
		Gson gson = new Gson();
		try {
			Boolean resp = restOrderController.completeOrderStateless(userID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	

	
	
}
