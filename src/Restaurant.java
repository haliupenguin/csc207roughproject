import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void configureRestaurant(String filename) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line = fileReader.readLine();
            // First section is the Menu
            while (true) {
                String name = fileReader.readLine();
                if (name.equals("INVENTORY")) {
                    break;
                }
                double price = Double.parseDouble(fileReader.readLine());
                String ingredients = fileReader.readLine();
                String[] listIngredients = ingredients.split("\\|");
                HashMap<String, Integer> ingredientMap = new HashMap<>();
                for (int i = 0; i < listIngredients.length; i++) {
                    String[] s = listIngredients[i].split("\\s");
                    ingredientMap.put(s[0], Integer.parseInt(s[1]));
                }
                MenuItem m = new MenuItem(name, price, ingredientMap);
                menu.addMenuItem(m);
            }

            // Second section is inventory
            while (true) {
                String name = fileReader.readLine();
                if (name.equals("TABLES")) {
                    break;
                }
                int quantity = Integer.parseInt(fileReader.readLine());
                int minimum = Integer.parseInt(fileReader.readLine());
                inventory.addInventoryItem(name, quantity, minimum);
            }

            // Third section is number of Tables. Initialize that number of Tables
            int numTables = Integer.parseInt(fileReader.readLine());
            for (int i = 0; i < numTables; i++) {
                tables.add(new Table(i + 1));
            }

            // Last section is number of each Employee
            fileReader.readLine();
            fileReader.readLine();
            int numServers = Integer.parseInt(fileReader.readLine());
            for (int i = 0; i < numServers; i++) {
                employees.add(new Server(i + 1, this));
            }
            fileReader.readLine();
            int numCooks = Integer.parseInt(fileReader.readLine());
            for (int i = 0; i < numCooks; i++) {
                employees.add(new Cook(i + 1, this));
            }
            int numManagers = Integer.parseInt(fileReader.readLine());
            for (int i = 0; i < numManagers; i++) {
                employees.add(new Manager(i + 1, this));
            }
            int numReceivers = Integer.parseInt(fileReader.readLine());
            for (int i = 0; i < numReceivers; i++) {
                employees.add(new Receiver(i + 1, this));
            }
            fileReader.close();
        } catch (IOException e) {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
                out.println("MENU");
                out.println("Burger");
                out.println("2.25");
                out.println("Bun 1 | Patty 1 | Lettuce 1 | Tomato 1 | Mayonnaise 1");
                out.println("Fries");
                out.println("1.50");
                out.println("Potato 4 | Salt 1");
                out.println("INVENTORY");
                out.println("Bun\n100\n20");
                out.println("Patty\n100\n20");
                out.println("Lettuce\n100\n20");
                out.println("Tomato\n100\n20");
                out.println("Mayonnaise\n100\n20");
                out.println("Potato\n100\n20");
                out.println("Salt\n100\n20");
                out.println("TABLES");
                out.println("5");
                out.println("EMPLOYEES");
                out.println("Servers\n4");
                out.println("Cooks\n2");
                out.println("Managers\n1");
                out.println("Receivers\n1");
                out.close();
                configureRestaurant(filename);
            }
        }
    }

}
