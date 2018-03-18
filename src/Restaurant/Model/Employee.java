package Restaurant.Model;

import java.util.ArrayList;

/**
 * Represents a general employee at the Restaurant
 */
public abstract class Employee {

    private int id;

    private static ArrayList<Employee> employees = new ArrayList<>();

    /**
     * Creates a new Employee object with an Id. Every Employee has an unique Id
     *
     * @param id the ID of this Employee
     */
    public Employee(int id) {
        this.id = id;
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Returns the ID number of this Employee.
     *
     * @return the ID number of this employee.
     */
    public int getId() {
        return id;
    }
}
