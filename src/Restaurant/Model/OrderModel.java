package Restaurant.Model;

import javafx.beans.property.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Set;

public class OrderModel {

    private static int id = 1;

    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty tableNumber;
    private final IntegerProperty orderId;
    private final IntegerProperty customerNum;
    private final ObjectProperty<HashMap<String, Integer>> ingredientsNeeded;
    private final StringProperty status;

    public OrderModel(MenuItem item, HashMap<String, Integer> modifications,
                      int tableNumber, int customerNum) {
        this.name = new SimpleStringProperty(item.getName());
        this.price = new SimpleDoubleProperty(item.getPrice());
        this.tableNumber = new SimpleIntegerProperty(tableNumber);
        this.orderId = new SimpleIntegerProperty(id++);
        this.customerNum = new SimpleIntegerProperty(customerNum);
        this.ingredientsNeeded = new SimpleObjectProperty<>(new HashMap<>(item.getIngredientsNeeded()));
        this.modifyIngredients(modifications);
        this.status = new SimpleStringProperty("Not Acknowledged");
    }

    private void modifyIngredients(HashMap<String, Integer> modifications) {
        HashMap<String, Integer> tempIngredientsNeeded = ingredientsNeeded.get();
        Set<String> currentIngredients = tempIngredientsNeeded.keySet();

        for (String ingredient : modifications.keySet()) {
            if (currentIngredients.contains(ingredient)) {
                int new_value = tempIngredientsNeeded.get(ingredient) + modifications.get(ingredient);
                if (new_value < 0) {
                    new_value = 0;
                }
                tempIngredientsNeeded.replace(ingredient, new_value);
            } else if (modifications.get(ingredient) > 0) {
                tempIngredientsNeeded.put(ingredient, modifications.get(ingredient));
            }
        }
        ingredientsNeeded.set(tempIngredientsNeeded);
    }


    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Order #" + orderId + "\n" + "Table #" + tableNumber + "\n" + name + " " + formatter.format(price);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getTableNumber() {
        return tableNumber.get();
    }

    public IntegerProperty tableNumberProperty() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber.set(tableNumber);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public HashMap<String, Integer> getIngredientsNeeded() {
        return ingredientsNeeded.get();
    }

    public ObjectProperty<HashMap<String, Integer>> ingredientsNeededProperty() {
        return ingredientsNeeded;
    }

    public void setIngredientsNeeded(HashMap<String, Integer> ingredientsNeeded) {
        this.ingredientsNeeded.set(ingredientsNeeded);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getCustomerNum() {
        return customerNum.get();
    }

    public IntegerProperty customerNumProperty() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum.set(customerNum);
    }
}
