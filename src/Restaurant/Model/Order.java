package Restaurant.Model;

import javafx.beans.property.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

public class Order extends Observable {

    private static int id = 1;

    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty tableNumber;
    private final IntegerProperty orderId;
    private final IntegerProperty customerNum;
    private final ObjectProperty<HashMap<String, Integer>> ingredientsNeeded;
    private final StringProperty status;     // Statuses can be any of: Not acknowledged,
                                             // Acknowledged, Cooking, Cooked, Delivered

    /**
     * Creates a new Restaurant.Model.Order object, which holds a MenuItem, the modifications, the table number of the
     * Order, and the customer number of the Order.
     *
     * @param item the MenuItem of this order
     * @param modifications a HashMap of String to Integer of potential modifications to the order
     * @param tableNumber the table number that this Order is assigned to
     * @param customerNum the customer number that this order is assigned to
     */
    public Order(MenuItem item, HashMap<String, Integer> modifications,
                 int tableNumber, int customerNum) {
        this.name = new SimpleStringProperty(item.getName());
        this.price = new SimpleDoubleProperty(item.getPrice());
        this.tableNumber = new SimpleIntegerProperty(tableNumber);
        this.orderId = new SimpleIntegerProperty(id++);
        this.customerNum = new SimpleIntegerProperty(customerNum);
        this.ingredientsNeeded = new SimpleObjectProperty<>(new HashMap<>(item.getIngredientsNeeded()));
        this.modifyIngredients(modifications);
        this.status = new SimpleStringProperty("Not Acknowledged");
    }

    /**
     * Modifies this order's ingredients needed.
     *
     * A modification contains either additions or subtractions to the ingredients of a MenuItem.
     *
     * @param modifications a HashMap of String to Integer of modifications to the order
     */
    private void modifyIngredients(HashMap<String, Integer> modifications) {
        HashMap<String, Integer> tempIngredientsNeeded = ingredientsNeeded.get();
        Set<String> currentIngredients = tempIngredientsNeeded.keySet();

        for (String ingredient : modifications.keySet()) {
            if (currentIngredients.contains(ingredient)) {
                int new_value = tempIngredientsNeeded.get(ingredient) + modifications.get(ingredient);
                if (new_value < 0) {
                    new_value = 0;
                }
                tempIngredientsNeeded.replace(ingredient, new_value);
            } else if (modifications.get(ingredient) > 0) {
                tempIngredientsNeeded.put(ingredient, modifications.get(ingredient));
            }
        }
        ingredientsNeeded.set(tempIngredientsNeeded);
    }


    /**
     * Returns a String representation of this Restaurant.Model.Order, with its Id, table number, name, and price
     *
     * @return the String representation of this Order
     */
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Order #" + orderId.get() + "\n" + "Table #" + tableNumber.get() + "\n" + name.get() + " " + formatter.format(price.get());
    }

    /**
     * Returns a String representation of this Restaurant.Model.Order, with its Id, name, and price. Used with the bills
     *
     * @return the String representation of this Order used for bills
     */
    public String billStringFormat() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Order #" + orderId.get() + ": " + name.get() + " " + formatter.format(price.get());
    }

    /**
     * Returns the name of this Restaurant.Model.Order as a String
     *
     * @return the name of this Order
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the name of this Restaurant.Model.Order.
     *
     * @param name the new name of this order
     */
    public void setName(String name) {
        this.name.set(name);
        setChanged();
        notifyObservers("Set name to " + name);
    }

    /**
     * Returns the price of this Restaurant.Model.Order
     *
     * @return the price of this order
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Returns the table number that this Restaurant.Model.Order is assigned to
     *
     * @return the table number that this Order is assigned to
     */
    public int getTableNumber() {
        return tableNumber.get();
    }

    /**
     * Returns the Id of this Restaurant.Model.Order
     *
     * @return the Id of this Order
     */
    public int getOrderId() {
        return orderId.get();
    }

    /**
     * Returns the Id of this Restaurant.Model.Order as an IntegerProperty
     *
     * @return the Id of this Order as an IntegerProperty
     */
    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    /**
     * Returns a HashMap of the ingredients needed to their quantities for this Restaurant.Model.Order
     *
     * @return the ingredients needed for this Order
     */
    public HashMap<String, Integer> getIngredientsNeeded() {
        return ingredientsNeeded.get();
    }

    /**
     * Returns the status of this Restaurant.Model.Order
     *
     * @return the status of this Order
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * Returns the status of this Restaurant.Model.Order as a StringProperty
     *
     * @return the status of this Order as a StringProperty
     */
    public StringProperty statusProperty() {
        return status;
    }

    /**
     * Sets this Restaurant.Model.Order's status to newStatus
     *
     * @param newStatus the new status
     */
    public void setStatus(String newStatus) {
        this.status.set(newStatus);
        setChanged();
        notifyObservers("Set status to " + status.get());
    }

    /**
     * Returns the customer number that is assigned to this Restaurant.Model.Order
     *
     * @return the customer number assigned to this Order
     */
    public int getCustomerNum() {
        return customerNum.get();
    }

}
