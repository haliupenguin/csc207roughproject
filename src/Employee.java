public class Employee {

    protected int id;
    protected Restaurant restaurant;

    public Employee(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public String toString() {
        return "Employee, id " + Integer.toString(id);
    }

}
