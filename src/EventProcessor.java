import java.io.IOException;
import java.util.HashMap;

public class EventProcessor {
    /**
     * Given an event's data from events.txt, this method will make a Server employee make an order.
     *
     * @param fields the details of the order, formatted correctly into an array of string.
     */
    public static void processTakeOrder(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        MenuItem orderItem = null;
        for (MenuItem item : Menu.getItems()) {
            if (item.equals(fields[4])) {
                orderItem = item;
            }
        }

        Table orderTable = null;
        for (Table table : Table.getTables()) {
            if (table.getNumber() == Integer.parseInt(fields[3])) {
                orderTable = table;
            }
        }


        HashMap<String, Integer> orderModifications = new HashMap<>();
        for (int i = 5; i < fields.length; i++) {
            String[] modification = fields[i].split("\\s");
            orderModifications.put(modification[0], Integer.parseInt(modification[1]));
        }

        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Server) employee).takeOrder(orderItem, orderTable, orderModifications);
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Server employee deliver an order.
     *
     * @param fields the details of the order, formatted correctly into an array of string.
     */
    public static void processDeliverOrder(String[] fields) {
        int employeeId = Integer.parseInt(fields[3]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Server) employee).deliverOrder(Integer.parseInt(fields[3]));
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Cook employee start cooking an order.
     *
     * @param fields the details of the order, formatted correctly into an array of string.
     */
    public static void processStartCooking(String[] fields) throws IOException {
        int employeeId = Integer.parseInt(fields[1]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Cook) employee).startCooking(Integer.parseInt(fields[3]));
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Cook employee finish cooking an order.
     *
     * @param fields the details of the order, formatted correctly into an array of string.
     */
    public static void processFinishCooking(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Cook) employee).finishCooking(Integer.parseInt(fields[3]));
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Receiver employee receive
     * an ingredient shipment.
     *
     * @param fields the details of the shipment, formatted correctly into an array of string.
     */
    public static void processReceive(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        String ingredient = fields[3];
        int amount = Integer.parseInt(fields[4]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Receiver) employee).receive(ingredient, amount);
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Manager employee override
     * the default order amount of an ingredient with its new amount
     *
     * @param fields the details of the override, formatted correctly into an array of String
     */
    public static void processOverride(String[] fields) {
        String ingredient = fields[3];
        int new_amount = Integer.parseInt(fields[4]);
        int employeeId = Integer.parseInt(fields[1]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Manager) employee).overrideDefaultOrderAmount(ingredient, new_amount);
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Server employee return a rejected order.
     *
     * @param fields the details of the order, formatted correctly into an array of string.
     */
    public static void processRejected(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Server) employee).takeRejectedOrder(Integer.parseInt(fields[3]));
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Server get a Table's bill
     *
     * @param fields the details of the request, formatted correctly into an array of String
     */
    public static void processGetBill(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() == employeeId) {
                ((Server) employee).getBill(Table.getTables().get(Integer.parseInt(fields[3]) - 1));
            }
        }
    }

    /**
     * Given an event's data from events.txt, this method will make a Manager check the Inventory
     * and output it to the screen
     *
     * @param fields the details of the request, formatted correctly into an array of String
     */
    public static void processCheckInventory(String[] fields) {
        System.out.println("================================");
        System.out.println("Inventory checked by employee ID " + fields[1]);
        System.out.println(Manager.checkInventory());
        System.out.println("================================");
    }

    public static void processSignIn(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        System.out.println("================================");
        if (fields[0].equals("Server")) {
            Employee.getEmployees().add(new Server(employeeId));
            System.out.println(fields[0] + " ID " + fields[1] + " signed in.");
        } else if (fields[0].equals("Cook")) {
            Employee.getEmployees().add(new Cook(employeeId));
            System.out.println(fields[0] + " ID " + fields[1] + " signed in.");
        } else if (fields[0].equals("Receiver")) {
            Employee.getEmployees().add(new Receiver((employeeId)));
            System.out.println(fields[0] + " ID " + fields[1] + " signed in.");
        } else if (fields[0].equals("Manager")) {
            Employee.getEmployees().add(new Manager(employeeId));
            System.out.println(fields[0] + " ID " + fields[1] + " signed in.");
        } else {
            System.out.println("Sign in failed due to invalid Employee type.");
        }
        System.out.println("================================");
    }

    public static void processSignOut(String[] fields) {
        int employeeId = Integer.parseInt(fields[1]);
        boolean signed_out = false;
        Employee remove = null;
        for (Employee employee : Employee.getEmployees()) {
            if (employee.getId() ==  employeeId) {
                remove = employee;
                System.out.println("================================");
                System.out.println(fields[0] + " ID " + fields[1] + " signed out.");
                System.out.println("================================");
                signed_out = true;
            }
        }
        if (signed_out) {
            Employee.getEmployees().remove(remove);
        }
        if (!signed_out) {
            System.out.println("================================");
            System.out.println("Sign out failed due to invalid ID number.");
            System.out.println("================================");
        }
    }
}
