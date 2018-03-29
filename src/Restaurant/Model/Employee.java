package Restaurant.Model;

/**
 * Represents a general employee at the Restaurant
 */
public abstract class Employee {

    private int id;

    /**
     * Creates a new Restaurant.Model.Employee object with an Id. Every Restaurant.Model.Employee has an unique Id
     *
     * @param id the ID of this Restaurant.Model.Employee
     */
    public Employee(int id) {
        this.id = id;
    }

    /**
     * Returns the ID number of this Restaurant.Model.Employee.
     *
     * @return the ID number of this employee.
     */
    public int getId() {
        return id;
    }

}
