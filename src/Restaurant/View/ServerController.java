package Restaurant.View;

import Restaurant.CookFXML;
import Restaurant.Model.OrderModel;

import Restaurant.Model.Table;
import javafx.beans.property.SimpleStringProperty;
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
    }

    public void showOrderDetails(OrderModel order) {
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
}
