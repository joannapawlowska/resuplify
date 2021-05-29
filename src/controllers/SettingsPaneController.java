package controllers;

import components.view.NonNegativeIntegerTextField;
import components.logic.Preference;
import components.view.SwitchButton;

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

        setPreferredModeButtonState();
        setPreferredDeliveryTextField();
        setModeLabel();

        addListenerToModeButtonState();
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
        darkModeBtn.setOnMouseClicked(mouseEvent -> reverseModeButtonState() );
    }

    private void reverseModeButtonState(){
        darkModeBtn.setSwitchedOn(!darkModeBtn.isSwitchedOn());
    }

    private void setPreferredModeButtonState(){
        darkModeBtn.setSwitchedOn(Preference.isDarkMode());
    }

    private void setPreferredDeliveryTextField() {
        deliveryTextField.setText(String.valueOf(Preference.getDeliveryDuration()));
    }

    private void setModeLabel() {
        darkModeLabel.setText(darkModeBtn.isSwitchedOn() ? "On" : "Off");
    }

    public void addListenerToModeButtonState(){
        darkModeBtn.getSwitchedOnProperty().addListener((change) -> {
            setModeLabel();
            mainSceneController.setMode(darkModeBtn.isSwitchedOn());
        } );
    }

    @FXML
    private void handleCancelBtn(){
        setPreferredModeButtonState();
        setPreferredDeliveryTextField();
    }

    @FXML
    private void handleSaveBtn(){
        Preference.updateDarkMode(darkModeBtn.isSwitchedOn());
        Preference.updateDeliveryDuration(Integer.parseInt(deliveryTextField.getText()));
        mainSceneController.predictionPaneController.updateDeliveryTextField();
    }
}