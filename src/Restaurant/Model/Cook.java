//package Restaurant.Model;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
///**
// * Represents a Cook at the restaurant
// *
// * A cook prepares orders, taking ingredients from the Inventory to make the orders
// */
//public class Cook extends Employee {
//
//    private static ArrayList<Order> uncooked = new ArrayList<>();
//    private static ArrayList<Order> cooking = new ArrayList<>();
//    private static ArrayList<Order> cooked = new ArrayList<>();
//
//    /**
//     * Creates a new Cook object
//     *
//     * @param id the ID of the Cook
//     */
//    public Cook(int id) {
//        super(id);
//    }
//
//    /**
//     * Receives an Order from a Server and adds the Order into the list of Orders that have
//     * not started cooking
//     *
//     * @param order the Order that was received
//     */
//    public static void receiveOrder(Order order) {
//        uncooked.add(order);
//        System.out.println("================================");
//        System.out.println("Order Received");
//        System.out.println(order.toString());
//        System.out.println("================================");
//    }
//
//    /**
//     * Registers that this Cook has started to cook the Order given by the orderId
//     *
//     * @param orderId the ID of the Order that has started cooking
//     * @throws IOException if the Inventory cannot reorder ingredients after an ingredient runs low
//     *                     due to the Order being made
//     */
//    public void startCooking(int orderId) throws IOException {
//        Order orderToProcess = null;
//        for (Order order : uncooked) {
//            if (order.getOrderId() == orderId && Inventory.processOrder(order)) {
//                orderToProcess = order;
//            }
//        }
//        if (orderToProcess != null) {
//            System.out.println("================================");
//            System.out.println("Order Cooking");
//            System.out.println(orderToProcess.toString());
//            System.out.println("================================");
//            uncooked.remove(orderToProcess);
//            cooking.add(orderToProcess);
//        }
//    }
//
//    /**
//     * Register that the Order with orderId has been cooked and ready for delivery to the Table
//     *
//     * @param orderId the ID of the Order that is ready for delivery
//     */
//    public void finishCooking(int orderId) {
//        Order orderToProcess = null;
//        for (Order order : cooking) {
//            if (order.getOrderId() == orderId) {
//                orderToProcess = order;
//
//            }
//        }
//        if (orderToProcess != null) {
//            cooking.remove(orderToProcess);
//            cooked.add(orderToProcess);
//            System.out.println("================================");
//            System.out.println("Order Complete, Ready to Deliver");
//            System.out.println(orderToProcess.toString());
//            System.out.println("================================");
//        }
//    }
//
//    /**
//     * Removes an order from the list of cooked Orders ready to be delivered and returns it.
//     *
//     * If no Order exists in the cooked Orders that match the orderId, then null is returned
//     *
//     * @param orderId the ID of the Order that is to be removed and delivered
//     * @return the Order to be delivered, if it exists
//     */
//    public static Order removeOrder(int orderId) {
//        for (Order order : cooked) {
//            if (order.getOrderId() == orderId) {
//                cooked.remove(order);
//                return order;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Returns the ID number of this Employee.
//     *
//     * @return the ID number of this employee.
//     */
//    public int getId() {
//        return super.getId();
//    }
//}
