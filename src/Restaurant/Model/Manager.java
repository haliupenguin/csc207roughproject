package Restaurant.Model;

/**
 * Represents a manager at the restaurant.
 *
 * The manager handles the ordering of more ingredients
 * for the Inventory.
 */
public class Manager extends Employee {

    private Inventory inventory;

    /**
     * Creates a new Restaurant.Model.Manager with an ID.
     *
     * @param id the ID of this Restaurant.Model.Manager
     */
    public Manager(int id, Inventory inventory) {
        super(id);
        this.inventory = inventory;
    }

    /**
     * Overrides the default order amount of an ingredient when the ingredient drops
     * below the minimum.
     *
     * Whenever the Inventory alerts a Restaurant.Model.Manager that an ingredient is running low, this new
     * order amount will be ordered.
     *
     * @param ingredient the ingredient whose default order amount will be changed
     * @param number     the new default order amount
     */
    public void overrideDefaultOrderAmount(String ingredient, int number) {
        if (number > 0) {
            inventory.changeDefault(ingredient, number);
        }
    }

    /**
     * Overrides the minimum amount of an ingredient.
     *
     * The minimum amount is the amount that is kept before it must be restocked.
     *
     * @param ingredient the ingredient whose minimum will be changed
     * @param number     the new minimum
     */
    public void overrideMinimumAmount(String ingredient, int number) {
        if (number > 0) {
            inventory.changeMinimum(ingredient, number);
        }
    }

    /**
     * Returns the amount of an ingredient present in the Inventory
     *
     * @param ingredient the ingredient to be checked
     * @return           the amount of the ingredient in the Inventory
     */
    public int getIngredientAmount(String ingredient) {
        return inventory.getInventory().get(ingredient);
    }

    /**
     * Returns the minimum amount of an ingredient
     *
     * @param ingredient the ingredient to be checked
     * @return           the minimum amount of the ingredient
     */
    public int getMinimumAmount(String ingredient) {
        return inventory.getMinimums().get(ingredient);
    }

    /**
     * Returns the default order amount of an ingredient
     *
     * @param ingredient the ingredient to be checked
     * @return           the default order amount
     */
    public int getDefaultOrderAmount(String ingredient) {
        return inventory.getDefaultOrderAmounts().get(ingredient);
    }


    /**
     * Returns the ID number of this Restaurant.Model.Employee.
     *
     * @return the ID number of this employee.
     */
    public int getId() {
        return super.getId();
    }

    /**
     * Returns the Inventory
     *
     * @return the Manager's Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

}
