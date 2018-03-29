package Restaurant.Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Observable;

/**
 * Represents a delivery of ingredients to this restaurant when the stock in the inventory
 * has fallen below the minimum threshold
 *
 */
public class Delivery extends Observable{

    private static ObservableList<Delivery> deliveries = FXCollections.observableArrayList();

    private static int idCounter = 1;

    private IntegerProperty idNumber;
    private BooleanProperty received;

    private ObjectProperty<HashMap<String, Integer>> ingredients;

    /**
     * Initializes a new Delivery object with the ingredients that are being shipped to this
     * restaurant
     *
     * @param ingredients the ingredients that this Delivery contains.
     */
    public Delivery(HashMap<String, Integer> ingredients) {
        this.ingredients = new SimpleObjectProperty<>(ingredients);
        this.idNumber = new SimpleIntegerProperty(idCounter++);
        this.received = new SimpleBooleanProperty(false);
    }

    /**
     * Adds a new Delivery into the ArrayList of deliveries
     *
     * @param delivery the delivery to be added
     */
    public static void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }

    /**
     * Confirms that this Delivery has been received
     */
    public void receive() {
        received.set(true);
    }

    /**
     * Returns a list of all deliveries for this restaurant
     *
     * @return a list of all deliveries
     */
    public static ObservableList<Delivery> getDeliveries() {
        return deliveries;
    }

    /**
     * Returns the IntegerProperty containing the ID of this Delivery
     *
     * @return the ID as an IntegerProperty
     */
    public IntegerProperty idNumberProperty() {
        return idNumber;
    }

    /**
     * Returns whether this Delivery has been received or not.
     *
     * @return whether this Delivery has been received
     */
    public boolean isReceived() {
        return received.get();
    }

    /**
     * Returns a HashMap of the ingredients contained in this Delivery
     *
     * @return a HashMap of the ingredients in this Delivery
     */
    public HashMap<String, Integer> getIngredients() {
        return ingredients.get();
    }

    /**
     * Returns the ID number of this Delivery as an integer
     *
     * @return the ID number of this Delivery as an integer
     */
    public int getIdNumber() {
        return idNumber.get();
    }

    /**
     * Returns a String representation of this Delivery
     *
     * @return a String representation of this Delivery
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(idNumber.get());
        for (String ingredient : ingredients.get().keySet()) {
            result.append("\n");
            result.append(ingredient);
            result.append(": ");
            result.append(ingredients.get().get(ingredient));
        }
        return result.toString();
    }

}
