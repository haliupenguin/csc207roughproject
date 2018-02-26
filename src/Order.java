import java.util.HashMap;

public class Order {

    private MenuItem orderedItem;
    private HashMap<String, Integer> changes; // Changes to the MenuItem
    private Table table;

    /*
     * State will be one of the following Strings:
     *  - "Sent" : the order was taken down by a server and sent to the Kitchen
     *  - "Seen" : the order has been seen by a cook and is being made
     *  - "Filled" : the order has been cooked and is waiting for Server to deliver to Table
     *  - "Delivered" : the order has been received happily by the Table
     *  - "Rejected" : the order was not satisfactory, must be remade
     */
    private String state;

    public Order(MenuItem menuItem, Table table) {
        this.orderedItem = menuItem;
        this.table = table;
        this.changes = new HashMap<>();
        this.state = "Sent";
    }

    public void add(String ingredient) {
        if (orderedItem.getIngredients().containsKey(ingredient)) {
            changes.put(ingredient, 1);
        }
    }

    public void remove(String ingredient) {
        if (orderedItem.getIngredients().containsKey(ingredient)) {
            changes.put(ingredient, -1);
        }
    }

    public MenuItem getOrderedItem() {
        return orderedItem;
    }

    public String getState(){
        return state;
    }

    protected void setState(String newState) {
        this.state = newState;
    }

    public String toString() {
        StringBuilder toReturn = new StringBuilder(orderedItem.toString());
        toReturn.append('\n');
        if (changes.keySet().size() > 0) {
            toReturn.append("=== Changes ===\n");
            for (String ingredient : changes.keySet()) {
                if (changes.get(ingredient) == 1) {
                    toReturn.append("   -Extra ");
                }
                else {
                    toReturn.append("   -Less ");
                }
                toReturn.append(ingredient);
                toReturn.append('\n');
            }
        }
        return new String(toReturn);
    }

}
