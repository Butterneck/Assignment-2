////////////////////////////////////////////////////////////////////
// [Filippo] [Pinton] [1187361]
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.Order;
import it.unipd.tos.model.User;

public class AppTest {
    App app = new App();;
    List<MenuItem> orderItems;
    double orderPrice;
    List<Order> order;

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
        order = new ArrayList<>();
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
        orderPrice = app.getOrderPrice(orderItems, normalUser);
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

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(27.0D, orderPrice, 0.0D);
    }

    @Test
    public void testConSconto10ESconto50() throws TakeAwayBillException {
        for (int i = 0; i < 6; i++) {
            orderItems.add(gelato_bananaSplit);
        }

        for (int i = 0; i < 5; i++) {
            orderItems.add(budino_biancaneve);
        }

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(59.4D, orderPrice, 0.0D);
    }

    @Test
    public void testTotalPriceWithoutElement() throws TakeAwayBillException {
        orderPrice = app.getOrderPrice(orderItems, normalUser);
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

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(22.5D, orderPrice, 0.0D);
    }

    @Test
    public void testSconto10ConOrdineConTotaleMaggiore50SuGelatiEBudini() throws TakeAwayBillException {
        for (int i = 0; i < 5; ++i) {
            orderItems.add(gelato_bananaSplit);
        }

        for (int i = 0; i < 4; ++i) {
            orderItems.add(budino_pinguino);
        }

        orderItems.add(bevanda_cocaCola);

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(51.75D, orderPrice, 0.0D);
    }

    @Test
    public void testSconto10ConOrdineConTotaleMaggiore50NONSuGelatiEBudini() throws TakeAwayBillException {
        for (int i = 0; i < 10; ++i) {
            orderItems.add(bevanda_cocaCola);
        }

        for (int i = 0; i < 4; ++i) {
            orderItems.add(budino_pinguino);
        }

        orderItems.add(gelato_coppaNafta);

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(48.0D, orderPrice, 0.0D);
    }

    @Test
    public void testNoSconto10ConOrdineConTotaleUguale50SuGelatiEBudini() throws TakeAwayBillException {
        for (int i = 0; i < 10; ++i) {
            orderItems.add(budino_pinguino);
        }

        orderItems.add(bevanda_cocaCola);

        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(52.5D, orderPrice, 0.0D);
    }

    @Test(expected = TakeAwayBillException.class)
    public void testOrdiniConPiuDi30Elementi() throws TakeAwayBillException {
        for (int i = 0; i < 31; i++) {
            orderItems.add(gelato_bananaSplit);
        }

        orderPrice = app.getOrderPrice(orderItems, normalUser);
    }

    @Test
    public void testOrdiniInferiori10Euro() throws TakeAwayBillException {
        orderItems.add(gelato_bananaSplit);
        orderPrice = app.getOrderPrice(orderItems, normalUser);
        assertEquals(7.5D, orderPrice, 0.01D);
    }

    @Test
    public void test10OrdiniGratisMinorenniNellOrarioTra18e19() throws TakeAwayBillException {
        orderItems.add(gelato_bananaSplit);

        User user = null;
        Order orderItem = null;
        for (int i = 0; i < 15; i++) {
            user = new User(i, "Pippo", "De Rossi", i + 5, "Via Delle piante n. " + (i + 1));
            orderItem = new Order(orderItems, user, LocalTime.of(18, 30, 0), app.getOrderPrice(orderItems, user));
            order.add(orderItem);
        }
        List<Order> freeOrder = app.getFreeOrders(order);
        assertEquals(10, freeOrder.size());
        for (Order i : freeOrder) {
            assertTrue(i.getUser().getAge() < 18 && !i.getTimeOrder().isBefore(LocalTime.of(18, 0, 0))
                    && !i.getTimeOrder().isAfter(LocalTime.of(19, 0, 0)));
        }
    }

    @Test
    public void test10OrdiniGratisMinorenniNonFraLe18e19() throws TakeAwayBillException {
        orderItems.add(gelato_bananaSplit);

        User user = null;
        Order orderItem = null;
        for (int i = 0; i < 15; i++) {
            user = new User(i, "Pippo", "De Rossi", i + 5, "Via Delle piante n. " + (i + 1));
            orderItem = new Order(orderItems, user, LocalTime.of(9, 30, 0), app.getOrderPrice(orderItems, user));
            order.add(orderItem);
        }

        List<Order> freeOrder = app.getFreeOrders(order);

        assertEquals(0, freeOrder.size());
    }

    @Test(expected = TakeAwayBillException.class)
    public void testEccezioneElementoNulloNellOrdine() throws TakeAwayBillException {
        orderItems.add(budino_pinguino);
        orderItems.add(null);
        orderPrice = app.getOrderPrice(orderItems, normalUser);
    }

}