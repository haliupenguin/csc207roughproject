import java.util.HashMap;

public class Receiver extends Employee {

    public Receiver(int id, Restaurant restaurant) {
        super(id, restaurant);
    }

    public void receiveShipment(String ingredient, int amount) {
        restaurant.inventory.restock(ingredient, amount);
    }

    public void receiveShipment(HashMap<String, Integer> shipment) {
        for (String ingredient : shipment.keySet()) {
            receiveShipment(ingredient, shipment.get(ingredient));
        }
    }
}
