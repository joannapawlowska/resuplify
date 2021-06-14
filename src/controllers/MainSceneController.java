package controllers;

import components.logic.Preference;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class MainSceneController {

    @FXML private AnchorPane mainScene;
    @FXML private AnchorPane prediction;
    @FXML private AnchorPane settings;
    @FXML private AnchorPane logOut;
    @FXML private RadioButton predictionPaneBtn;
    @FXML SettingsPaneController settingsPaneController;
    @FXML PredictionPaneController predictionPaneController;
    @FXML LogOutPaneController logOutPaneController;

    public void initialize() {
        setHomePane();
        setMode(Preference.isDarkMode());
        settingsPaneController.injectMainController(this);
        predictionPaneController.injectLogOutPaneController(logOutPaneController);
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

    private void setDarkMode(){
        mainScene.getStylesheets().remove("css/light-mode.css");
        mainScene.getStylesheets().add("css/dark-mode.css");
    }

    private void setLightMode(){
        mainScene.getStylesheets().remove("css/dark-mode.css");
        mainScene.getStylesheets().add("css/light-mode.css");
    }

    @FXML
    private void handlePredictionPaneBtn() {
        prediction.toFront();
    }

    @FXML
    private void handleSettingsPaneBtn() {
        settings.toFront();
    }

    @FXML
    private void handleLogOutPaneBtn() {
        logOut.toFront();
    }
}