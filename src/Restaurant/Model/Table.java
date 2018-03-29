package Restaurant.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a table at the restaurant
 *
 * The Table lists all the Orders that have been made by customers seated at this Table
 */
public class Table {

    private static ObservableList<Table> tables = FXCollections.observableArrayList();

    private ArrayList<Order> orders = new ArrayList<>();
    private IntegerProperty number;
    private IntegerProperty numCustomers;

    /**
     * Creates a new Table object
     *
     * @param number         this Table's number
     * @param customerNumber the number of customers seated at this Table
     */
    public Table(int number, int customerNumber) {
        this.number = new SimpleIntegerProperty(number);
        this.numCustomers = new SimpleIntegerProperty(customerNumber);
        tables.add(this);

    }

    /**
     * Returns an ObservableList of all the Tables
     *
     * @return an ObservableList of all the Tables
     */
    public static ObservableList<Table> getTables() {
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
     * Prints a bill to a txt file.
     *
     * The bill contains all Orders made by this Table that have been delivered, and the bill is the sum
     * of the prices. Taxes are included, and an auto-gratuity of 15% is added for parties of 8 or more.
     *
     * @throws IOException if the file cannot be written to
     */
    public void printOneBill() throws IOException {
        NumberFormat formatter = new DecimalFormat("#0.00");
        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bill"+number.get()+".txt")))) {
            printer.println("Table #" + number.get());
            printer.println("================================");
            double totalPrice = 0.0;
            for (Order order : orders) {
                if (order.getStatus().equals("Delivered")) {
                    printer.println(order.billStringFormat());
                    totalPrice += order.getPrice();
                }
            }
            printer.println("================================");
            printer.println("Subtotal: " + formatter.format(totalPrice));
            totalPrice *= 1.13;
            printer.println("Total: " + formatter.format(totalPrice));

            if (numCustomers.get() >= 8) {
                totalPrice *= 1.15;
                printer.println("Price with auto-gratuity (for 8 people or more) of 15%: " + formatter.format(totalPrice));
            }

            writeToPayments("Table #" + number.get() + ": payment for " + totalPrice);
            printer.close();
        }
    }

    /**
     * Prints a bill for a single customer to a txt file.
     *
     * The bill contains all Orders made by this customer that have been delivered, and the bill is the sum
     * of the prices. Taxes are included, and an auto-gratuity of 15% is added for parties of 8 or more.
     *
     * @throws IOException if the file cannot be written to
     */
    public void printSplitBill() throws IOException {
        NumberFormat formatter = new DecimalFormat("#0.00");
        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bill" + number.get() + ".txt")))) {
            printer.println("Table #" + number.get());
            printer.println("================================");
            double finalPrice = 0.0;
            for (int i = 1; i <= numCustomers.get(); i++) {
                double totalPrice = 0.0;
                printer.println("Customer #" + i);
                for (Order order : orders) {
                    if (order.getCustomerNum() == i && order.getStatus().equals("Delivered")) {
                        totalPrice += order.getPrice();
                        printer.println(order.billStringFormat());
                    }
                }
                printer.println();
                printer.println("Subtotal: " + formatter.format(totalPrice));
                totalPrice *= 1.13;
                printer.println("Total: " + formatter.format(totalPrice));

                if (numCustomers.get() >= 8) {
                    totalPrice *= 1.15;
                    printer.println("Price with auto-gratuity (for 8 people or more) of 15%: " + formatter.format(totalPrice));
                }
                finalPrice += totalPrice;
                printer.println("================================");
            }

            writeToPayments("Table #" + number.get() + ": payment for " + formatter.format(finalPrice));
            printer.close();
        }
    }

    /**
     * Writes to a file containing all payments made to the restaurant
     *
     * @param string       the description of the payment
     * @throws IOException if the file cannot be written to
     */
    private void writeToPayments(String string) throws IOException {
        try (FileWriter fileWriter = new FileWriter("payments.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bufferedWriter)) {

            out.println(string);
        }
    }

    /**
     * Clears all the orders from this Table
     */
    public void clearOrders() {
        orders.clear();
    }

    /**
     * Returns a String representation of this Table
     *
     * @return a String representation of this Table
     */
    public String toString() {
        return "Table " + Integer.toString(number.get());
    }

    /**
     * Returns all the Orders of this Table
     *
     * @return all the Orders of this Table
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Returns the number of this Table as an IntegerProperty
     *
     * @return the number of this Table
     */
    public IntegerProperty numberProperty() {
        return number;
    }

    /**
     * Returns the number of this Table as an integer
     *
     * @return the number of this Table as an integer
     */
    public int getNumber() {
        return number.get();
    }

    /**
     * Returns the number of customers seated at this Table
     *
     * @return the number of customers seated at this Table
     */
    public int getNumCustomers() {
        return numCustomers.get();
    }

    /**
     * Sets the number of customers seated at this Table
     *
     * @param numCustomers the new number of customers at this Table
     */
    public void setNumCustomers(int numCustomers) {
        this.numCustomers.set(numCustomers);
    }

}
