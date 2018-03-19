package Restaurant.View;

import Restaurant.CookFXML;
import Restaurant.Model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TableDialogController {

    @FXML
    private TextField customerNumberField;
    @FXML
    private TextArea orderText;

    private Stage dialogStage;
    private Table table;

    private CookFXML cookFXML;

    public void initialize() {
        showOrderDetails(table);
    }

    private void showOrderDetails(Table table) {
        if (table == null) {
            orderText.setText("");
        } else {

        }
    }

    public void setTableDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(CookFXML cookFXML) {
        this.cookFXML = cookFXML;
    }

    public void setTable(Table table) {
        this.table = table;
        if (table.getNumCustomers() > 0) {
            customerNumberField.setText(Integer.toString(table.getNumCustomers()));
        }
    }
}
