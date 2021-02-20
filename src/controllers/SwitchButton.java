package controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


public class SwitchButton extends StackPane {

    private final Circle slider = new Circle();
    private final Label label = new Label();
    private final BooleanProperty switchedOn = new SimpleBooleanProperty(true);
    private static final PseudoClass SWITCHED_ON = PseudoClass.getPseudoClass("switched-on");
    private static final PseudoClass SWITCHED_OFF = PseudoClass.getPseudoClass("switched-off");

    public SwitchButton() {
        setSize();
        setStylesheetAndStyleClasses();
        setCurrentButtonStyle();
        addAction();
        getChildren().addAll(label, slider);
    }

    private void setSize(){
        setMinSize(60, 30);
        setPrefSize(60, 30);
        setMaxSize(60, 30);

        label.setMinSize(45, 30);
        label.setPrefSize(45, 30);
        label.setMaxSize(45, 30);

        slider.setRadius(11);
    }

    private void setStylesheetAndStyleClasses() {
        getStylesheets().add("css/switchButton.css");
        getStyleClass().add("switch-btn");
        slider.getStyleClass().add("switch-slider");
    }

    private void setCurrentButtonStyle(){
        if (switchedOn.get()) {
            setSwitchedOnStyle();
        } else {
            setSwitchedOffStyle();
        }
    }

    private void setSwitchedOnStyle(){
        pseudoClassStateChanged(SWITCHED_ON, true);
        pseudoClassStateChanged(SWITCHED_OFF, false);
        label.setText("Yes");
    }

    private void setSwitchedOffStyle(){
        pseudoClassStateChanged(SWITCHED_ON, false);
        pseudoClassStateChanged(SWITCHED_OFF, true);
        label.setText("No");
    }

    private void addAction() {
        switchedOn.addListener(e -> setCurrentButtonStyle());
    }

    public boolean isSwitchedOn() {
        return switchedOn.get();
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn.set(switchedOn);
    }
}