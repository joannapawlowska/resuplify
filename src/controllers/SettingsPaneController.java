package controllers;

import components.NonNegativeIntegerTextField;
import components.Preference;
import components.SwitchButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SettingsPaneController {

    @FXML private Label lightModeLabel;
    @FXML private Label darkModeLabel;
    @FXML private HBox deliveryBox;
    @FXML private HBox lightModeBox, darkModeBox;

    private NonNegativeIntegerTextField deliveryTextField;
    private SwitchButton lightModeBtn, darkModeBtn;
    private MainSceneController mainSceneController;

    public void initialize() {
        addModeButtonsToPane();
        setModeButtonStates();
        addDeliveryTextFieldToPane();
        setModeLabels();
        addActionToModeButtons();
        setDeliveryText();
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
        lightModeBtn.setSwitchedOn(Preference.isLightMode());
        darkModeBtn.setSwitchedOn(Preference.isDarkMode());
    }

    private void addDeliveryTextFieldToPane() {
        deliveryTextField = new NonNegativeIntegerTextField();
        deliveryTextField.setPrefSize(35, 26);
        deliveryBox.getChildren().add(1, deliveryTextField);
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

    private void setDeliveryText() {
        deliveryTextField.setText(String.valueOf(Preference.getDeliveryDuration()));
    }

    @FXML
    private void handleCancelBtn(ActionEvent event){
        lightModeBtn.setSwitchedOn(Preference.isLightMode());
        darkModeBtn.setSwitchedOn(Preference.isDarkMode());
        setModeLabels();
        setDeliveryText();
    }

    @FXML
    private void handleSaveBtn(ActionEvent event){
        Preference.updateDarkMode(darkModeBtn.isSwitchedOn());
        Preference.updateLightMode(lightModeBtn.isSwitchedOn());
        Preference.updateDeliveryDuration(Integer.parseInt(deliveryTextField.getText()));
        mainSceneController.setMode();
        mainSceneController.predictionPaneController.updateDeliveryTextField();
    }
}