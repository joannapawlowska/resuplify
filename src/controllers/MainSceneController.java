package controllers;

import components.Preference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class MainSceneController {

    @FXML private AnchorPane mainScene;
    @FXML private AnchorPane prediction;
    @FXML private AnchorPane settings;
    @FXML private AnchorPane logOut;
    @FXML private RadioButton predictionPaneBtn;
    @FXML public SettingsPaneController settingsPaneController;

    public void initialize() {
        setHomePane();
        setMode(Preference.isDarkMode());
        settingsPaneController.injectMainController(this);
    }

    private void setHomePane(){
        predictionPaneBtn.fire();
    }

    public void setMode(boolean darkMode){
        if(darkMode)
            setDarkMode();
        else
            setLightMode();
    }

    public void setDarkMode(){
        mainScene.getStylesheets().remove("css/light-mode.css");
        mainScene.getStylesheets().add("css/dark-mode.css");
    }

    public void setLightMode(){
        mainScene.getStylesheets().remove("css/dark-mode.css");
        mainScene.getStylesheets().add("css/light-mode.css");
    }

    @FXML
    private void handlePredictionPaneBtn(ActionEvent actionEvent) {
        prediction.toFront();
    }

    @FXML
    private void handleSettingsPaneBtn(ActionEvent actionEvent) {
        settings.toFront();
    }

    @FXML
    private void handleLogOutPaneBtn(ActionEvent actionEvent) {
        logOut.toFront();
    }
}