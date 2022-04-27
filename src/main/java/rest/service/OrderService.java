package rest.service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


import model.Order;
import model.Product;
import rest.controller.RestOrderController;

@Path("order")
public class OrderService {

	@Inject
	private RestOrderController orderController;

	@GET
	@Path("/get/{userID}")
	@Consumes("*/*") //solo per test - da non fare
	@Produces( { "application/json", "text/plain" } )
	public Response getOrder(@PathParam("userID") Long userID) throws Exception{
		Gson gson = new Gson();
		try {
			Order order = orderController.getOrderOfUser(userID);
			Response resp = Response.ok(gson.toJson(order), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@POST
	@Path("/add/{userID}")
	@Consumes("*/*") //solo per test - da non fare
	@Produces( { "application/json", "text/plain" } )
	public Response addProductToOrder(@PathParam("userID") Long userID, String requestBody) throws Exception{
		Gson gson = new Gson();
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = orderController.addProductToOrder(userID, productID);
			Response response = Response.ok(gson.toJson(resp)).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}








	// -------------------------------------------------------------------------------------------
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response addProductToOrderStateful(String requestBody) throws Exception{
		Gson gson = new Gson();
		System.out.println("adding product : " + requestBody);
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = orderController.addProductToOrderStateful(productID);
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
			Boolean resp = orderController.removeProductFromOrderStateful(productID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@PUT // mi sembra adatto il Put perch� alla fine si modifica una risorsa gi� esistente, DELETE pareva troppo estremo
	@Path("/remove/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response removeProductFromOrderStateless(@PathParam("userID") Long userID, String requestBody) throws Exception{
		Gson gson = new Gson();
		try {
			Long productID = gson.fromJson(requestBody, Product.class).getId();
			Boolean resp = orderController.removeProductFromOrderStateless(productID, userID);
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
			Boolean resp = orderController.completeOrderStateful();
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@PUT // mi sembra adatto il Put perch� alla fine si modifica una risorsa gi� esistente, DELETE pareva troppo estremo
	@Path("/complete/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response completeOrderStateless(@PathParam("userID") Long userID) throws Exception{
		Gson gson = new Gson();
		try {
			Boolean resp = orderController.completeOrderStateless(userID);
			Response response = Response.ok(gson.toJson(resp), MediaType.APPLICATION_JSON).build();
			return response;
		}catch(Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@GET
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response getOrderStateful() throws Exception{
		Gson gson = new Gson();
		try {
			Order order = orderController.getOrderOfUserStateful();
			Response resp = Response.ok(gson.toJson(order), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {

			return Response.notAcceptable(null).build();
		}
	}



}
