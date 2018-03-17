import java.util.HashMap;

/**
 * Represents a receiver at the restaurant
 *
 * A receiver receives shipments of ingredients that are added into inventory
 */
public class Receiver extends Employee {

    /**
     * Creates a new Receiver object
     *
     * @param id the ID of this Receiver
     */
    public Receiver(int id) {
        super(id);
    }

    /**
     * Receives a shipment of an ingredient and adds it into Inventory
     *
     * @param ingredient the ingredient received
     * @param quantity the quantity of the ingredient receieved
     */
    public void receive(String ingredient, int quantity) {
        Inventory.restockIngredient(ingredient, quantity);
    }

    /**
     * Returns the ID number of this Employee.
     *
     * @return the ID number of this employee.
     */
    public int getId() {
        return super.getId();
    }
}
