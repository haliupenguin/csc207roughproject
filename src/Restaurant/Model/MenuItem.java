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

    public ObjectProperty<HashMap<String, Integer>> ingredientsNeededProperty() {
        return ingredientsNeeded;
    }

    public void setIngredientsNeeded(HashMap<String, Integer> ingredientsNeeded) {
        this.ingredientsNeeded.set(ingredientsNeeded);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * Creates a MenuItem object
     *
     * @param ingredientsNeeded a HashMap of all the ingredients needed to make this MenuItem
     * @param name              the name of this MenuItem
     * @param price             the price of this MenuItem
     */
    public MenuItem(HashMap<String, Integer>ingredientsNeeded, String name, double price){
        this.ingredientsNeeded = new SimpleObjectProperty<>(ingredientsNeeded);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    /**
     * Returns a HashMap of the ingredients and the quantities needed to make this Order
     *
     * @return the ingredients and quantities needed to make this Order
     */

    /**
     * Returns a String representation of this MenuItem
     *
     * @return a String representing this MenuItem
     */
    @Override
    public String toString() {
//        NumberFormat formatter = new DecimalFormat("#0.00");
//        return name.get() + " " + formatter.format(price.get());
        return name.get();
    }

    public HashMap<String, Integer> getIngredientsNeeded() {
        return ingredientsNeeded.get();
    }

    public String getName() {
        return name.get();
    }

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
        return (o instanceof MenuItem && ((MenuItem) o).getName().equals(name));
    }

    public boolean equals(String s) {
        return name.equals(s);
    }
}
