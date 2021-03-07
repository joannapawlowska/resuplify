package controllers;

import components.NonNegativeIntegerTextField;
import components.Preference;
import components.SwitchButton;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

public class SettingsPaneController {

    @FXML private Label darkModeLabel;
    @FXML private HBox deliveryBox;
    @FXML private HBox darkModeBox;

    private MainSceneController mainSceneController;
    private NonNegativeIntegerTextField deliveryTextField;
    private SwitchButton darkModeBtn;

    public void initialize() {
        addChildrenToPane();
        addActionToModeButton();
        addListenerToModeButtonState();

        setPreferredModeButtonState();
        setPreferredDeliveryTextField();
    }

    public void injectMainController(MainSceneController controller) {
        mainSceneController = controller;
    }

    private void addChildrenToPane() {
        addModeButton();
        addDeliveryTextField();
    }

    private void addModeButton() {
        darkModeBtn = new SwitchButton();
        darkModeBox.getChildren().add(1, darkModeBtn);
    }

    private void addDeliveryTextField() {
        deliveryTextField = new NonNegativeIntegerTextField();
        deliveryTextField.setPrefSize(35, 26);
        deliveryBox.getChildren().add(1, deliveryTextField);
    }

    private void addActionToModeButton(){
        darkModeBtn.setOnMouseClicked(mouseEvent -> reverseModeButtonState());
    }

    private void reverseModeButtonState(){
        darkModeBtn.setSwitchedOn(!darkModeBtn.isSwitchedOn());
    }

    public void addListenerToModeButtonState(){
        darkModeBtn.getSwitchedOnProperty().addListener((change) -> {
            darkModeLabel.setText(darkModeBtn.isSwitchedOn() ? "On" : "Off");
            mainSceneController.setMode(darkModeBtn.isSwitchedOn());
        } );
    }

    private void setPreferredModeButtonState(){
        darkModeBtn.setSwitchedOn(Preference.isDarkMode());
    }

    private void setPreferredDeliveryTextField() {
        deliveryTextField.setText(String.valueOf(Preference.getDeliveryDuration()));
    }

    @FXML
    private void handleCancelBtn(ActionEvent event){
        setPreferredModeButtonState();
        setPreferredDeliveryTextField();
    }

    @FXML
    private void handleSaveBtn(ActionEvent event){
        Preference.updateDarkMode(darkModeBtn.isSwitchedOn());
        Preference.updateDeliveryDuration(Integer.parseInt(deliveryTextField.getText()));
    }
}