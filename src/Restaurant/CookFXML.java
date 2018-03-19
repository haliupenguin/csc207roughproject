package Restaurant;

import Restaurant.Model.*;
import Restaurant.View.OrderOverviewController;
import Restaurant.View.RootLayoutController;
import Restaurant.View.ServerController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class CookFXML extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<OrderModel> orderData = FXCollections.observableArrayList();
    private ObservableList<Table> tableData = FXCollections.observableArrayList();

    public CookFXML() {
        HashMap<String, Integer> burgerIngredients = new HashMap<>();
        burgerIngredients.put("Lettuce", 1);
        burgerIngredients.put("Bun", 1);
        burgerIngredients.put("Meat", 1);
        HashMap<String, Integer> saladIngredients = new HashMap<>();
        saladIngredients.put("Lettuce", 1);
        saladIngredients.put("Tomato", 1);
        MenuItem burger = new MenuItem(burgerIngredients, "Burger", 4.50);
        MenuItem salad = new MenuItem(saladIngredients, "Salad", 3.50);
        orderData.add(new OrderModel(burger, new HashMap<>(), 1, 1));
        HashMap<String, Integer> mod = new HashMap<>();
        mod.put("Lettuce", 1);
        orderData.add(new OrderModel(salad, new HashMap<>(), 1, 2));
        orderData.add(new OrderModel(burger, mod, 2, 1));

        Menu.addItem(burger);
        Menu.addItem(salad);

        Table table1 = new Table(1, 2);
        Table table2 = new Table(2, 1);
        tableData.add(table1);
        tableData.add(table2);

        Inventory.addIngredient("Bun", 40, 20, 20);
        Inventory.addIngredient("Lettuce", 40, 20, 20);
        Inventory.addIngredient("Meat", 49,20,20);
        Inventory.addIngredient("Tomato",60,20,20);

    }

    public ObservableList<OrderModel> getOrderData() {
        return orderData;
    }

    public ObservableList<Table> getTableData() {
        return tableData;
    }

    public ObservableList<SimpleStringProperty> getIngredientData(){
        ObservableList<SimpleStringProperty> data = FXCollections.observableArrayList();
        for (String ingredient : Inventory.getIngredients()) {
            data.add(new SimpleStringProperty(ingredient));
        }
        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cook Program");

        initRootLayout();

        showServerGUI();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CookFXML.class.getResource("View/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setCookFXML(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCookGUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CookFXML.class.getResource("View/CookFXML.fxml"));
            AnchorPane cookGui = (AnchorPane) loader.load();

            rootLayout.setCenter(cookGui);

            OrderOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showServerGUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CookFXML.class.getResource("View/Server.fxml"));
            AnchorPane serverGui = (AnchorPane) loader.load();

            rootLayout.setCenter(serverGui);

            ServerController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openTableDialog() {

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
