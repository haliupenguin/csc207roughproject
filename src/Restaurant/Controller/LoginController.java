package Restaurant.Controller;

import Restaurant.MainApplication;
import Restaurant.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the View that Employees see when they log in
 *
 * Has functionality to allow an Employee to enter their ID and password and log in
 */
public class LoginController {

    @FXML
    private TextField idField;
    @FXML
    private PasswordField passwordField;

    private MainApplication mainApplication;
    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());

    private EmployeeDatabase employeeDatabase;

    /**
     * Initializes a LoginController object
     */
    public LoginController() {
    }

    /**
     * Initializes the View for the log in page
     */
    @FXML
    private void initialize() {
        idField.setText("");
        passwordField.setText("");
    }

    /**
     * Sets the MainApplication
     *
     * @param mainApp the MainApplication that this LoginController belongs to
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApplication = mainApp;

    }

    /**
     * Sets the EmployeeDatabase that this LoginController checks information with
     *
     * @param employeeDatabase the EmployeeDatabase to be used
     */
    public void setEmployeeDatabase(EmployeeDatabase employeeDatabase) {
        this.employeeDatabase = employeeDatabase;
    }

    /**
     * Handles a sign in event from the user.
     *
     * If the sign in is successful, the screen of the Employee is shown.
     */
    public void handleSignIn() {
        try {
            int id = Integer.parseInt(idField.getText());
            String password = passwordField.getText();

            Employee thisEmployee = null;

            for (Employee employee : employeeDatabase.getEmployees()) {
                if (employee.getId() == id) {
                    thisEmployee = employee;
                }
            }

            if (employeeDatabase.verify(id, password) && thisEmployee != null) {
                mainApplication.setEmployee(thisEmployee);
                employeeDatabase.signIn(id);
                if (thisEmployee instanceof Server) {
                    mainApplication.showServerGUI();
                } else if (thisEmployee instanceof Cook) {
                    mainApplication.showCookGUI();
                } else if (thisEmployee instanceof Manager) {
                    mainApplication.showManagerGui();
                } else if (thisEmployee instanceof Receiver) {
                    mainApplication.showReceiverGui();
                } else {
                    mainApplication.showLogin();
                }
                logger.log(Level.INFO, "Employee " + id + " signed in");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Invalid employee id/password combination");

                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Your Employee ID is a number");

            alert.showAndWait();
        }
    }

}
