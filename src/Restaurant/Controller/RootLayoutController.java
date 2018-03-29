package Restaurant.Controller;
;
import Restaurant.ConfigReadWriter;
import Restaurant.MainApplication;
import Restaurant.Model.EmployeeDatabase;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the menu bar at the top of the application
 *
 * Also will display any views for Employees such as Login
 */
public class RootLayoutController {

    private MainApplication mainApplication;
    private EmployeeDatabase employeeDatabase;
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    /**
     * Sets the mainApplication that this RootLayoutController belongs to
     *
     * @param mainApplication the mainApplication that this RootLayoutController belongs to
     */
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Sets the EmployeeDatabase that this controller uses to log Employees out
     *
     * @param employeeDatabase the EmployeeDatabase that this controller uses
     */
    public void setEmployeeDatabase(EmployeeDatabase employeeDatabase) {
        this.employeeDatabase = employeeDatabase;
    }


    /**
     * Handles the exiting of the application from the RootLayout button
     */
    @FXML
    private void handleExit() {
        try {
            if (mainApplication.getEmployee() != null) {
                logger.log(Level.INFO, "Employee " + mainApplication.getEmployee().getId() + " signed out");
                employeeDatabase.signOut(mainApplication.getEmployee().getId());
            }
            logger.log(Level.INFO, "Application exited");
            ConfigReadWriter.updateConfig(mainApplication.getInventory());
            employeeDatabase.updateEmployeeDatabase();
            System.exit(0);
        } catch (IOException e ){
            e.printStackTrace();
            logger.log(Level.SEVERE, "config could not be written to");
        }
    }

    /**
     * Handles the signing out of an employee and returns them to the sign in screen
     */
    @FXML
    private void handleSignOut() {
        if (mainApplication.getEmployee() != null) {
            employeeDatabase.signOut(mainApplication.getEmployee().getId());
            logger.log(Level.INFO, "Employee " + mainApplication.getEmployee().getId() + " signed out");
            mainApplication.showLogin();
            mainApplication.setEmployee(null);
        }
    }

}
