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

    public String toString() {
        StringBuilder toReturn = new StringBuilder("=== Menu ===\n");
        for (MenuItem item : menuItems) {
            toReturn.append(item.toString());
            toReturn.append("    ");
            toReturn.append(item.getPrice());
            toReturn.append('\n');
        }
        return new String(toReturn);
    }

    public boolean equals(Object o) {
        if (o instanceof Menu) {
            Menu m = (Menu) o;
            return menuItems.equals(m.menuItems);
        }
        return false;
    }

}
