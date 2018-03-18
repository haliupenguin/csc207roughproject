//package Restaurant.Model;
//
//import java.util.HashMap;
//
///**
// * Represents a Server at the restaurant
// *
// * A server takes orders from Tables and delivers cooked food to the Table
// */
//public class Server extends Employee {
//
//    /**
//     * Creates a Server object
//     *
//     * @param id the Server's ID
//     */
//    public Server(int id) {
//        super(id);
//    }
//
//    /**
//     * Takes information about an order from a Table and makes it into an Order object that is
//     * sent to the Cook to be made.
//     *
//     * @param menuItem the menuItem object that is ordered by the customer
//     * @param table the Table object where the customer is seated
//     * @param modifications all the additions/subtractions that the customer wants to make to the MenuItem
//     */
//    public void takeOrder(MenuItem menuItem, Table table, HashMap<String, Integer> modifications) {
//        Order order = new Order(menuItem, modifications, table.getNumber());
//        table.addOrder(order);
//        System.out.println("================================");
//        System.out.println("New Order:");
//        System.out.println(order.toString());
//        System.out.println("================================");
//        Cook.receiveOrder(order);
//    }
//
//    /**
//     * Delivers the Order specified by orderId to the Table if it has been cooked
//     *
//     * @param orderId the ID of the order to be delivered to the Table
//     */
//    public void deliverOrder(int orderId) {
//        Order order = Cook.removeOrder(orderId);
//        if (!(order == null)) {
//            order.deliver();
//        }
//    }
//
//    /**
//     * Takes an Order that the customer found unsatisfactory and returns it to the Cooks to
//     * be redone
//     *
//     * @param orderId the ID of the order to be redone
//     */
//    public void takeRejectedOrder(int orderId) {
//        Order order = Cook.removeOrder(orderId);
//        if (!(order == null)) {
//            Cook.receiveOrder(order);
//        }
//    }
//
//    /**
//     * Outputs the bill of a table.
//     *
//     * @param table the table
//     */
//    public void getBill(Table table) {
//        System.out.println("================================");
//        System.out.println(table.getTotalBill());
//        System.out.println("================================");
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
