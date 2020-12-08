package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {

    private final User normalUser= new User(1L,"Filippo","Pinton",21,"Via Acqua 87");

    @Test
    public void testgetId(){
        assertEquals(1L, normalUser.getId());
    }

    @Test
    public void testgetName(){
        assertEquals("Filippo", normalUser.getName());
    }

    @Test
    public void testgetSurname(){
        assertEquals("Pinton", normalUser.getSurname());
    }

    @Test
    public void testgetAge(){
        assertEquals(21, normalUser.getAge());
    }

    @Test
    public void testgetAddress(){
        assertEquals("Via Acqua 87", normalUser.getAddress());
    }

}