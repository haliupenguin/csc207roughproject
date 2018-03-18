package Restaurant.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents the restaurant's inventory.
 * Contains all the ingredients available as well as their quantities, minimum quantity
 * and default reorder quantity.
 */
public class Inventory {

    private static HashMap<String, Integer> inventory = new HashMap<>();
    private static HashMap<String, Integer> minimums = new HashMap<>();
    private static HashMap<String, Integer> defaultOrderAmounts = new HashMap<>();

    public static Set<String> getIngredients() {
        return inventory.keySet();
    }

    /**
     * Returns the inventory with each ingredient and its current quantity
     *
     * Method used to update the config file after the program ends
     *
     * @return a HashMap of each ingredient and its quantity
     */
    public static HashMap<String, Integer> getInventory() {
        return inventory;
    }

    /**
     * Returns the minimum amounts required for each ingredient
     *
     * Method used to update the config file after the program ends.
     *
     * @return a HashMap of each ingredients minimum amount
     */
    public static HashMap<String, Integer> getMinimums() {
        return minimums;
    }

    /**
     * Returns the default order amounts for each ingredient.
     *
     * Method is used to update the config file after the program ends.
     *
     * @return a HashMap describing each ingredient's default order amount
     */
    public static HashMap<String, Integer> getDefaultOrderAmounts() {
        return defaultOrderAmounts;
    }

  /**
   * Adds a new ingredient to the Inventory with the ingredient's name, the initial number,
   * the minimum quantity needed to be kept in inventory, and the default quantity that is ordered
   * by the Manager.
   *
   * @param ingredient the name of the ingredient to be added
   * @param quantity the initial quantity of the ingredient already in the Inventory
   * @param minimum the minimum quantity of the ingredient in Inventory until it needs to be
   *                ordered
   * @param defaultOrderAmount the amount that the Manager will order once the Inventory amount
   *                           falls below the minimum.
   */
    public static void addIngredient(String ingredient, int quantity, int minimum, int defaultOrderAmount) {
        inventory.put(ingredient, quantity);
        minimums.put(ingredient, minimum);
        defaultOrderAmounts.put(ingredient, defaultOrderAmount);
    }

    /**
     * Restocks the ingredient by the quantity received.
     *
     * @param ingredient the ingredient that is being restocked
     * @param quantity   the number of units of the ingredient to be added into the Inventory
     */
    public static void restockIngredient(String ingredient, int quantity) {
        inventory.replace(ingredient, inventory.get(ingredient) + quantity);
    }

    /**
     * Checks the Inventory to see if any ingredient has fallen below the minimum amount. If
     * an ingredient is below the minimum amount, then it tells the Manager to order the
     * ingredient.
     *
     * @throws IOException if requests.txt cannot be written to
     */
    private static void checkInventory() throws IOException {
        for (String ingredient : inventory.keySet()) {
            if (inventory.get(ingredient) < minimums.get(ingredient)) {
                Manager.orderIngredients(ingredient, defaultOrderAmounts.get(ingredient));
            }
        }
    }

    /**
     * Processes a Cook cooking an Order by deducting the ingredients needed to make the Order
     * from the Inventory. Returns a boolean stating whether it is possible to make the Order.
     * If the Order is made, the Inventory checks itself to see if any ingredients need to be
     * restocked
     *
     * If the order cannot be made from the supplies in the Inventory, then false is returned.
     * Otherwise, true is returned.
     *
     * @param order the Order that is taking ingredients from the Inventory
     * @return a boolean stating whether the Order could be made
     * @throws IOException if an ingredient needs to be restocked and requests.txt cannot be
     *                     written to
     */
    public static boolean processOrder(Order order) throws IOException {
        checkInventory();
        HashMap<String, Integer> needed = order.getIngredientsNeeded();
        for (String ingredient : needed.keySet()) {
            if (inventory.get(ingredient) < needed.get(ingredient)) {
                return false;
            }
        }
        for (String ingredient : needed.keySet()) {
            inventory.replace(ingredient, inventory.get(ingredient) - needed.get(ingredient));
        }
        return true;
    }

    /**
     * Changes the default order amount for an ingredient for when the ingredient falls below
     * the minimum and needs a Manager to make an order for the ingredient.
     *
     * @param ingredient the ingredient whose default order amount is to be changed
     * @param newDefault the new default order amount for the ingredient
     */
    public static void changeDefault(String ingredient, int newDefault) {
        defaultOrderAmounts.replace(ingredient, newDefault);
    }

    /**
     * Returns a String representation of the Inventory
     *
     * @return a String representation of the Inventory
     */
    public static String getStringRepresentation() {
        StringBuilder result = new StringBuilder("Inventory Contents:");
        for (String ingredient : inventory.keySet()) {
            result.append("\n");
            result.append(ingredient);
            result.append(": ");
            result.append(inventory.get(ingredient));
        }
        return result.toString();
    }
}
