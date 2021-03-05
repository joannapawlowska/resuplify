package controllers;

import components.Preference;
import components.SwitchButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SettingsPaneController {

    @FXML public Label lightModeLabel;
    @FXML public Label darkModeLabel;
    @FXML private TextField deliveryTextField;
    @FXML private HBox lightModeBox, darkModeBox;

    private SwitchButton lightModeBtn, darkModeBtn;
    private MainSceneController mainSceneController;

    public void initialize() {
        addModeButtonsToPane();
        setModeButtonStates();
        setModeLabels();
        addActionToModeButtons();
        forceDeliveryTextFieldToBeNumericOnly();
        setDeliveryText();
    }

    private void forceDeliveryTextFieldToBeNumericOnly() {
        deliveryTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isPositiveInteger(newValue)) {
                    deliveryTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private boolean isPositiveInteger(String text){
        return text.matches("\\d+");
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
    }
}