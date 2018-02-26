import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Manager extends Employee {

    // HashMap of all ingredients managers do not want the default amount for
    private static HashMap<String, Integer> overrideOrders = new HashMap<>();

    public Manager(int id, Restaurant restaurant) {
        super(id, restaurant);
    }

    public void addIngredientOverride(String ingredient, int newOrderAmount) {
        overrideOrders.put(ingredient, newOrderAmount);
    }

    public static void order(String ingredient, int needed) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("requests.txt")))) {
            if (overrideOrders.keySet().contains(ingredient)) {
                needed = overrideOrders.get(ingredient);
            }
            out.println("Request for " + needed + " units of " + ingredient);
        }
    }

    public String toString() {
        return "Manager, id " + id;
    }

}
