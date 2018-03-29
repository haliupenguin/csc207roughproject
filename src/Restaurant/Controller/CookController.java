package Restaurant.Controller;

import Restaurant.Model.Cook;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import Restaurant.MainApplication;
import Restaurant.Model.Order;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the view that Cooks see
 *
 * Controller has functions to see Orders, acknowledge, start cooking, finish cooking, and cancel
 * those Orders
 */
public class CookController implements Observer {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderNumberColumn;
    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private Label orderNameLabel;
    @FXML
    private TextArea ingredientsTextArea;

    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());
    private Cook cook;

    /**
     * Initializes a CookController object
     */
    public CookController() {
    }

    /**
     * Initializes the Cook's view
     */
    @FXML
    private void initialize() {
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        showOrderDetails(null);

        orderTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));
    }

    /**
     * Sets this CookController's mainApplication
     *
     * @param mainApp the mainApplication that this CookController belongs to
     */
    public void setMainApp(MainApplication mainApp) {
        orderTable.setItems(mainApp.getOrderData());
    }

    /**
     * Shows the details of an Order onto the screen
     *
     * @param order the Order to be displayed
     */
    private void showOrderDetails(Order order) {
        if (order != null) {
            orderNameLabel.setText(order.getName());
            StringBuilder ingredientsDisplay = new StringBuilder();
            HashMap<String, Integer> ingredientsNeeded = order.getIngredientsNeeded();
            for (String ingredient : ingredientsNeeded.keySet()) {
                ingredientsDisplay.append(ingredient);
                ingredientsDisplay.append(": ");
                ingredientsDisplay.append(ingredientsNeeded.get(ingredient));
                ingredientsDisplay.append('\n');
            }
            ingredientsTextArea.setText(ingredientsDisplay.toString());
        } else {
            orderNameLabel.setText("");
            ingredientsTextArea.setText("");
        }
    }

    /**
     * Checks all previous Orders to check for any Orders with a status of 'Not Acknowledged'.
     * Returns true if all previous Orders have been at least Acknowledged.
     *
     * An Order with a lower ID must be at least Acknowledged before an Order with a higher ID
     * can be Acknowledged.
     *
     * @param orderTable   the table of Orders
     * @param orderToCheck the Order which is being checked
     * @return             if all previous Orders have been acknowledged
     */
    private boolean checkPreviousOrders(TableView orderTable, Order orderToCheck) {
        int index = orderToCheck.getOrderId();
        ObservableList<Order> list = orderTable.getItems();
        for (Order order : list) {
            if (order.getOrderId() < index && order.getStatus().equals("Not Acknowledged")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Displays an Alert if a Cook tries to perform an action on an Order while earlier Orders
     * have not been acknowledged yet.
     */
    private void checkPreviousAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText("Please acknowledge the orders before this order");

        alert.showAndWait();
    }

    /**
     * Handles the event where a Cook acknowledges an Order
     */
    public void handleAcknowledge() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            if (!checkPreviousOrders(orderTable, order) ) {
                checkPreviousAlert();
            } else if (order.getStatus().equals("Not Acknowledged")) {
                cook.acknowledge(order);
                logger.log(Level.INFO, "Acknowledged:\n" + order.toString());
            }
        }
    }

    /**
     * Handles the event where a Cook starts to cook an Order
     */
    public void handleStartCooking() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            if (!checkPreviousOrders(orderTable, order)) {
                checkPreviousAlert();
            } else if ((order.getStatus().equals("Not Acknowledged") ||
                    order.getStatus().equals("Acknowledged"))) {
                cook.startCooking(order);
                logger.log(Level.INFO, "Started cooking:\n" + order.toString());
            }
        }
    }

    /**
     * Handles the event where a Cook finishes cooking an Order
     */
    public void handleFinishCooking() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            if (order.getStatus().equals("Cooking")) {
                cook.finishCooking(order);
                logger.log(Level.INFO, "Finish cooking:\n" + order.toString());
            }
        }
    }

    /**
     * Handles the event where an Order is cancelled by the Cook
     */
    public void handleCancelOrder() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            order.setStatus("Cancelled");
            logger.log(Level.INFO, "Cancelled:\n" + order.toString());
        }
    }

    /**
     * Displays an information pop-up when this Cook has been notified
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

    /**
     * Sets the Cook belonging to this CookController
     *
     * @param cook the Cook belonging to this CookController
     */
    public void setCook(Cook cook) {
        this.cook = cook;
    }
}
