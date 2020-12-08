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

        // ordine nullo
        if (itemsOrdered == null) {
            throw new TakeAwayBillException("L'ordine non è valido");
        }

        // ordine con elementi nulli
        if (itemsOrdered.contains(null)) {
            throw new TakeAwayBillException("Elemento nullo nell'ordine");
        }

        // limite 30 elementi per ordine
        if (itemsOrdered.size() > 30) {
            throw new TakeAwayBillException("Non ci possono essere più di 30 elementi nell'ordine");
        }

        double price = itemsOrdered.stream().mapToDouble(element -> element.getPrice()).sum();

        // Sconto 50%
        List<MenuItem> gelati = itemsOrdered.stream().filter(element -> element.getItemType() == ItemType.GELATI)
                .collect(Collectors.toList());

        if (gelati.size() > 5) {
            price -= 0.5 * gelati.stream().mapToDouble(element -> element.getPrice()).min().getAsDouble();
        }

        // Sconto 10%
        double priceGelatiBudini = itemsOrdered.stream()
                .filter(element -> element.getItemType() == ItemType.BUDINI || element.getItemType() == ItemType.GELATI)
                .mapToDouble(element -> element.getPrice()).sum();

        if (priceGelatiBudini > 50) {
            price *= 0.9D;
        }

        // sovrapprezzo ordini < 10euro
        if (price < 10 && price > 0) {
            price += 0.5;
        }

        return price;
    }

    public static void main(String[] args) {

    }
}