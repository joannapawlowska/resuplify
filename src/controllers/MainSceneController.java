package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML private AnchorPane predictionPane;
    @FXML private AnchorPane settingsPane;
    @FXML private AnchorPane logOutPane;
    @FXML private RadioButton predictionPaneBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultSelectedPane();
    }

    private void setDefaultSelectedPane(){
        predictionPaneBtn.fire();
    }

    @FXML
    private void handlePredictionPaneBtn(ActionEvent actionEvent) {
        predictionPane.toFront();
    }

    @FXML
    private void handleSettingsPaneBtn(ActionEvent actionEvent) {
        settingsPane.toFront();
    }

    @FXML
    private void handleLogOutPaneBtn(ActionEvent actionEvent) {
        logOutPane.toFront();
    }
}