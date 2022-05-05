package rest_service_test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;


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
    @Test
    public void addProductToOrder_TestWithRestAssured() throws IllegalAccessException {  	
        with().body("{'id': 1}") // viene automaticamente trasformato in JSON
                .given().pathParam("id", "1").request("POST", baseURL+"/order/add/"+ "{id}")
                .then().statusCode(200);


    }

}
