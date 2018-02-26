import java.io.BufferedReader;
import java.io.FileReader;
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

    public void configureRestaurant(String filename) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {

        } catch (IOException e) {

        }
    }

}
