package Restaurant.Controller;

import Restaurant.MainApplication;
import Restaurant.Model.Menu;
import Restaurant.Model.MenuItem;
import Restaurant.Model.Order;
import Restaurant.Model.Table;
import Restaurant.Model.Server;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the View that Servers see
 *
 * Controller has functions to make orders, deliver orders, and open a window that has functionality
 * regarding Tables
 */
public class ServerController implements Observer {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderNumberColumn;
    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private Label tableNumberLabel;
    @FXML
    private Label customerNumberLabel;
    @FXML
    private Label menuItemLabel;

    @FXML
    private ComboBox<MenuItem> menuChoices;
    @FXML
    private ComboBox<Table> tableChoices;
    @FXML
    private ComboBox<Integer> customerChoices;

    @FXML
    private TextArea modificationsText;

    @FXML
    private TableView<Table> tableTables;
    @FXML
    private TableColumn<Table, Integer> tableNumberColumn;
    @FXML
    private TableView<SimpleStringProperty> ingredientTable;
    @FXML
    private TableColumn<SimpleStringProperty, String> ingredientColumn;

    private MainApplication mainApplication;
    private HashMap<String, Integer> modifications;
    private Server server;

    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());

    /**
     * Initializes a ServerController object
     */
    public ServerController() {
        this.modifications = new HashMap<>();
    }

    /**
     * Sets the Server belonging to this controller
     *
     * @param server the Server belonging to this controller
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Sets the MainApplication that this ServerController belongs to. Sets the Items for the
     * tables that display data
     *
     * @param mainApp the MainApplication that this ServerController belongs to
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApplication = mainApp;

        orderTable.setItems(mainApp.getOrderData());
        tableTables.setItems(mainApp.getTableData());
        ingredientTable.setItems(mainApp.getIngredientData());

        tableChoices.setItems(Table.getTables());
        menuChoices.setItems(Menu.getItems());
    }

    /**
     * Initializes the Server's display
     */
    @FXML
    private void initialize() {
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        showOrderDetails(null);

        orderTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));

        tableNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());

        ingredientColumn.setCellValueFactory(cellData -> cellData.getValue());

        tableChoices.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setTableChoice(newValue));
    }

    /**
     * Populates a ComboBox that lists customer numbers for a specific Table
     *
     * @param table the Table whose customer numbers are to be listed
     */
    private void setTableChoice(Table table) {
        int customerNumber = table.getNumCustomers();
        ObservableList<Integer> customerIndexList = FXCollections.observableArrayList();
        for (int i = 1; i <= customerNumber; i++) {
            customerIndexList.add(i);
        }
        customerChoices.setItems(customerIndexList);
    }

    /**
     * Displays the details of an Order to the screen.
     *
     * @param order the Order to be displayed
     */
    private void showOrderDetails(Order order) {
        if (order != null) {
            tableNumberLabel.setText(Integer.toString(order.getTableNumber()));
            customerNumberLabel.setText(Integer.toString(order.getCustomerNum()));
            menuItemLabel.setText(order.getName());
        } else {
            tableNumberLabel.setText("");
            customerNumberLabel.setText("");
            menuItemLabel.setText("");
        }
    }

    /**
     * Displays the details of the modifications to an Order made by a customer
     */
    private void showModificationDetails() {
        StringBuilder builder = new StringBuilder();
        for (String ingredient : modifications.keySet()) {
            int value = modifications.get(ingredient);

            if (value > 0) {
                builder.append('+');
            }
            builder.append(value);
            builder.append(' ');
            builder.append(ingredient);
            builder.append('\n');
        }
        modificationsText.setText(builder.toString());
    }

    /**
     * Handles the event where a Server delivers an Order to a customer
     */
    public void handleDeliver() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            if (server.deliver(order)) {
                logger.log(Level.INFO, "Delivered:\n" + order.toString());
            }
        }
    }

    /**
     * Handles the event where a Server takes back an Order that was rejected by the customer
     */
    public void handleRejectedOrder() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            if (server.handleRejectedOrder(order)) {
                logger.log(Level.INFO, order.toString() + "\nRejected and set back to kitchen");
            }
        }
    }

    /**
     * Removes any ingredients in modifications that have a quantity of zero
     *
     * This may occur when a Server adds an ingredient then subtracts it
     */
    private void removeZeroInModifications() {
        ArrayList<String> toRemove = new ArrayList<>();
        for (String ingredient : modifications.keySet()) {
            if (modifications.get(ingredient) == 0) {
                toRemove.add(ingredient);
            }
        }
        for (String ingredient: toRemove) {
            modifications.remove(ingredient);
        }
    }

    /**
     * Handles the event where an ingredient is added to an item as a modification
     */
    public void handleAddIngredient() {
        try {
            String ingredient = ingredientTable.getSelectionModel().getSelectedItem().get();
            if (modifications.keySet().contains(ingredient)) {
                modifications.replace(ingredient, modifications.get(ingredient) + 1);
            } else {
                modifications.put(ingredient, 1);
            }
            removeZeroInModifications();
            showModificationDetails();
        } catch (NullPointerException e) {
        }
    }

    /**
     * Handles the event where an ingredient is subtracted as a modification
     */
    public void handleSubtractIngredient() {
        try {
            String ingredient = ingredientTable.getSelectionModel().getSelectedItem().get();
            if (modifications.keySet().contains(ingredient)) {
                modifications.replace(ingredient, modifications.get(ingredient) - 1);
            } else {
                modifications.put(ingredient, -1);
            }
            removeZeroInModifications();
            showModificationDetails();
        } catch (NullPointerException e){
            logger.log(Level.SEVERE, "Unexpected error. Contact system administrator");
        }
    }

    /**
     * Checks previous orders to see if there are cooked orders that must be delivered prior to
     * making a new Order. Returns true if no Orders need to be delivered.
     *
     * @param tableView the table of all the Orders
     * @return          true if there are no Orders needed to be delivered
     */
    private boolean checkPreviousOrders(TableView tableView) {
        ObservableList<Order> list = tableView.getItems();
        for (Order order : list) {
            if (order.getStatus().equals("Cooked")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handles the event where a Server sends an Order to be made
     */
    public void handleSendOrder() {
        try {
            MenuItem menuItem = menuChoices.getSelectionModel().getSelectedItem();
            Table table = tableChoices.getSelectionModel().getSelectedItem();
            int customerNumber = customerChoices.getSelectionModel().getSelectedItem();
            if (checkPreviousOrders(orderTable)) {
                Order order = server.takeOrder(menuItem, table, customerNumber, modifications);
                if (mainApplication.getInventory().processOrder(order)) {
                    order.addObserver(this);
                    mainApplication.getOrderData().add(order);
                    modifications.clear();
                    logger.log(Level.INFO, "Sent:\n" + order + "\nTo the kitchen.");
                    showModificationDetails();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Not enough ingredients in Inventory to make said order");

                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please deliver cooked food before making more orders");

                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please fill in all the fields before making an order");

            alert.showAndWait();
        }
    }

    /**
     * Handles the event where a Server wishes to look at the details about a Table
     *
     * Opens a TableDialog
     */
    public void handleViewTable() {
        Table table = tableTables.getSelectionModel().getSelectedItem();
        if (table != null) {
            mainApplication.openTableDialog(table);
            if (tableChoices.getSelectionModel().selectedItemProperty().get() != null &&
                    tableChoices.getSelectionModel().selectedItemProperty().get().equals(table)){
                setTableChoice(table);
            }
        }
    }

    /**
     * Displays an information pop-up when this ServerController has been notified
     *
     * @param o   the Observable giving the notification
     * @param arg the message of the notification
     */
    @Override
    public void update(Observable o, Object arg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setContentText((String) arg);

        alert.showAndWait();
    }

}
