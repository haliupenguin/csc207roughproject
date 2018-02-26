import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {

    protected Menu menu;
    protected Inventory inventory;
    protected ArrayList<Table> tables;
    protected ArrayList<Employee> employees;

    public Restaurant() {
        this.menu = new Menu();
        this.inventory = new Inventory();
        this.tables = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Restaurant(String fileName) throws IOException {
        // Takes config file
    }

}
