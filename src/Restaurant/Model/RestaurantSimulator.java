//package Restaurant.Model;
//
//import java.io.*;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.HashMap;
//
//
///**
// * The class where the program starts.
// *
// * Reads from config.txt for the initial configuration (unless the file does not exist), and reads
// * from events.txt for the events that occur in the simulation. Note that if any of these files
// * are not in the correct format, the program may not work correctly.
// *
// * See README.txt to see how config.txt and events.txt should be formatted.
// */
//public class RestaurantSimulator {
//
//    /**
//     * Create a default config.txt that contains an empty menu, 1 table, an empty inventory of
//     * ingredients, and 1 of each kind of employee (1 server, 1 cook, 1 receiver, 1 manager).
//     *
//     * @throws IOException if there is an error writing to or creating config.txt.
//     */
//    public static void createDefaultConfig() throws IOException {
//        try (PrintWriter newConfig = new PrintWriter(new BufferedWriter(new FileWriter("config.txt")))) {
//            newConfig.println("MENU");
//            newConfig.println();
//            newConfig.println("TABLES");
//            newConfig.println(1);
//            newConfig.println();
//            newConfig.println("INGREDIENTS");
//            newConfig.println();
//            newConfig.println("EMPLOYEES");
//            newConfig.println("SERVER");
//            newConfig.println(1);
//            newConfig.println("COOK");
//            newConfig.println(1);
//            newConfig.println("RECEIVER");
//            newConfig.println(1);
//            newConfig.println("MANAGER");
//            newConfig.println(1);
//        }
//    }
//
//    /**
//     * Update config.txt after a change(s) has been made to the inventory, number of tables/employees, and/or menu.
//     *
//     * @throws IOException if config.txt does not exist.
//     */
//    public static void updateConfig() throws IOException {
//        // Clear the old config.txt
//        PrintWriter config = new PrintWriter(new FileWriter("config.txt"));
//        config.print("");
//        config.close();
//
//        try (PrintWriter newConfig = new PrintWriter(new BufferedWriter(new FileWriter("config.txt")))) {
//            newConfig.println("MENU");
//            for (MenuItem item : Menu.getItems()) {
//                newConfig.println(item.getName());
//                NumberFormat formatter = new DecimalFormat("#0.00");
//                newConfig.println(formatter.format(item.getPrice()));
//
//                StringBuilder ingredientsNeeded = new StringBuilder();
//                for (String ingredient : item.getIngredientsNeeded().keySet()) {
//                    ingredientsNeeded.append(ingredient);
//                    ingredientsNeeded.append(" ");
//                    ingredientsNeeded.append(item.getIngredientsNeeded().get(ingredient));
//                    ingredientsNeeded.append(" | ");
//                }
//                ingredientsNeeded.delete(ingredientsNeeded.length() - 3, ingredientsNeeded.length());
//                newConfig.println(ingredientsNeeded.toString());
//            }
//
//            newConfig.println();
//            newConfig.println("TABLES");
//            newConfig.println(Table.getTables().size());
//
//            newConfig.println();
//            newConfig.println("INVENTORY");
//
//            for (String ingredient : Inventory.getInventory().keySet()) {
//                newConfig.println(ingredient);
//                newConfig.println(Inventory.getInventory().get(ingredient));
//                newConfig.println(Inventory.getMinimums().get(ingredient));
//                newConfig.println(Inventory.getDefaultOrderAmounts().get(ingredient));
//            }
//
//            newConfig.println();
//            newConfig.println("EMPLOYEES");
//            int servers = 0;
//            int cooks = 0;
//            int receivers = 0;
//            int managers = 0;
//
//            for (Employee employee : Employee.getEmployees()) {
//                if (employee instanceof Server) {
//                    servers++;
//                } else if (employee instanceof Cook) {
//                    cooks++;
//                } else if (employee instanceof Receiver) {
//                    receivers++;
//                } else {
//                    managers++;
//                }
//            }
//
//            newConfig.println("SERVER");
//            newConfig.println(servers);
//            newConfig.println("COOKS");
//            newConfig.println(cooks);
//            newConfig.println("RECEIVERS");
//            newConfig.println(receivers);
//            newConfig.println("MANAGERS");
//            newConfig.println(managers);
//        }
//    }
//
//
//    /**
//     * Loads configuration from config.txt. If config.txt does not exist, a default one will be written.
//     * If config.txt is not formatted correctly, this function may not work correctly. See README.txt to see how
//     * config.txt should be formatted.
//     *
//     * @throws IOException if there is an issue writing the default config.txt.
//     */
//    public static void checkConfig() throws IOException {
//        try (BufferedReader config = new BufferedReader(new FileReader("config.txt"))) {
//            config.readLine();
//
//            // Creates the Menu from the config file. In the config file, the first line is the name
//            // of the MenuItem, second line is its price, and third line is its ingredients plus
//            // the quantity needed.
//            while (true) {
//                String name = config.readLine();
//                if (name.equals("")) {
//                    break;
//                }
//                double price = Double.parseDouble(config.readLine());
//                String ingredients = config.readLine().trim();
//                String[] listIngredients = ingredients.split("\\|");
//                HashMap<String, Integer> ingredientMap = new HashMap<>();
//                for (int i = 0; i < listIngredients.length; i++) {
//                    String[] s = listIngredients[i].trim().split("\\s");
//                    String ingredientName = s[0].trim();
//                    ingredientMap.put(ingredientName, Integer.parseInt(s[1]));
//                }
//                MenuItem m = new MenuItem(ingredientMap, name, price);
//                Menu.addItem(m);
//            }
//
//            // Creates all the tables
//            config.readLine();
//            int numtables = Integer.parseInt(config.readLine());
//            for (int i = 0; i < numtables; i++) {
//                new Table(i + 1);
//            }
//
//            // Creates the Inventory with current amount of each ingredient and their minimum amounts
//            config.readLine();
//            config.readLine();
//            while (true) {
//                String name = config.readLine().trim();
//                if (name.equals("")) {
//                    break;
//                }
//                int amount = Integer.parseInt(config.readLine());
//                int minimum = Integer.parseInt(config.readLine());
//                int defaultOrderAmount = Integer.parseInt(config.readLine());
//                Inventory.addIngredient(name, amount, minimum, defaultOrderAmount);
//            }
//
//            // Creates all the Employees
//            config.readLine();
//            int id = 1;
//            config.readLine();
//            int numServers = Integer.parseInt(config.readLine());
//            for (int i = 0; i < numServers; i++) {
//                Employee.getEmployees().add(new Server(id++));
//            }
//            config.readLine();
//            int numCooks = Integer.parseInt(config.readLine());
//            for (int i = 0; i < numCooks; i++) {
//                Employee.getEmployees().add(new Cook(id++));
//            }
//            config.readLine();
//            int numReceivers = Integer.parseInt(config.readLine());
//            for (int i = 0; i < numReceivers; i++) {
//                Employee.getEmployees().add(new Receiver(id++));
//            }
//            config.readLine();
//            int numManagers = Integer.parseInt(config.readLine());
//            for (int i = 0; i < numManagers; i++) {
//                Employee.getEmployees().add(new Manager(id++));
//            }
//        } catch (IOException e) {
//            createDefaultConfig();
//        }
//    }
//
//    /**
//     * Processes the events in events.txt, line by line. See README.txt for how events.txt is
//     * formatted. In the case that events.txt is not properly formatted, this method may or
//     * may not work.
//     *
//     * @throws IOException if events.txt does not exist.
//     */
//    public static void processEvents() throws IOException {
//        try (BufferedReader events = new BufferedReader(new FileReader("events.txt"))) {
//            String event = events.readLine();
//
//            while (event != null) {
//                String[] fields = event.split(" \\|");
//                for (int i = 0; i < fields.length; i++) {
//                    fields[i] = fields[i].trim();
//                }
//                if (fields[2].equals("take_order")) {
//                    EventProcessor.processTakeOrder(fields);
//                } else if (fields[2].equals("start_cook")) {
//                    EventProcessor.processStartCooking(fields);
//                } else if (fields[2].equals("finish_cook")) {
//                    EventProcessor.processFinishCooking(fields);
//                } else if (fields[2].equals("deliver_order")) {
//                    EventProcessor.processDeliverOrder(fields);
//                } else if (fields[2].equals("receive")) {
//                    EventProcessor.processReceive(fields);
//                } else if (fields[2].equals("take_rejected")) {
//                    EventProcessor.processRejected(fields);
//                } else if (fields[2].equals("get_bill")) {
//                    EventProcessor.processGetBill(fields);
//                } else if (fields[2].equals("check_inventory")) {
//                    EventProcessor.processCheckInventory(fields);
//                } else if (fields[2].equals("override")) {
//                    EventProcessor.processOverride(fields);
//                } else if (fields[2].equals("sign_in")) {
//                    EventProcessor.processSignIn(fields);
//                } else if (fields[2].equals("sign_out")) {
//                    EventProcessor.processSignOut(fields);
//                } else {
//                    System.out.println("Command not recognized");
//                }
//                event = events.readLine();
//            }
//        }
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        checkConfig();
//        processEvents();
//        updateConfig();
//    }
//}
