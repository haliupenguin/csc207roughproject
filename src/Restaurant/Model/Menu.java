package Restaurant.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the Menu of the restaurant
 *
 * The Menu contains all the MenuItems that customers can order
 */
public class Menu {

    private static ObservableList<MenuItem> items = FXCollections.observableArrayList();

    /**
     * Adds an MenuItem to the Menu
     *
     * @param item the MenuItem to be added
     */
    public static void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * Returns all the items in the Menu
     *
     * @return the MenuItems on the Menu
     */
    public static ObservableList<MenuItem> getItems() {
        return items;
    }

    /**
     * Returns a String representation of the Menu
     *
     * The String contains the name of each MenuItem and its price
     *
     * @return a String representation fo the Menu
     */
    public static String menuString() {
        StringBuilder result = new StringBuilder();

        for (MenuItem item : items) {
            result.append(item.toString());
            result.append("\n");
        }

        return result.toString();
    }
}
