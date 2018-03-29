package Restaurant.Controller;

import Restaurant.Model.EmployeeDatabase;
import Restaurant.Model.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import Restaurant.MainApplication;
import Restaurant.Model.Order;

import java.awt.Desktop;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;

/**
 * Controller for the View that Managers see
 *
 * Controller has functions to get undelivered orders, today's payments, and modify Inventory
 * conditions
 */
public class ManagerController implements Observer {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderNumberColumn;
    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableView<SimpleStringProperty> ingredientTable;
    @FXML
    private TableColumn<SimpleStringProperty, String> ingredientColumn;

    @FXML
    private Label ingredientNameLabel;
    @FXML
    private Label ingredientQuantityLabel;
    @FXML
    private TextField minimumAmountTextField;
    @FXML
    private TextField defaultOrderAmountTextField;

    @FXML
    private TextField addEmployeeID;
    @FXML
    private PasswordField addEmployeePassword;
    @FXML
    private TextField removeEmployeeID;
    @FXML
    private PasswordField removeEmployeePassword;
    @FXML
    private ComboBox<String> addEmployeeComboBox;
    @FXML
    private ObservableList<String> employeeTypes = FXCollections.observableArrayList();

    private MainApplication mainApplication;
    private Manager manager;
    private EmployeeDatabase employeeDatabase;
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    /**
     * Initializes a ManagerController object
     */
    public ManagerController() {
    }

    /**
     * Initializes the Manager's display
     */
    @FXML
    public void initialize() {
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        showIngredientDetails(null);

        ingredientColumn.setCellValueFactory(cellData -> cellData.getValue());

        ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
                newValue) -> showIngredientDetails(newValue));

        employeeTypes.add("Server");
        employeeTypes.add("Cook");
        employeeTypes.add("Receiver");
        employeeTypes.add("Manager");
        addEmployeeComboBox.setItems(employeeTypes);
    }

    /**
     * Displays the details about an ingredient in the Inventory
     *
     * @param ingredient the ingredient to display
     */
    private void showIngredientDetails(SimpleStringProperty ingredient) {
        if (ingredient != null) {
            ingredientNameLabel.setText(ingredient.get());
            ingredientQuantityLabel.setText(Integer.toString(manager.getIngredientAmount(ingredient.get())));
            minimumAmountTextField.setText(Integer.toString(manager.getMinimumAmount(ingredient.get())));
            defaultOrderAmountTextField.setText(Integer.toString(manager.getDefaultOrderAmount(ingredient.get())));
        } else {
            ingredientNameLabel.setText("");
            ingredientQuantityLabel.setText("");
            minimumAmountTextField.setText("");
            defaultOrderAmountTextField.setText("");
        }
    }

    /**
     * Sets the MainApplication that this controller belongs to. Fills in the tables for Orders and
     * ingredients
     *
     * @param mainApp the MainApplication that this controller belongs to
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApplication = mainApp;

        orderTable.setItems(mainApp.getOrderData());
        ingredientTable.setItems(mainApp.getIngredientData());
    }

    /**
     * Sets the Manager belonging to this ManagerController
     *
     * @param manager the Manager belonging to this ManagerController
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Sets the EmployeeDatabase that this Manager can modify
     *
     * @param employeeDatabase the EmployeeDatabase
     */
    public void setEmployeeDatabase(EmployeeDatabase employeeDatabase) {
        this.employeeDatabase = employeeDatabase;
    }

    /**
     * Handles the event that a Manager requests a list of all undelivered orders in the restaurant
     */
    public void handleGetUndeliveredOrders() {
        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("undelivered.txt")))) {
            printer.println("=== UNDELIVERED ORDERS ===");
            ObservableList<Order> orderList = orderTable.getItems();
            for (Order order : orderList) {
                if (!order.getStatus().equals("Delivered")) {
                    printer.println(order.toString());
                    printer.println("_________");
                }
            }
            printer.close();

            Desktop.getDesktop().open(new File("undelivered.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event where a Manager changes the default order amount or minimum of an
     * ingredient in the Inventory
     */
    public void handleMakeChanges() {
        try {
            manager.overrideDefaultOrderAmount(ingredientNameLabel.getText(),
                    Integer.parseInt(defaultOrderAmountTextField.getText()));
            manager.overrideMinimumAmount(ingredientNameLabel.getText(),
                    Integer.parseInt(minimumAmountTextField.getText()));
            logger.log(Level.INFO, "Default Order Amount / Minimum Amount of " +
                    ingredientNameLabel.getText() + "has been changed");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter data correctly!");

            alert.showAndWait();
        }
    }

    /**
     * Handles the event where a Manager requests all payments made to the restaurant today
     */
    public void handleGetTodaysPayment() {
        try {
            Desktop.getDesktop().open(new File("payments.txt"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Payments file could not be opened");
            e.printStackTrace();
        }
    }

    /**
     * Handles the event where a Manager adds a new Employee to the restaurant
     *
     * This modifies the EmployeeDatabase to include the new Employee's details
     */
    public void addNewEmployee() {
        try {
            int newId = Integer.parseInt(addEmployeeID.getText());
            if (employeeDatabase.employeeInDatabase(newId)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("This ID already exists in the database");
                alert.showAndWait();
            } else {
                employeeDatabase.addNewEmployee(addEmployeeComboBox.getValue(), newId,
                        addEmployeePassword.getText(), manager.getInventory());
                logger.log(Level.INFO, "Added new employee " + addEmployeeComboBox.getValue() + " " + newId);
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("ID must be an integer");

            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select an employee type");

            alert.showAndWait();
        }
    }

    /**
     * Handles the event where a Manager removes an Employee from the restaurant
     *
     * This modifies to EmployeeDatabase to exclude that Employee
     */
    public void removeEmployee() {
        try {
            int id = Integer.parseInt(removeEmployeeID.getText());
            String password = removeEmployeePassword.getText();
            if (!employeeDatabase.employeeInDatabase(id)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("This ID does not exist in the database");
                alert.showAndWait();
            } else if (!employeeDatabase.verify(id, password)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("This password does not match this ID");
                alert.showAndWait();
            } else {
                employeeDatabase.removeEmployee(id);
                logger.log(Level.INFO, "Removed employee id " + id);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("ID must be an integer");

            alert.showAndWait();
        }
    }

    /**
     * Displays an information pop-up when this ManagerController has been notified
     *
     * @param o   the Observable giving the notification
     * @param arg the message of the notification
     */
    @Override
    public void update(Observable o, Object arg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setContentText((String) arg);

        alert.showAndWait();
    }
}
