import java.io.*;

/**
 * Represents a manager at the restaurant.
 *
 * The manager handles the ordering of more ingredients
 * for the Inventory.
 */
public class Manager extends Employee {

    /**
     * Creates a new Manager with an ID.
     *
     * @param id the ID of this Manager
     */
    public Manager(int id) {
        super(id);
    }

    /**
     * Overrides the default order amount of an ingredient when the ingredient drops
     * below the minimum.
     *
     * Whenever the Inventory alerts a Manager that an ingredient is running low, this new
     * order amount will be ordered.
     *
     * @param ingredient the ingredient whose default order amount will be changed
     * @param number     the new default order amount
     */
    public void overrideDefaultOrderAmount(String ingredient, int number) {
        if (number > 0) {
            Inventory.changeDefault(ingredient, number);
        }
    }

    /**
     * Orders a shipment of an ingredient.
     *
     * This method is called whenever an ingredient is running low in the Inventory. The Manager
     * will order a default amount unless it has been overridden.
     *
     * @param ingredient the ingredient to be ordered
     * @param amount the amount to be ordered
     * @throws IOException if requests.txt cannot be written to
     */
    public static void orderIngredients(String ingredient, int amount) throws IOException {

        try(FileWriter fileWriter = new FileWriter("requests.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println("Request for " + amount + " units of " + ingredient);
        }

    }

    public static String checkInventory() {
        return Inventory.getStringRepresentation();
    }

    /**
     * Returns the ID number of this Employee.
     *
     * @return the ID number of this employee.
     */
    public int getId() {
        return super.getId();
    }
}
