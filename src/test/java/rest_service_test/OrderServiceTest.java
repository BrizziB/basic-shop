package rest_service_test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.tools.xjc.model.Model;
import dao.UserDao;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.ModelFactory;
import model.Order;
import model.Product;
import model.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rest.controller.RestOrderController;
import rest.service.OrderService;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class OrderServiceTest {
    private final static String baseURL =                                                   //meglio ottenere questo valore dinamicamente
            "/movie-rental-manager/service";

    @BeforeEach
    public void setup() throws IllegalAccessException {
        RestAssured.baseURI = "http://localhost/";                                           //inizializzo la baseURI e la porta per gli endpoint
        RestAssured.port = 8080;
    }

    @Test
    public void getOrderResponse_TestWithRestAssured() {
        Response response = given().pathParam("id", "1")
                .get(baseURL+"/order/get/" + "{id}");           				// ottengo la Response dall'endpoint

        response.then().statusCode(200)                                             // verifico codice di risposta
                .assertThat().body("id", equalTo(1))
                .assertThat().body("buyer.email",
                equalTo("user1@example.com"))                               //asserzioni di Hamcrest
                .assertThat().body("items", hasSize(0));        				//si possono concatenare
    }
    /* risposta in caso di ordine vuoto {buyer, [], id, uuid}
        {
        "buyer": {
            "email": "user1@example.com",
            "password": "pass1",
            "id": 1,
            "uuid": "0af4ad95-2290-42a3-90b3-ec6042616935"
        },
        "items": [],
        "id": 1,
        "uuid": "1c31d85b-fa6d-4f7f-a68b-9990615e8177"
    }*/
    private User user;
    private Order testOrder;
    private Product product1;
    private OrderService orderService;

    private RestOrderController orderController;
    @Test
    public void addProductToOrder_TestWithRestAssured() throws IllegalAccessException {  	
        with().body("{'id': 1}") // viene automaticamente trasformato in JSON
                .given().pathParam("id", "1").request("POST", baseURL+"/order/add/"+ "{id}")
                .then().statusCode(200);


    }

}
