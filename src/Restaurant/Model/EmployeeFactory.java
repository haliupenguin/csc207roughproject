package Restaurant.Model;

/**
 * Creates Employees for use in the MainApplication and EmployeeDatabase
 */
public class EmployeeFactory {

    /**
     * Initializes a new EmployeeFactory
     */
    public EmployeeFactory() {}

    /**
     * Adds a new Employee to the EmployeeDatabase.
     *
     * Employees are of four types: Servers, Cooks, Managers, and Receivers.
     *
     * @param type      the type of the Employee
     * @param idNumber  the ID of the Employee
     * @param inventory the Inventory that certain Employees will be matched to
     */
    public Employee addNewEmployee(String type, int idNumber,
                               Inventory inventory) {
        switch (type) {
            case ("Server"):
                return new Server(idNumber);
            case ("Cook"):
                return new Cook(idNumber);
            case ("Manager"):
                return new Manager(idNumber, inventory);
            case ("Receiver"):
                return new Receiver(idNumber, inventory);
        }
        return null;
    }

}
