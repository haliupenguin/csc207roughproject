package Restaurant;

import Restaurant.Model.*;
import Restaurant.Controller.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 * Since credit must be given to those who taught us how JavaFX works, we shall give a citation to
 * code.makery.ch/library/javafx-8-tutorial
 */

/**
 * The main application that runs the restaurant
 */
public class MainApplication extends Application {

    private Inventory inventory = new Inventory();
    private EmployeeDatabase employeeDatabase;
    private Employee employee;

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    private ObservableList<Table> tableData = FXCollections.observableArrayList();

    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());
    private static FileHandler fileHandler;

    /**
     * Initializes a MainApplication
     */
    public MainApplication() {
        try {
            ConfigReadWriter.readConfig(inventory, this);

            this.employeeDatabase = new EmployeeDatabase(new HashMap<Integer, String>());
            employeeDatabase.readEmployeeDatabase(inventory);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "config file could not be written");
        }

    }

    /**
     * Returns the List of all Orders
     *
     * @return the List of all Orders
     */
    public ObservableList<Order> getOrderData() {
        return orderData;
    }

    /**
     * Returns the list of all Tables
     * @return the list of all Tables
     */
    public ObservableList<Table> getTableData() {
        return tableData;
    }

    /**
     * Returns a list of all the ingredients in the Inventory
     * @return a list of all the ingredients in the Inventory
     */
    public ObservableList<SimpleStringProperty> getIngredientData(){
        ObservableList<SimpleStringProperty> data = FXCollections.observableArrayList();
        for (String ingredient : inventory.getIngredients()) {
            data.add(new SimpleStringProperty(ingredient));
        }
        return data;
    }

    public static void main(String[] args) {
        initializeLogger();
        logger.log(Level.INFO, "Application started.");
        launch(args);
    }

    /**
     * Starts the UI
     *
     * @param primaryStage the Stage that elements are displayed to
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(evt -> {
            // Override the RED X button
            try {
                evt.consume();

                employeeDatabase.signOut(employee.getId());
                logger.log(Level.INFO, "Employee " + employee.getId() + " signed out");
                logger.log(Level.INFO, "Application Exited");

                ConfigReadWriter.updateConfig(inventory);

                employeeDatabase.updateEmployeeDatabase();
                System.exit(0);
            } catch (NullPointerException e) {
                // Because no employee was signed in
                logger.log(Level.INFO, "Application Exited");
                employeeDatabase.updateEmployeeDatabase();
                System.exit(0);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "config could not be written to");
            }
        });

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Restaurant Program");

        initRootLayout();

        showLogin();
    }

    /**
     * Displays the root of the display
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApplication(this);
            controller.setEmployeeDatabase(employeeDatabase);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Cook's view
     */
    public void showCookGUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/Cook.fxml"));
            AnchorPane cookGui = loader.load();

            rootLayout.setCenter(cookGui);

            CookController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCook((Cook) employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show's the Server's view
     */
    public void showServerGUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/Server.fxml"));
            AnchorPane serverGui = loader.load();

            rootLayout.setCenter(serverGui);

            ServerController controller = loader.getController();
            controller.setMainApp(this);
            controller.setServer((Server) employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Manager's view
     */
    public void showManagerGui() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/Manager.fxml"));
            AnchorPane managerGui = loader.load();

            rootLayout.setCenter(managerGui);

            ManagerController controller = loader.getController();
            controller.setMainApp(this);
            controller.setManager((Manager) employee);
            controller.setEmployeeDatabase(employeeDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Receiver's view
     */
    public void showReceiverGui() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/Receiver.fxml"));
            AnchorPane receiverGui = loader.load();

            rootLayout.setCenter(receiverGui);

            ReceiverController controller = loader.getController();
            controller.setMainApp(this);
            controller.setReceiver((Receiver) employee);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/Login.fxml"));
            AnchorPane loginGui = loader.load();

            rootLayout.setCenter(loginGui);

            LoginController controller = loader.getController();
            controller.setMainApp(this);
            controller.setEmployeeDatabase(employeeDatabase);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a table dialog
     *
     * @param table the Table to be displayed
     */
    public void openTableDialog(Table table) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("View/TableDialog.fxml"));
            AnchorPane dialog = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Table Viewer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            TableDialogController controller = loader.getController();
            controller.setTableDialogStage(dialogStage);
            controller.setTable(table);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets this MainApplication's current Employee
     *
     * @param employee the current Employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Returns the Inventory of this MainApplication
     * @return the Inventory of this MainApplication
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns the Employee belonging to this MainApplication
     * @return the Employee belonging to this MainApplication
     */
    public Employee getEmployee() {
        return employee;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Initializes a logger for this program
     */
    public static void initializeLogger() {
        try {
            fileHandler = new FileHandler("log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
    }

}
