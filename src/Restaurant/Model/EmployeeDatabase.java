package Restaurant.Model;

import Restaurant.MainApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the database of all restaurant employees with their IDs and passwords
 *
 */
public class EmployeeDatabase {

    private HashMap<Integer, String> credentials;
    private ArrayList<Integer> signedIn = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private EmployeeFactory employeeFactory = new EmployeeFactory();
    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());


    /**
     * Creates a new Restaurant.Model.EmployeeDatabase, which stores the sign in/out credentials for the employees.
     *
     * @param credentials a HashMap of employee id's to their passwords
     */
    public EmployeeDatabase(HashMap<Integer, String> credentials) {
        this.credentials = credentials;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Verify the credentials of this employee are correct.
     * @param id the employee's id
     * @param password the employee's password
     * @return if the credentials are correct
     */
    public boolean verify(int id, String password) {
        return credentials.keySet().contains(id) && credentials.get(id).equals(password);
    }

    /**
     * Returns if the employee of this id is in the database
     * @param id the employee's id
     * @return if the employee is in the database
     */
    public boolean employeeInDatabase(int id) {
        return credentials.keySet().contains(id);
    }

    /**
     * Signs in an Employee. Returns true if the sign in information matches that of the
     * database, returns false otherwise.
     *
     * @param id       the ID of the Employee
     */
    public void signIn(int id) {
        if (credentials.keySet().contains(id) && !signedIn.contains(id)) {
            signedIn.add(id);
        }
    }

    /**
     * Signs out an Employee based only on their ID.
     *
     * @param id the ID of the Employee
     */
    public void signOut(int id) {
        try {
            if (signedIn.contains(id)) {
                signedIn.remove(new Integer(id));
            }
        } catch (NullPointerException e) {
        }
    }

    public void addNewEmployee(String type, int idNumber, String password, Inventory inventory) {
        employees.add(employeeFactory.addNewEmployee(type, idNumber, inventory));
        credentials.put(idNumber, password);

    }

    /**
     * Removes this employee from the database
     * @param id the employee's id
     */
    public void removeEmployee(int id) {
        credentials.remove(id);
        employees.removeIf(employee -> employee.getId() == id);
    }

    /**
     * Updates the employee database text file
     */
    public void updateEmployeeDatabase() {
        try (PrintWriter database = new PrintWriter(new BufferedWriter(new FileWriter(
                "employeeDatabase.txt")))) {
            for (Employee employee : employees) {
                database.write(Integer.toString(employee.getId()) + " " + credentials.get(employee.getId())
                        + " " + employee.getClass().getSimpleName());
                database.write('\n');
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "employeeDatabase.txt could not be written to");
            e.printStackTrace();
        }
    }

    /**
     * Reads the employeeDatabase text file to populate the EmployeeDatabase
     *
     * @param inventory the Inventory that will belong to certain Employees
     */
    public void readEmployeeDatabase(Inventory inventory) {
        try (BufferedReader database = new BufferedReader(new FileReader("employeeDatabase.txt"))) {
            String line = database.readLine();
            while (line != null) {
                String[] idAndPassword = line.split("\\s");
                addNewEmployee(idAndPassword[2], Integer.parseInt(idAndPassword[0]), idAndPassword[1], inventory);
                line = database.readLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "employeeDatabase.txt could not be read");
            e.printStackTrace();
        }
    }

}
