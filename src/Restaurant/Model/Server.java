package Restaurant.Model;

import java.util.HashMap;

/**
 * Represents a server at a restaurant.
 *
 * Servers take orders, deliver orders and sit tables.
 */
public class Server extends Employee {

    /**
     * Create a new Restaurant.Model.Server with an Id
     *
     * @param id the Id of the Server
     */
    public Server(int id) {
        super(id);
    }

    /**
     * Deliver an Restaurant.Model.Order, and return true. If it does not deliver for whatever reason, return false.
     *
     * @param order the Order to be delivered
     * @return if the Order has been delivered
     */
    public boolean deliver(Order order) {
        if (order.getStatus().equals("Cooked")) {
            order.setStatus("Delivered");
            return true;
        }
        return false;
    }

    /**
     * Sets an Restaurant.Model.Order that has been cooked or delivered back to not acknowledged and return true.
     * If it does not set the status back for whatever reason, return false
     *
     * @param order the Order to be rejected
     * @return if the Order has been rejected
     */
    public boolean handleRejectedOrder(Order order) {
        if (order.getStatus().equals("Cooked") || order.getStatus().equals("Delivered")) {
            order.setStatus("Not Acknowledged");
            return true;
        }
        return false;
    }

    /**
     * Creates a new Restaurant.Model.Order and sends it to the kitchen.
     *
     * @param menuItem the MenuItem of the Order
     * @param table the table that ordered this Order
     * @param customerNumber the customer that ordered this Order
     * @param modifications potential modifications to this Order
     * @return the new Order
     */
    public Order takeOrder(MenuItem menuItem, Table table, int customerNumber, HashMap<String, Integer> modifications) {
        Order newOrder = new Order(menuItem, modifications, table.getNumber(), customerNumber);
        table.addOrder(newOrder);
        return newOrder;
    }

}
