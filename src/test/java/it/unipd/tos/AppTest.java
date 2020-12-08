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

    @Test 
    public void testSconto50SulGelatoMenoCaro() throws TakeAwayBillException {
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_bananaSplit);
        orderItems.add(gelato_bananaSplit);
        orderItems.add(bevanda_cocaCola);

        orderPrice = app.getOrderPrice(orderItems,normalUser);  
        assertEquals(27.0D, orderPrice, 0.0D);
    }

    @Test 
    public void testConSconto10ESconto50() throws TakeAwayBillException {
        for(int i = 0; i < 6; i++) {
            orderItems.add(gelato_bananaSplit); 
        }

        for(int i = 0; i < 5; i++) {
            orderItems.add(budino_biancaneve); 
        }

        orderPrice = app.getOrderPrice(orderItems,normalUser); 
        assertEquals(59.4D, orderPrice, 0.0D);
    }

    @Test 
    public void testTotalPriceWithoutElement() throws TakeAwayBillException {      
       orderPrice = app.getOrderPrice(orderItems,normalUser); 
       assertEquals(0.0D, orderPrice, 0.0D);
    }

    @Test 
    public void testCon5GelatiQuindiNoSconto() throws TakeAwayBillException {
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta); 
        orderItems.add(gelato_coppaNafta);
        orderItems.add(gelato_coppaNafta);
        orderItems.add(bevanda_cocaCola);
        orderItems.add(budino_pinguino); 

        orderPrice = app.getOrderPrice(orderItems,normalUser); 
        assertEquals(22.5D, orderPrice, 0.0D);
    }

    @Test 
    public void testSconto10ConOrdineConTotaleMaggiore50SuGelatiEBudini() throws TakeAwayBillException {
        for(int i=0; i<5;++i){
            orderItems.add(gelato_bananaSplit);
        }

        for(int i=0;i<4;++i){
            orderItems.add(budino_pinguino);
        }

        orderItems.add(bevanda_cocaCola);

        orderPrice = app.getOrderPrice(orderItems,normalUser); 
        assertEquals(51.75D, orderPrice, 0.0D);
    }

    @Test 
    public void testSconto10ConOrdineConTotaleMaggiore50NONSuGelatiEBudini() throws TakeAwayBillException {
        for(int i=0; i<10;++i){
            orderItems.add(bevanda_cocaCola);
        }

        for(int i=0;i<4;++i){
            orderItems.add(budino_pinguino);
        }

        orderItems.add(gelato_coppaNafta);

        orderPrice = app.getOrderPrice(orderItems,normalUser); 
        assertEquals(48.0D, orderPrice, 0.0D);
    }

    @Test 
    public void testNoSconto10ConOrdineConTotaleUguale50SuGelatiEBudini() throws TakeAwayBillException {
        for(int i=0; i<10;++i){
            orderItems.add(budino_pinguino);
        }

        orderItems.add(bevanda_cocaCola);

        orderPrice = app.getOrderPrice(orderItems,normalUser); 
        assertEquals(52.5D, orderPrice, 0.0D);
    }

    @Test (expected = TakeAwayBillException.class) 
    public void testOrdiniConPiuDi30Elementi() throws TakeAwayBillException {
        for(int i = 0; i < 31; i++) {
            orderItems.add(gelato_bananaSplit); 
        }
        
        orderPrice = app.getOrderPrice(orderItems,normalUser); 
    }

    @Test 
    public void testOrdiniInferiori10Euro() throws TakeAwayBillException {
        orderItems.add(gelato_bananaSplit);
        orderPrice = app.getOrderPrice(orderItems,normalUser); 
        assertEquals(7.5D, orderPrice, 0.01D);
    }

}