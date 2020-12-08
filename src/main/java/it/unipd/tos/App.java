////////////////////////////////////////////////////////////////////
// [Filippo] [Pinton] [1187361]
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.List;
import java.util.stream.Collectors;

import it.unipd.tos.business.TakeAwayBill;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;
import it.unipd.tos.model.MenuItem.ItemType;

public class App implements TakeAwayBill {

    @Override
    public double getOrderPrice(List<MenuItem> itemsOrdered, User user) throws TakeAwayBillException {
        try {
            double price = itemsOrdered.stream().mapToDouble(element -> element.getPrice()).sum();

            List<MenuItem> gelati = itemsOrdered.stream().filter(element -> element.getItemType() == ItemType.GELATI)
                    .collect(Collectors.toList());

            if (gelati.size() > 5) {
                price -= 0.5 * gelati.stream().mapToDouble(element -> element.getPrice()).min().getAsDouble();
            }

            return price;
        } catch (Exception e) {
            throw new TakeAwayBillException(e.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}