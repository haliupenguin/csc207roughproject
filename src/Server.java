import java.util.ArrayList;

public class Server extends Employee {

    public Server(int id, Restaurant restaurant) {
        super(id, restaurant);
    }

    public void takeOrder(MenuItem menuItem, Table table, ArrayList<String> additions, ArrayList<String> subtractions) {
        Order order = new Order(menuItem, table);
        for (String addition : additions) {
            order.add(addition);
        }
        for (String subtract : subtractions) {
            order.remove(subtract);
        }
        table.addOrder(order);
        Cook.receiveOrder(order);
    }

    public void takeOrder(MenuItem menuItem, int table, ArrayList<String> additions, ArrayList<String> subtractions) {
        if (table < restaurant.tables.size()) {
            takeOrder(menuItem, restaurant.tables.get(table), additions, subtractions);
        }
    }

    public void deliverOrder(Order order) {
        if (order.getState().equals("Filled")) {
            Cook.removeOrder(order);
            order.setState("Delivered");
        }
    }

    public void takeRejectedOrder(Order order) {
        order.setState("Sent");
        Cook.receiveOrder(order);
    }




}
