package controller_test;


import controller.OrderController;
import dao.OrderDao;
import model.ModelFactory;
import model.Order;
import model.Product;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController; //sotto test
    private OrderDao orderDao;
    private Order fakeOrder;
    private Product product1;
    private Product product2;
    @BeforeEach
    public void setup() throws IllegalAccessException {
        orderController = new OrderController();

        orderDao = mock(OrderDao.class);

        fakeOrder = ModelFactory.initializeOrder();
        product1 = ModelFactory.initializeProduct();
        product2 = ModelFactory.initializeProduct();

        FieldUtils.writeField(orderController,
                "orderDao", orderDao, true);
    }



    @Test
    public void testRetrieveOrderProducts(){
        List<Product> orderProducts =
                new ArrayList<Product>(2);
        orderProducts.add(product1);
        orderProducts.add(product2);

        fakeOrder.setItems(orderProducts);                                              //preparo l'oggetto order con 2 Products
        when(orderDao.retrieveOrderByUserId(1L)).
                thenReturn(fakeOrder);

        List<Product> testProductList =
                orderController.retrieveOrderProducts();                                // eseguo il metodo sotto test
        assertEquals( 2, testProductList.size());                               // valuto i risultati tramite asserzioni
        assertEquals( product1, testProductList.get(0));                                // sono i due prodotti che ho inserito
        assertEquals( product2, testProductList.get(1));                                // nel corretto ordine
    }

    @Test
    public void testCompleteOrder(){
        // un modo per testare questo pu� essere di aggiungere almeno un prodotto a fakeOrder
        // e verificare che, chiamando il metodo completeOrder(), fakeOrder viene svuotato dal prodotto
    }
}
