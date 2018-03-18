package Restaurant.View;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import Restaurant.CookFXML;

public class RootLayoutController {

    private CookFXML cookFXML;

    public void setCookFXML(CookFXML cookFXML) {
        this.cookFXML = cookFXML;
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleChangeCook() {
        cookFXML.showCookGUI();
    }

    @FXML
    private void handleChangeServer() {
        cookFXML.showServerGUI();
    }
}
