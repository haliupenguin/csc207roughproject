package Restaurant.Model;

import java.util.Observer;
import java.util.Observable;

/**
 * Represents a receiver at the restaurant
 *
 * A receiver receives shipments of ingredients that are added into inventory
 */
public class Receiver extends Employee {

    private Inventory inventory;

    /**
     * Creates a new Restaurant.Model.Receiver object
     *
     * @param id the ID of this Restaurant.Model.Receiver
     */
    public Receiver(int id, Inventory inventory) {
        super(id);
        this.inventory = inventory;
    }

    /**
     * Receives a shipment of an ingredient and adds it into Inventory
     *
     * @param ingredient the ingredient received
     * @param quantity the quantity of the ingredient received
     */
    public void receive(String ingredient, int quantity) {
        inventory.restockIngredient(ingredient, quantity);
    }

    /**
     * Returns the ID number of this Restaurant.Model.Employee.
     *
     * @return the ID number of this employee.
     */
    public int getId() {
        return super.getId();
    }

}
