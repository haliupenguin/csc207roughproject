package Restaurant;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import Restaurant.Model.*;

/**
 * Reader and writer to extract information from the config file.
 */
public class ConfigReadWriter {

    private final static Logger logger = Logger.getLogger(MainApplication.class.getName());

    /**
     * Creates a default config file if one could not be found before
     *
     * @throws IOException if the file could not be written to
     */
    private static void createDefaultConfig() throws IOException {
        try (PrintWriter newConfig = new PrintWriter(new BufferedWriter(new FileWriter("config.txt")))) {
            newConfig.println("MENU");
            newConfig.println();
            newConfig.println("TABLES");
            newConfig.println(1);
            newConfig.println();
            newConfig.println("INGREDIENTS");
            newConfig.println();
        }
    }

    /**
     * Updates the config file when the program closes
     *
     * @param inventory the Inventory that will be written into the config
     * @throws IOException if the file cannot be written to
     */
    public static void updateConfig(Inventory inventory) throws IOException {
        // Clear the old config.txt
        PrintWriter config = new PrintWriter(new FileWriter("config.txt"));
        config.print("");
        config.close();

        try (PrintWriter newConfig = new PrintWriter(new BufferedWriter(new FileWriter("config.txt")))) {
            newConfig.println("MENU");
            for (MenuItem item : Menu.getItems()) {
                newConfig.println(item.getName());
                NumberFormat formatter = new DecimalFormat("#0.00");
                newConfig.println(formatter.format(item.getPrice()));

                StringBuilder ingredientsNeeded = new StringBuilder();
                for (String ingredient : item.getIngredientsNeeded().keySet()) {
                    ingredientsNeeded.append(ingredient);
                    ingredientsNeeded.append(" ");
                    ingredientsNeeded.append(item.getIngredientsNeeded().get(ingredient));
                    ingredientsNeeded.append(" | ");
                }
                ingredientsNeeded.delete(ingredientsNeeded.length() - 3, ingredientsNeeded.length());
                newConfig.println(ingredientsNeeded.toString());
            }

            newConfig.println();
            newConfig.println("TABLES");
            newConfig.println(Table.getTables().size());

            newConfig.println();
            newConfig.println("INVENTORY");

            for (String ingredient : inventory.getInventory().keySet()) {
                newConfig.println(ingredient);
                newConfig.println(inventory.getInventory().get(ingredient));
                newConfig.println(inventory.getMinimums().get(ingredient));
                newConfig.println(inventory.getDefaultOrderAmounts().get(ingredient));
            }
            newConfig.println();
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Config.txt could not be written to");
        }
    }

    /**
     * Reads the config files which contains information about Menu, Tables, and Inventory.
     *
     * @param inventory       the Inventory that will be populated
     * @param mainApplication the MainApplication that the ConfigReadWriter is attached to
     * @throws IOException    if the config file cannot be read
     */
    public static void readConfig(Inventory inventory, MainApplication mainApplication) throws IOException {
        try (BufferedReader config = new BufferedReader(new FileReader("config.txt"))) {
            config.readLine();

            // Creates the Menu from the config file. In the config file, the first line is the name
            // of the MenuItem, second line is its price, and third line is its ingredients plus
            // the quantity needed.
            while (true) {
                String name = config.readLine();
                if (name.equals("")) {
                    break;
                }
                double price = Double.parseDouble(config.readLine());
                String ingredients = config.readLine().trim();
                String[] listIngredients = ingredients.split("\\|");
                HashMap<String, Integer> ingredientMap = new HashMap<>();
                for (int i = 0; i < listIngredients.length; i++) {
                    String[] s = listIngredients[i].trim().split("\\s");
                    String ingredientName = s[0].trim();
                    ingredientMap.put(ingredientName, Integer.parseInt(s[1]));
                }
                MenuItem m = new MenuItem(ingredientMap, name, price);
                Menu.addItem(m);
            }

            // Creates all the tables
            config.readLine();
            int numTables = Integer.parseInt(config.readLine());
            for (int i = 0; i < numTables; i++) {
                mainApplication.getTableData().add(new Table(i + 1,0));
            }

            // Creates the Inventory with current amount of each ingredient and their minimum amounts
            config.readLine();
            config.readLine();
            while (true) {
                String name = config.readLine().trim();
                if (name.equals("")) {
                    break;
                }
                int amount = Integer.parseInt(config.readLine());
                int minimum = Integer.parseInt(config.readLine());
                int defaultOrderAmount = Integer.parseInt(config.readLine());
                inventory.addIngredient(name, amount, minimum, defaultOrderAmount);
            }

        } catch (IOException e) {
            createDefaultConfig();
        }
    }
}
