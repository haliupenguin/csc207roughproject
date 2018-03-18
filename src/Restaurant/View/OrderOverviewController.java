package Restaurant.View;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import Restaurant.CookFXML;
import Restaurant.Model.OrderModel;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderOverviewController {

    @FXML
    private TableView<OrderModel> orderTable;
    @FXML
    private TableColumn<OrderModel, Integer> orderNumberColumn;
    @FXML
    private TableColumn<OrderModel, String> orderStatusColumn;

    @FXML
    private Label orderNameLabel;
    @FXML
    private TextArea ingredientsTextArea;

    @FXML
    private Button acknowledge;
    @FXML
    private Button startCooking;
    @FXML
    private Button finishCooking;
    @FXML
    private Button cancelOrder;

    private CookFXML cookFXML;

    public OrderOverviewController() {
    }

    @FXML
    private void initialize() {
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        showOrderDetails(null);

        orderTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));
    }

    public void setMainApp(CookFXML cookFXML) {
        this.cookFXML = cookFXML;

        orderTable.setItems(cookFXML.getOrderData());
    }

    public void showOrderDetails(OrderModel order) {
        if (order != null) {
            orderNameLabel.setText(order.getName());
            StringBuilder ingredientsDisplay = new StringBuilder();
            HashMap<String, Integer> ingredientsNeeded = order.getIngredientsNeeded();
            for (String ingredient : ingredientsNeeded.keySet()) {
                ingredientsDisplay.append(ingredient);
                ingredientsDisplay.append(": ");
                ingredientsDisplay.append(ingredientsNeeded.get(ingredient));
                ingredientsDisplay.append('\n');
            }
            ingredientsTextArea.setText(ingredientsDisplay.toString());
        } else {
            orderNameLabel.setText("");
            ingredientsTextArea.setText("");
        }
    }

    private boolean checkPreviousOrders(TableView tableView, OrderModel orderToCheck) {
        int index = orderToCheck.getOrderId();
        ObservableList<OrderModel> list = tableView.getItems();
        for (OrderModel order : list) {
            if (order.getOrderId() < index && order.getStatus().equals("Not Acknowledged")) {
                return false;
            }
        }
        return true;
    }

    private void checkPreviousAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText("Please acknowledge the orders before this order");

        alert.showAndWait();
    }

    public void handleAcknowledge() {
        try {
            OrderModel order = orderTable.getSelectionModel().getSelectedItem();
            if (checkPreviousOrders(orderTable, order) && order.getStatus().equals("Not Acknowledged")) {
                order.setStatus("Acknowledged");
            } else if (!order.getStatus().equals("Not Acknowledged")) {
            } else {
                checkPreviousAlert();
            }
        } catch (NullPointerException e) {
        }
    }

    public void handleStartCooking() {
        try {
            OrderModel order = orderTable.getSelectionModel().getSelectedItem();
            if ((order.getStatus().equals("Not Acknowledged") ||
                    order.getStatus().equals("Acknowledged")) &&
                    checkPreviousOrders(orderTable, order)) {
                order.setStatus("Cooking");
            } else if (!(order.getStatus().equals("Not Acknowledged") ||
                    order.getStatus().equals("Acknowledged"))) {
            } else {
                checkPreviousAlert();
            }
        } catch (NullPointerException e) {
        }
    }

    public void handleFinishCooking() {
        try {
            OrderModel order = orderTable.getSelectionModel().getSelectedItem();
            if (order.getStatus().equals("Cooking")) {
                order.setStatus("Cooked");
            }
        } catch (NullPointerException e) {
        }
    }

    public void handleCancelOrder() {
        try {
            OrderModel order = orderTable.getSelectionModel().getSelectedItem();
            order.setStatus("Cancelled");
        } catch (NullPointerException e) {
        }
    }
}
