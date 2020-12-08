package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class OrderTest {

    private final MenuItem budino_pinguino = new MenuItem(MenuItem.ItemType.BUDINI, "Pinguino", 5D);
    private final MenuItem gelato_coppaNafta = new MenuItem(MenuItem.ItemType.GELATI, "Coppa Nafta", 3D);
    private final MenuItem bevanda_cocaCola = new MenuItem(MenuItem.ItemType.BEVANDE, "Coca cola", 2.5D);
    private final User normalUser= new User(1L,"Filippo","Pinton",21,"Via Acqua 87");
    private final List<MenuItem> ItemOrder=Arrays.asList(budino_pinguino,gelato_coppaNafta,bevanda_cocaCola);
    private final Order order= new Order(ItemOrder,normalUser,LocalTime.of(21, 0, 0),10.5D);

    @Test
    public void testGetItemMenu(){
        assertEquals(ItemOrder, order.getItemMenu());
    }

    @Test
    public void testGetUser(){
        assertEquals(normalUser, order.getUser());
    }

    @Test
    public void testGetTimeOrder(){
        assertEquals(LocalTime.of(21, 0, 0), order.getTimeOrder());
    }

    @Test
    public void testGetPrice(){
        assertEquals(10.5D, order.getPrice(),0.01D);
    }
}