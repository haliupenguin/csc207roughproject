import java.io.IOException;

public class Restaurant {

    protected Menu menu;
    protected Inventory inventory;

    public Restaurant() {
        this.menu = new Menu();
        this.inventory = new Inventory();
    }

    public Restaurant(String fileName) throws IOException {
        // Takes config file
    }

}
