import java.util.ArrayList;

public class Table {

    private ArrayList<Order> orders;
    private int tableNum;

    public Table(int tableNum) {
        this.orders = new ArrayList<>();
        this.tableNum = tableNum;
    }

    public String getBill() {
        StringBuilder toReturn = new StringBuilder("Table #" + tableNum);
        toReturn.append('\n');
        int totalCost = 0;
        for (Order order : orders) {
            if (order.getState().equals("delivered")) {
                toReturn.append(order.toString());
                totalCost += order.getOrderedItem().getPrice();
                toReturn.append("Price: " + order.getOrderedItem().getPrice());
                toReturn.append('\n');
            }
        }
        toReturn.append("Total cost: " + totalCost);
        return new String(toReturn);
    }
}