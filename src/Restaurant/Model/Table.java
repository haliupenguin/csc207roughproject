package Restaurant.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.jws.WebParam;

/**
 * Represents a table at the restaurant
 *
 * The Table lists all the Orders that have been made by customers seated at this Table
 */
public class Table {

    private static ObservableList<Table> tables = FXCollections.observableArrayList();

    private ArrayList<OrderModel> orders = new ArrayList<>();
    private IntegerProperty number;
    private IntegerProperty numCustomers;

    /**
     * Creates a new Table object
     *
     * @param number this Table's number
     */
    public Table(int number, int customerNumber) {
        this.number = new SimpleIntegerProperty(number);
        this.numCustomers = new SimpleIntegerProperty(customerNumber);
        tables.add(this);

    }

    public static ObservableList<Table> getTables() {
        return tables;
    }

    public static Table getTable(int number) {
        for (Table table : tables) {
            if (table.getNumber() == number) {
                return table;
            }
        }
        return null;
    }

    /**
     * Adds an Order to this Table
     * *
     * @param order the Order to be added
     */
    public void addOrder(OrderModel order) {
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
        for (OrderModel order : orders) {
            if (order.getStatus().equals("Delivered")) {
                total += order.getPrice();
            }
        }
        return total;
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
        for (OrderModel order : orders) {
            if (order.getStatus().equals("Delivered")) {
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

    public String toString() {
        return "Table " + Integer.toString(number.get());
    }

    public ArrayList<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderModel> orders) {
        this.orders = orders;
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public IntegerProperty numCustomersProperty() {
        return numCustomers;
    }

    public int getNumber() {
        return number.get();
    }

    public int getNumCustomers() {
        return numCustomers.get();
    }

    public void setNumCustomers(int numCustomers) {
        this.numCustomers.set(numCustomers);
    }

}
