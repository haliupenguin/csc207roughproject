package Restaurant.View;

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

    public void initialize() {
    }

    public void setTableDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTable(Table table) {
        this.table = table;
        if (table.getNumCustomers() > 0) {
            customerNumberField.setText(Integer.toString(table.getNumCustomers()));
        }
    }
}
