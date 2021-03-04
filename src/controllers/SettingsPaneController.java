package controllers;

import components.Settings;
import components.SwitchButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SettingsPaneController {

    @FXML
    private TextField deliveryDuration;

    @FXML
    private HBox lightModeBox, darkModeBox;

    private SwitchButton lightModeBtn, darkModeBtn;

    private MainSceneController mainSceneController;

    public void initialize() {
        addModeButtonsToPane();
        setSettings();
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

    private void setSettings(){
        lightModeBtn.setSwitchedOn(Settings.isLightMode());
        darkModeBtn.setSwitchedOn(Settings.isDarkMode());
    }

    private void addActionToModeButtons(){
        handleLightModeBtnAction();
        handleDarkModeBtnAction();
    }

    private void handleLightModeBtnAction(){
        lightModeBtn.setOnMouseClicked(mouseEvent -> {
            reverseModeButtonStates();
        });
    }

    private void setMode(){
        if(lightModeBtn.isSwitchedOn())
            mainSceneController.setLightMode();
        else
            mainSceneController.setDarkMode();
    }

    private void handleDarkModeBtnAction(){
        darkModeBtn.setOnMouseClicked(mouseEvent -> {
            reverseModeButtonStates();
        });
    }

    private void reverseModeButtonStates(){
        lightModeBtn.setSwitchedOn(!lightModeBtn.isSwitchedOn());
        darkModeBtn.setSwitchedOn(!darkModeBtn.isSwitchedOn());
    }


    @FXML
    private void handleCancelBtn(ActionEvent event){
        lightModeBtn.setSwitchedOn(Settings.isLightMode());
        darkModeBtn.setSwitchedOn(Settings.isDarkMode());
        mainSceneController.setMode();
    }

    @FXML
    private void handleSaveBtn(ActionEvent event){
        Settings.updateDarkMode(darkModeBtn.isSwitchedOn());
        Settings.updateLightMode(lightModeBtn.isSwitchedOn());
        mainSceneController.setMode();
    }

}