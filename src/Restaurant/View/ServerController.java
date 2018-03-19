package Restaurant.View;

import Restaurant.CookFXML;
import Restaurant.Model.OrderModel;
import Restaurant.Model.Menu;
import Restaurant.Model.MenuItem;
import Restaurant.Model.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class ServerController {

    @FXML
    private TableView<OrderModel> orderTable;
    @FXML
    private TableColumn<OrderModel, Integer> orderNumberColumn;
    @FXML
    private TableColumn<OrderModel, String> orderStatusColumn;

    @FXML
    private Label tableNumberLabel;
    @FXML
    private Label customerNumberLabel;
    @FXML
    private Label menuItemLabel;

    @FXML
    private Button deliverOrder;
    @FXML
    private Button rejectedOrder;

    @FXML
    private ChoiceBox<MenuItem> menuChoices;
    @FXML
    private ChoiceBox<Table> tableChoices;
    @FXML
    private ChoiceBox<Integer> customerChoices;

    @FXML
    private TextArea modificationsText;

    @FXML
    private TableView<Table> tableTables;
    @FXML
    private TableColumn<Table, Integer> tableNumberColumn;
    @FXML
    private TableView<SimpleStringProperty> ingredientTable;
    @FXML
    private TableColumn<SimpleStringProperty, String> ingredientColumn;

    private CookFXML cookFXML;

    private HashMap<String, Integer> modifications;

    public ServerController() {
        this.modifications = new HashMap<>();
    }

    public void setMainApp(CookFXML cookFXML) {
        this.cookFXML = cookFXML;

        orderTable.setItems(cookFXML.getOrderData());
        tableTables.setItems(cookFXML.getTableData());
        ingredientTable.setItems(cookFXML.getIngredientData());

        menuChoices.setItems(Menu.getItems());
        tableChoices.setItems(Table.getTables());
    }

    @FXML
    private void initialize() {
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        showOrderDetails(null);

        orderTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));

        tableNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());

        ingredientColumn.setCellValueFactory(cellData -> cellData.getValue());

        tableChoices.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setTableChoice(newValue));
    }

    public void setTableChoice(Table table) {
        int customerNumber = table.getNumCustomers();
        ObservableList<Integer> customerIndexList = FXCollections.observableArrayList();
        for (int i = 1; i <= customerNumber; i++) {
            customerIndexList.add(i);
        }
        customerChoices.setItems(customerIndexList);
    }

    private void showOrderDetails(OrderModel order) {
        if (order != null) {
            tableNumberLabel.setText(Integer.toString(order.getTableNumber()));
            customerNumberLabel.setText(Integer.toString(order.getCustomerNum()));
            menuItemLabel.setText(order.getName());
        } else {
            tableNumberLabel.setText("");
            customerNumberLabel.setText("");
            menuItemLabel.setText("");
        }
    }

    private void showModificationDetails() {
        StringBuilder builder = new StringBuilder();
        for (String ingredient : modifications.keySet()) {
            int value = modifications.get(ingredient);
            if (value == 0) {
                modifications.remove(ingredient, 0);
            } else {
                if (value > 0) {
                    builder.append('+');
                }
                builder.append(value);
                builder.append(' ');
                builder.append(ingredient);
                builder.append('\n');
            }
        }
        modificationsText.setText(builder.toString());
    }

    public void handleDeliver() {
        try {
            OrderModel order = orderTable.getSelectionModel().getSelectedItem();
            if (order.getStatus().equals("Cooked")) {
                order.setStatus("Delivered");
            }
        } catch (NullPointerException e) {
        }
    }

    public void handleRejectedOrder() {
        try {
            OrderModel order = orderTable.getSelectionModel().getSelectedItem();
            if (order.getStatus().equals("Cooked") || order.getStatus().equals("Delivered")) {
                order.setStatus("Not Acknowledged");
            }
        } catch (NullPointerException e) {
        }
    }

    public void handleAddIngredient() {
        try {
            String ingredient = ingredientTable.getSelectionModel().getSelectedItem().get();
            if (modifications.keySet().contains(ingredient)) {
                modifications.replace(ingredient, modifications.get(ingredient) + 1);
            } else {
                modifications.put(ingredient, 1);
            }
            showModificationDetails();
        } catch (NullPointerException e) {
        }
    }

    public void handleSubtractIngredient() {
        try {
            String ingredient = ingredientTable.getSelectionModel().getSelectedItem().get();
            if (modifications.keySet().contains(ingredient)) {
                modifications.replace(ingredient, modifications.get(ingredient) - 1);
            } else {
                modifications.put(ingredient, -1);
            }
            showModificationDetails();
        } catch (NullPointerException e){
        }
    }
}
