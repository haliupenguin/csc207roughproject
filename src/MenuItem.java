import java.util.HashMap;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Represents a food item on the Menu of the restaurant
 *
 * The MenuItem contains the ingredients needed to make the item, its price, and its name
 */
public class MenuItem {

    private HashMap<String, Integer> ingredientsNeeded;
    private String name;
    private Double price;

    /**
     * Creates a MenuItem object
     *
     * @param ingredientsNeeded a HashMap of all the ingredients needed to make this MenuItem
     * @param name              the name of this MenuItem
     * @param price             the price of this MenuItem
     */
    public MenuItem(HashMap<String, Integer>ingredientsNeeded, String name, double price){
        this.ingredientsNeeded = ingredientsNeeded;
        this.name = name;
        this.price = price;
    }

    /**
     * Returns a HashMap of the ingredients and the quantities needed to make this Order
     *
     * @return the ingredients and quantities needed to make this Order
     */
    public HashMap<String, Integer> getIngredientsNeeded() {
        return ingredientsNeeded;
    }

    /**
     * Returns the name of this MenuItem
     *
     * @return the name of this MenuItem
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of this MenuItem
     *
     * @return the price of this MenuItem
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Returns a String representation of this MenuItem
     *
     * @return a String representing this MenuItem
     */
    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return name + "\n" + formatter.format(price);
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
