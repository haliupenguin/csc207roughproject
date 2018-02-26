import java.util.ArrayList;

public class Menu {

    private ArrayList<MenuItem> menuItems;

    public Menu(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Menu() {
        this(new ArrayList<>());
    }

    public void addMenuItem(MenuItem menuItem) {
        if (!menuItems.contains(menuItem)) {
            menuItems.add(menuItem);
        }
    }

}
