package rest.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import model.Product;
import rest.controller.RestProductListController;

@Path("products")
public class ProductService {
	
	@Inject
	private RestProductListController restProductController;
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response getProducts() throws Exception{
		Gson gson = new Gson();
		try {
			List<Product> products = restProductController.getProducts();
			Response resp = Response.ok(gson.toJson(products), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}
	
	
	//versione stateless
	
	@GET
	@Path("/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { "application/json", "text/plain" } )
	public Response getProductsStateless(@PathParam("userID") Long userID) throws Exception{
		Gson gson = new Gson();
		try {
			List<Product> products = restProductController.getProductsStateless(userID);
			Response resp = Response.ok(gson.toJson(products), MediaType.APPLICATION_JSON).build();
			return resp;
		}catch(Exception e) {
			
			return Response.notAcceptable(null).build();
		}
	}

}
