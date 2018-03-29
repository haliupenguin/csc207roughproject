package Restaurant.Model;

import Restaurant.MainApplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the restaurant's inventory.
 * Contains all the ingredients available as well as their quantities, minimum quantity
 * and default reorder quantity.
 */
public class Inventory extends Observable {

    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());

    private HashMap<String, Integer> inventory = new HashMap<>();
    private HashMap<String, Integer> minimums = new HashMap<>();
    private HashMap<String, Integer> defaultOrderAmounts = new HashMap<>();

    /**
     * Create a new Restaurant.Model.Inventory.
     */
    public Inventory() {
    }

    /**
     * Returns a Set of String of the ingredients in the inventory
     *
     * @return a Set of the ingredients in the inventory
     */
    public Set<String> getIngredients() {
        return inventory.keySet();
    }

    /**
     * Returns the inventory with each ingredient and its current quantity
     *
     * Method used to update the config file after the program ends
     *
     * @return a HashMap of each ingredient and its quantity
     */
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    /**
     * Returns the minimum amounts required for each ingredient
     *
     * Method used to update the config file after the program ends.
     *
     * @return a HashMap of each ingredients minimum amount
     */
    public HashMap<String, Integer> getMinimums() {
        return minimums;
    }

    /**
     * Returns the default order amounts for each ingredient.
     *
     * Method is used to update the config file after the program ends.
     *
     * @return a HashMap describing each ingredient's default order amount
     */
    public HashMap<String, Integer> getDefaultOrderAmounts() {
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
    public void addIngredient(String ingredient, int quantity, int minimum, int defaultOrderAmount) {
        inventory.put(ingredient, quantity);
        minimums.put(ingredient, minimum);
        defaultOrderAmounts.put(ingredient, defaultOrderAmount);
        setChanged();
        notifyObservers("Added " + quantity + " units of " + ingredient + ". Made its minimum " + minimum + " and" +
                " default order amount " + defaultOrderAmount);
    }

    /**
     * Restocks the ingredient by the quantity received.
     *
     * @param ingredient the ingredient that is being restocked
     * @param quantity   the number of units of the ingredient to be added into the Inventory
     */
    public void restockIngredient(String ingredient, int quantity) {
        inventory.replace(ingredient, inventory.get(ingredient) + quantity);
        setChanged();
        notifyObservers("Restocked " + quantity + " units of " + ingredient);
    }

    /**
     * Checks the Inventory to see if any ingredient has fallen below the minimum amount. If
     * an ingredient is below the minimum amount, then it tells the Restaurant.Model.Manager to order the
     * ingredient.
     */
    private void checkInventory() {
        HashMap<String, Integer> ingredientsNeeded = new HashMap<>();
        for (String ingredient : inventory.keySet()) {
            if (inventory.get(ingredient) < minimums.get(ingredient)) {
                notifyObservers("Ingredient needed " +
                        defaultOrderAmounts.get(ingredient) + "units of " + ingredient);
                ingredientsNeeded.put(ingredient, defaultOrderAmounts.get(ingredient));
            }
        }
        if (!ingredientsNeeded.isEmpty()) {
            Delivery delivery = new Delivery(ingredientsNeeded);
            Delivery.addDelivery(delivery);

            logger.log(Level.INFO, "Shipment requested: \n" + ingredientsNeeded.toString());

            try (FileWriter fileWriter = new FileWriter("requests.txt", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter out = new PrintWriter(bufferedWriter)) {
                out.println("Shipment requested: \n" + ingredientsNeeded.toString());
            } catch (IOException e) {
                e.printStackTrace();
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
     */
    public boolean processOrder(Order order) {
        checkInventory();
        HashMap<String, Integer> needed = order.getIngredientsNeeded();
        for (String ingredient : needed.keySet()) {
            if (inventory.get(ingredient) < needed.get(ingredient)) {
                notifyObservers("Could not process order " + order.getOrderId());
                return false;
            }
        }
        for (String ingredient : needed.keySet()) {
            inventory.replace(ingredient, inventory.get(ingredient) - needed.get(ingredient));
        }
        setChanged();
        notifyObservers("Processed order " + order.getOrderId());
        return true;
    }

    /**
     * Changes the default order amount for an ingredient for when the ingredient falls below
     * the minimum and needs a Restaurant.Model.Manager to make an order for the ingredient.
     *
     * @param ingredient the ingredient whose default order amount is to be changed
     * @param newDefault the new default order amount for the ingredient
     */
    public void changeDefault(String ingredient, int newDefault) {
        defaultOrderAmounts.replace(ingredient, newDefault);
        setChanged();
        notifyObservers("Changed " + ingredient + " default order amount to " + newDefault);
    }

    /**
     * Changes the minimum amount of ingredient that this Restaurant.Model.Inventory can have before
     * a Delivery request is sent out by a Restaurant.Model.Manager.
     *
     * @param ingredient the ingredient whose minimum amount is to be changed
     * @param newMin the new minimum amount for the ingredient
     */
    public void changeMinimum(String ingredient, int newMin) {
        minimums.replace(ingredient, newMin);
        setChanged();
        notifyObservers("Changed " + ingredient + " minimum amount to " + newMin);
    }

}
