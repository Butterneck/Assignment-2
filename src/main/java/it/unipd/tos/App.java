////////////////////////////////////////////////////////////////////
// [Filippo] [Pinton] [1187361]
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.List;

import it.unipd.tos.business.TakeAwayBill;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class App implements TakeAwayBill {

    @Override
    public double getOrderPrice(List<MenuItem> itemsOrdered, User user) throws TakeAwayBillException {
        try {
            return itemsOrdered.stream().mapToDouble(s -> s.getPrice()).sum();
        } catch (Exception e) {
            throw new TakeAwayBillException(e.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}