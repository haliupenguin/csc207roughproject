package Restaurant.Model;

import java.util.HashMap;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import javafx.beans.property.*;

/**
 * Represents a food item on the Menu of the restaurant
 *
 * The MenuItem contains the ingredients needed to make the item, its price, and its name
 */
public class MenuItem {

    private final ObjectProperty<HashMap<String, Integer>> ingredientsNeeded;
    private final StringProperty name;
    private final DoubleProperty price;

    /**
     * Creates a MenuItem object
     *
     * @param ingredientsNeeded a HashMap of all the ingredients needed to make this MenuItem
     * @param name              the name of this MenuItem
     * @param price             the price of this MenuItem
     */
    public MenuItem(HashMap<String, Integer> ingredientsNeeded, String name, double price){
        this.ingredientsNeeded = new SimpleObjectProperty<>(ingredientsNeeded);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    /**
     * Returns a String representation of this MenuItem
     *
     * @return a String representing this MenuItem
     */
    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return name.get() + " " + formatter.format(price.get());
    }

    /**
     * Returns a HashMap of String to Integer of the ingredients needed and their quantities needed to make this Order
     *
     * @return a HashMap of String to Integer ingredients needed and their quantities
     */
    public HashMap<String, Integer> getIngredientsNeeded() {
        return ingredientsNeeded.get();
    }

    /**
     * Returns the name of this MenuItem
     *
     * @return the name of this MenuItem
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the price of this MenuItem
     *
     * @return the price of this MenuItem
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Returns whether this MenuItem is equal to another Object
     *
     * It is equal to a String if the MenuItem's name and the String are equal. It is equal
     * to another MenuItem when the two MenuItem's names are equal.
     *
     * @param o the Object to compare this MenuItem against
     * @return true if the given object represents a String or MenuItem equivalent to this MenuItem
     *         false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof MenuItem && ((MenuItem) o).getName().equals(name.get()));
    }
}
