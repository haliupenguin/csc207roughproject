public class Receiver extends Employee {

    public Receiver(int id, Restaurant restaurant) {
        super(id, restaurant);
    }

    public void receiveShipment(String ingredient, int amount) {
        restaurant.inventory.restock(ingredient, amount);
    }
}
