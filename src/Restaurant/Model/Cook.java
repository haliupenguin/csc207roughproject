package Restaurant.Model;

/**
 * Represents a Cook at a restaurant
 *
 * Cooks move an order along from uncooked to cooking to cooked.
 */
public class Cook extends Employee {

    /**
     * Creates a new Restaurant.Model.Cook with an id.
     *
     * @param id the id of the Cook
     */
    public Cook(int id) {
        super(id);
    }

    /**
     * Indicate an Order is acknowledged by setting its state to Acknowledged
     *
     * @param order the Order to be acknowledged
     */
    public void acknowledge(Order order) {
        order.setStatus("Acknowledged");
    }

    /**
     * Indicate an Order is being cooked by setting its state to Cooking
     *
     * @param order the Order to be cooked
     */
    public void startCooking(Order order) {
        order.setStatus("Cooking");
    }

    /**
     * Indicate an Order has been cooked by setting its status to Cooked
     *
     * @param order the Order that has been cooked
     */
    public void finishCooking(Order order) {
        order.setStatus("Cooked");
    }
}
