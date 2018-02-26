import java.io.IOException;
import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> inventory;
    private HashMap<String, Integer> minimums; // the minimum number of each item in the inventory before needing
                                               // to be reordered

    public Inventory() {
        this.inventory = new HashMap<>();;
        this.minimums = new HashMap<>();
    }

    // adds an ingredient to the inventory
    public void addInventoryItem(String ingredient, int supply, int minimum) {
        inventory.put(ingredient, supply);
        minimums.put(ingredient, minimum);
    }

    // Checks if any of the items are in need of reordering
    public void checkInventory() throws IOException{
        for (String ingredient : inventory.keySet()) {
            if (inventory.get(ingredient) < minimums.get(ingredient)) {
                Manager.order(ingredient, 20);
            }
        }
    }

    // Returns false if the MenuItem cannot be made with the current inventory, true otherwise
    public boolean processMenuItem(MenuItem menuItem) {
        HashMap<String, Integer> toProcess = menuItem.getIngredients();
        for (String ingredient : toProcess.keySet()) {
            if (inventory.get(ingredient) < toProcess.get(ingredient)) {
                return false;
            }
        }
        for (String ingredient : toProcess.keySet()) {
            inventory.replace(ingredient, inventory.get(ingredient) - toProcess.get(ingredient));
        }
        return true;
    }

    public String toString() {
        StringBuilder toReturn = new StringBuilder("=== Inventory === \n");
        for (String ingredient : inventory.keySet()) {
            toReturn.append(ingredient);
            toReturn.append("    ");
            toReturn.append(inventory.get(ingredient));
            toReturn.append("\n");
        }
        return new String(toReturn);
    }
}
