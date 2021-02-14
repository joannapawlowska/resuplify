package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML public AnchorPane predictionPane, settingsPane, logOutPane;

    @FXML public RadioButton predictionPaneBtn, settingsPaneBtn, logOutPaneBtn;

//    @FXML public Button minimizeBtn, maximizeBtn, closeBtn;

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

//    @FXML
//    private void handleMinimizeButton(ActionEvent actionEvent) {
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.setIconified(true);
//    }
//
//    @FXML
//    private void handleMaximizeButton(ActionEvent actionEvent) {
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.setMaximized(true);
//    }
//
//    @FXML
//    private void handleCloseButton(ActionEvent actionEvent) {
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.close();
//    }
}