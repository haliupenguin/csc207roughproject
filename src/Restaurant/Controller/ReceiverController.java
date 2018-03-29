package Restaurant.Controller;

import Restaurant.MainApplication;
import Restaurant.Model.Delivery;
import Restaurant.Model.Receiver;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the View that Receivers see
 *
 * Controller has functions to receive deliveries
 */
public class ReceiverController implements Observer {

    @FXML
    private TableView<Delivery> deliveryTable;
    @FXML
    private TableColumn<Delivery, Integer> deliveryColumn;
    @FXML
    private TableView<SimpleStringProperty> inventoryTable;
    @FXML
    private TableColumn<SimpleStringProperty, String> inventoryColumn;

    @FXML
    private TextArea deliveryStatusArea;
    @FXML
    private TextArea deliveryContentsArea;

    private MainApplication mainApp;
    private Receiver receiver;
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    /**
     * Initializes a ReceiverController object
     */
    public ReceiverController() {
    }

    /**
     * Sets the MainApplication that this ReceiverController belongs to. Sets the items in the
     * table of deliveries and inventory
     *
     * @param mainApp the MainApplication that this ReceiverController belongs to
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;

        deliveryTable.setItems(Delivery.getDeliveries());
        inventoryTable.setItems(mainApp.getIngredientData());
    }

    /**
     * Sets the Receiver belonging to this controller
     *
     * @param receiver the Receiver belonging to this controller
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Initializes the display for the Receiver
     */
    @FXML
    public void initialize() {
        deliveryColumn.setCellValueFactory(cellData -> cellData.getValue().idNumberProperty().asObject());
        inventoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get() + " " +
                        Integer.toString(mainApp.getInventory().getInventory().get(cellData.getValue().get()))));

        showDeliveryDetails(null);

        deliveryTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDeliveryDetails(newValue));
    }

    /**
     * Displays the information about a Delivery to the screen
     *
     * @param delivery the Delivery to be displayed
     */
    private void showDeliveryDetails(Delivery delivery) {
        if (delivery == null) {
            deliveryContentsArea.setText("");
            deliveryStatusArea.setText("");
        } else {
            if (delivery.isReceived()) {
                deliveryStatusArea.setText("Received");
            } else {
                deliveryStatusArea.setText("Not received");
            }
            deliveryContentsArea.setText(delivery.toString());
        }
    }

    /**
     * Handles the event where a Receiver confirms that a shipment of ingredients has been
     * received
     *
     * This puts all the ingredients part of the shipment to be put into Inventory.
     */
    public void handleConfirmReceived() {
        Delivery delivery = deliveryTable.getSelectionModel().getSelectedItem();
        if (delivery != null) {
            if (!delivery.isReceived()) {
                delivery.receive();

                logger.log(Level.INFO, "Delivery #" + delivery.getIdNumber() + " has been received");

                for (String ingredient : delivery.getIngredients().keySet()) {
                    receiver.receive(ingredient, delivery.getIngredients().get(ingredient));
                }

                showDeliveryDetails(delivery);
                inventoryTable.setItems(mainApp.getIngredientData());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("This delivery has already been received");

                alert.showAndWait();
            }
        }
    }

    /**
     * Displays an information pop-up when this ReceiverController has been notified
     *
     * @param o   the Observable giving the notification
     * @param arg the message of the notification
     */
    @Override
    public void update(Observable o, Object arg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setContentText((String) arg);

        alert.showAndWait();
    }
}
