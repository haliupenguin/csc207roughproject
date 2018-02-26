import java.io.IOException;
import java.util.ArrayList;

public class Cook extends Employee {

    private static ArrayList<Order> uncooked = new ArrayList<>(); // All orders sent to the kitchen, but not yet seen
    private static ArrayList<Order> cooking = new ArrayList<>(); // Orders that are seen and are cooking
    private static ArrayList<Order> cooked = new ArrayList<>(); // Finished orders awaiting pickup

    public static void receiveOrder(Order order) {
        uncooked.add(order);
    }

    public void startCooking(Order order) throws IOException{
        if (uncooked.indexOf(order) > -1 && restaurant.inventory.processOrder(order)) {
            order.setState("Seen");
            uncooked.remove(order);
            cooking.add(order);
        }
    }

    public void finishCooking(Order order) {
        if (cooking.indexOf(order) > -1) {
            order.setState("Filled");
            cooking.remove(order);
            System.out.println("ORDER FILLED, AWAITING PICKUP");
            System.out.println(order);
            cooked.add(order);
        }
    }

    public static void removeOrder(Order order) {
        if (cooked.indexOf(order) > -1) {
            cooked.remove(order);
        }
    }

    public Cook(int id, Restaurant restaurant) {
        super(id, restaurant);
    }


}
