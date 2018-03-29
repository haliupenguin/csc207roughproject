package Restaurant.Controller;

import Restaurant.MainApplication;
import Restaurant.Model.Order;
import Restaurant.Model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the View when a Server views a Table
 *
 * Controller has functions to set Tables and get bills
 */
public class TableDialogController {

    @FXML
    private TextField customerNumberField;
    @FXML
    private TextArea orderText;
    @FXML
    private Label tableNumberLabel;
    @FXML
    private Label occupiedLabel;

    private Stage dialogStage;
    private Table table;

    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());

    /**
     * Initializes the view for the Table
     */
    @FXML
    public void initialize() {
        showOrderDetails(table);
        tableNumberLabel.setText("");
        occupiedLabel.setText("");
    }

    /**
     * Displays all the Orders of a Table
     *
     * @param table the Table whose orders are displayed
     */
    private void showOrderDetails(Table table) {
        if (table == null) {
            orderText.setText("");
        } else {
            StringBuilder toDisplay = new StringBuilder();
            for (Order order : table.getOrders()) {
                toDisplay.append(order.toString());
                toDisplay.append('\n');
            }
            orderText.setText(toDisplay.toString());
        }
    }

    /**
     * Sets the DialogStage that this controller controls
     *
     * @param dialogStage the DialogStage that this controller controls
     */
    public void setTableDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the Table that this controller provides a view of
     *
     * @param table the Table to be displayed
     */
    public void setTable(Table table) {
        this.table = table;
        tableNumberLabel.setText(Integer.toString(table.getNumber()));
        if (table.getNumCustomers() > 0) {
            customerNumberField.setText(Integer.toString(table.getNumCustomers()));
            occupiedLabel.setText("Occupied");
        } else {
            customerNumberField.setText("");
            occupiedLabel.setText("Unoccupied");
        }
        showOrderDetails(table);
    }

    /**
     * Handles the event where a Server sets the number of customers seated at a Table
     */
    public void handleSetTable() {
        String numCustomers = customerNumberField.getText();
        if (numCustomers != null) {
            int num = Integer.parseInt(numCustomers);
            table.setNumCustomers(num);
            if (num > 0) {
                occupiedLabel.setText("Occupied");
                logger.log(Level.INFO, table.toString() + " is now occupied by " + numCustomers + " people");
            } else {
                occupiedLabel.setText("Unoccupied");
                logger.log(Level.INFO, table.toString() + " is now unoccupied");
            }
        }
    }

    /**
     * Handles the event where a Server gets a bill split for each customer at a Table
     *
     * Opens the bill in a text editor
     */
    public void getSplitBill() {
        try {
            table.printSplitBill();
            Desktop.getDesktop().open(new File("bill" + table.getNumber() + ".txt"));
            table.setNumCustomers(0);
            table.clearOrders();
            occupiedLabel.setText("Unoccupied");
            customerNumberField.setText("");
            orderText.setText("");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "bill cannot be opened");
            e.printStackTrace();
        }
    }

    /**
     * Handles the event where one bill is made for a Table
     *
     * Opens the bill in a text editor
     */
    public void getOneBill() {
        try {
            table.printOneBill();
            Desktop.getDesktop().open(new File("bill" + table.getNumber() + ".txt"));
            table.setNumCustomers(0);
            table.clearOrders();
            occupiedLabel.setText("Unoccupied");
            customerNumberField.setText("");
            orderText.setText("");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "bill cannot be opened");
            e.printStackTrace();
        }
    }

    /**
     * Handles the closing of the table dialog
     */
    public void handleClose() {
        dialogStage.close();
    }
}
