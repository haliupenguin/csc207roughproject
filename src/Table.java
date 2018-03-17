import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Represents a table at the restaurant
 *
 * The Table lists all the Orders that have been made by customers seated at this Table
 */
public class Table {

    private static ArrayList<Table> tables = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private int number;

    /**
     * Creates a new Table object
     *
     * @param number this Table's number
     */
    public Table(int number) {
        this.number = number;
        tables.add(this);
    }

    public static ArrayList<Table> getTables() {
        return tables;
    }

    /**
     * Adds an Order to this Table
     * *
     * @param order the Order to be added
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Returns the total price of all Orders of this Table
     *
     * An Order only counts towards the total price if it has been delivered.
     *
     * @return the total price of the Orders of this Table
     */
    public double getTotalPrice() {
        double total = 0;
        for (Order order : orders) {
            if (order.isDelivered()) {
                total += order.getPrice();
            }
        }
        return total;
    }

    /**
     * Returns this Table's number
     *
     * @return this Table's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns a String representation of this Table's bill
     *
     * The bill contains all Orders made by this Table that have been delivered, and the bill is the sum
     * of the prices.
     *
     * @return a String representation of this Table's bill
     */
    public String getTotalBill() {
        StringBuilder result = new StringBuilder("Table Number " + number);
        result.append("\n");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Order order : orders) {
            if (order.isDelivered()) {
                result.append(order.getName());
                result.append(":");
                result.append("\n");
                result.append(formatter.format(order.getPrice()));
                result.append("\n");
            }
        }
        result.append("Total: \n");
        result.append(formatter.format((getTotalPrice())));

        return result.toString();
    }


}
