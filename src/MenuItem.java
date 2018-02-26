import java.util.HashMap;

public class MenuItem {

    private HashMap<String, Integer> ingredients;
    private String name;
    private double price;

    public MenuItem(String name, double price, HashMap<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public MenuItem(String name, float price) {
        this(name, price, new HashMap<>());
    }

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    public double getPrice(){
        return price;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof MenuItem) {
            MenuItem m = (MenuItem) o;
            return (m.name).equals(name);
        }
        return false;
    }
}
