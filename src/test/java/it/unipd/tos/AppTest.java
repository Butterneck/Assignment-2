////////////////////////////////////////////////////////////////////
// [Filippo] [Pinton] [1187361]
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class AppTest {
    App app = new App();;
    List<MenuItem> orderItems;
    double orderPrice;

    private final MenuItem budino_pinguino = new MenuItem(MenuItem.ItemType.BUDINI, "Pinguino", 5D);
    private final MenuItem gelato_coppaNafta = new MenuItem(MenuItem.ItemType.GELATI, "Coppa Nafta", 3D);
    private final MenuItem bevanda_cocaCola = new MenuItem(MenuItem.ItemType.BEVANDE, "Coca cola", 2.5D);
    private final MenuItem budino_biancaneve = new MenuItem(MenuItem.ItemType.BUDINI, "Biancaneve", 5.5D);
    private final MenuItem gelato_bananaSplit = new MenuItem(MenuItem.ItemType.GELATI, "Banana split", 7D);
    private final User normalUser = new User(1L, "Filippo", "Pinton", 21, "Via Acqua 87");

    @Before
    public void clearElement() {
        orderItems = new ArrayList<>();
        orderPrice = 0D;
    }

    @Test
    public void testTotalPrice() throws TakeAwayBillException {

        orderItems.add(budino_pinguino);
        orderItems.add(gelato_coppaNafta);
        orderItems.add(bevanda_cocaCola);
        orderItems.add(budino_biancaneve);
        orderItems.add(gelato_bananaSplit);

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(23D, orderPrice, 0D);
    }

    @Test(expected = TakeAwayBillException.class) 
    public void testException() throws TakeAwayBillException {
       orderItems = null; 
       orderPrice = app.getOrderPrice(orderItems,normalUser); 
    }

    /*@Test 
    public void testSconto50SulGelatoMenoCaro() throws TakeAwayBillException {
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_bananaSplit);
        orderItems.add(gelato_bananaSplit);
        orderItems.add(bevanda_cocaCola);

        orderPrice = app.getOrderPrice(orderItems,normalUser);  
        assertEquals(27.25D, orderPrice, 0.0D);
    }*/

}