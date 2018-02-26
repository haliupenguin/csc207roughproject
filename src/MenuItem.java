import java.util.HashMap;

public class MenuItem {

    private HashMap<String, Integer> ingredients;
    private String name;

    public MenuItem(String name, HashMap<String, Integer> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public MenuItem(String name) {
        this(name, new HashMap<>());
    }

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }
}
