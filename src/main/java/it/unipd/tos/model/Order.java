////////////////////////////////////////////////////////////////////
// [Filippo] [Pinton] [1187361]
////////////////////////////////////////////////////////////////////
package it.unipd.tos.model;

import java.util.List;
import java.time.LocalTime;

public class Order {

    private List<MenuItem> itemMenu;
    private User user;
    private LocalTime timeOrder;
    private double price;

    public Order(List<MenuItem> itemMenu, User user, LocalTime timeOrder, double price) {
        this.itemMenu = itemMenu;
        this.user = user;
        this.timeOrder = timeOrder;
        this.price = price;
    }

    public List<MenuItem> getItemMenu() {
        return itemMenu;
    }

    public User getUser() {
        return user;
    }

    public LocalTime getTimeOrder() {
        return timeOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setFree(){
        this.price=0;
    }
}