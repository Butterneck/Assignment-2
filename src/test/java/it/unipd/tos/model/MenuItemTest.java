package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MenuItemTest {

    private final MenuItem budino_pinguino = new MenuItem(MenuItem.ItemType.BUDINI, "Pinguino", 5D);
    private final MenuItem gelato_coppaNafta = new MenuItem(MenuItem.ItemType.GELATI, "Coppa Nafta", 3D);
    private final MenuItem bevanda_cocaCola = new MenuItem(MenuItem.ItemType.BEVANDE, "Coca cola", 2.5D);

    @Test
    public void testGetItemType(){
        assertEquals(MenuItem.ItemType.BUDINI, budino_pinguino.getItemType());
        assertEquals(MenuItem.ItemType.GELATI, gelato_coppaNafta.getItemType());
        assertEquals(MenuItem.ItemType.BEVANDE, bevanda_cocaCola.getItemType());
    }

    @Test
    public void testGetName(){
        assertEquals("Pinguino", budino_pinguino.getName());
        assertEquals("Coppa Nafta", gelato_coppaNafta.getName());
        assertEquals("Coca cola", bevanda_cocaCola.getName());
    }

    @Test
    public void testGetPrice(){
        assertEquals(5D, budino_pinguino.getPrice(),0.01D);
        assertEquals(3D, gelato_coppaNafta.getPrice(),0.01D);
        assertEquals(2.5D, bevanda_cocaCola.getPrice(),0.01D);
    }

}