import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents an order made by a customer.
 *
 * An order stores the MenuItem that the customer ordered along with any modifications, the table
 * that the order came from, and whether it has been fulfilled.
 */
public class Order {

    private static int id = 1;

    private HashMap<String, Integer> ingredientsNeeded;
    private String name;
    private double price;
    private boolean delivered = false;
    private int tableNumber;
    private int orderId;

    /**
     * Creates a new Order object
     *
     * @param item the MenuItem that the customer ordered
     * @param modifications any additions or subtractions to the MenuItem
     * @param tableNumber the table number of the customer
     */
    public Order(MenuItem item, HashMap<String, Integer> modifications, int tableNumber) {
        this.name = item.getName();
        this.ingredientsNeeded = new HashMap<>(item.getIngredientsNeeded());
        this.price = item.getPrice();
        this.modifyIngredients(modifications);
        this.tableNumber = tableNumber;
        orderId = id;
        id++;
    }

    /**
     * Modifies the ingredients needed to prepare this order.
     *
     * A modification can either be an addition or a subtraction of an ingredient
     *
     * @param modifications a HashMap containing the ingredients and the new quantity of each
     *                      ingredient desired
     */
    private void modifyIngredients(HashMap<String, Integer> modifications) {
        Set<String> currentIngredients = ingredientsNeeded.keySet();

        for (String ingredient : modifications.keySet()) {
            if (currentIngredients.contains(ingredient)) {
                ingredientsNeeded.replace(ingredient, modifications.get(ingredient));
            } else {
                ingredientsNeeded.put(ingredient, modifications.get(ingredient));
            }
        }
    }

    /**
     * Registers that this order has been delivered and accepted by the customer
     */
    public void deliver() {
        delivered = true;
    }

    /**
     * Returns the ingredients needed to make this Order
     *
     * @return a HashMap containing each ingredient along with the amount of it needed
     */
    public HashMap<String, Integer> getIngredientsNeeded() {
        return ingredientsNeeded;
    }

    /**
     * Returns the name of this Order
     *
     * The name of this order is the name of the MenuItem it contains
     * @return the name of this Order
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of this Order
     *
     * @return the price of this Order
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a boolean stating whether this Order has been delivered to the customer
     *
     * Returns true if this Order has been delivered, false otherwise
     *
     * @return a boolean stating whether this Order has been delivered
     */
    public boolean isDelivered() {
        return delivered;
    }

    /**
     * Returns the ID of this Order
     *
     * @return the Id of this Order
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returns a String representation of this Order
     *
     * The representation contains the order number, the table it belongs to, and the the MenuItem
     * that was ordered with its price
     *
     * @return a String representation of this Order
     */
    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Order #" + orderId + "\n" + "Table #" + tableNumber + "\n" + name + " " + formatter.format(price);
    }
}
