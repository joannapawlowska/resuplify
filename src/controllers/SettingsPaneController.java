package controllers;

import components.Settings;
import components.SwitchButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SettingsPaneController {

    @FXML public Label lightModeLabel;
    @FXML public Label darkModeLabel;
    @FXML private TextField deliveryDuration;
    @FXML private HBox lightModeBox, darkModeBox;

    private SwitchButton lightModeBtn, darkModeBtn;
    private MainSceneController mainSceneController;

    public void initialize() {
        addModeButtonsToPane();
        setModeButtonStates();
        setModeLabels();
        addActionToModeButtons();
    }

    public void injectMainController(MainSceneController msc) {
        mainSceneController = msc;
    }

    private void addModeButtonsToPane() {
        lightModeBtn = new SwitchButton();
        darkModeBtn = new SwitchButton();
        lightModeBox.getChildren().add(1, lightModeBtn);
        darkModeBox.getChildren().add(1, darkModeBtn);
    }

    private void setModeButtonStates(){
        lightModeBtn.setSwitchedOn(Settings.isLightMode());
        darkModeBtn.setSwitchedOn(Settings.isDarkMode());
    }

    private void setModeLabels(){
        lightModeLabel.setText(lightModeBtn.isSwitchedOn() ? "On" : "Off");
        darkModeLabel.setText(darkModeBtn.isSwitchedOn() ? "On" : "Off");
    }

    private void addActionToModeButtons(){
        lightModeBtn.setOnMouseClicked(mouseEvent -> reverseModeButtonStates());
        darkModeBtn.setOnMouseClicked(mouseEvent -> reverseModeButtonStates());
    }

    private void reverseModeButtonStates(){
        lightModeBtn.setSwitchedOn(!lightModeBtn.isSwitchedOn());
        darkModeBtn.setSwitchedOn(!darkModeBtn.isSwitchedOn());
        setModeLabels();
    }

    @FXML
    private void handleCancelBtn(ActionEvent event){
        lightModeBtn.setSwitchedOn(Settings.isLightMode());
        darkModeBtn.setSwitchedOn(Settings.isDarkMode());
    }

    @FXML
    private void handleSaveBtn(ActionEvent event){
        Settings.updateDarkMode(darkModeBtn.isSwitchedOn());
        Settings.updateLightMode(lightModeBtn.isSwitchedOn());
        mainSceneController.setMode();
    }
}