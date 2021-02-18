package controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


public class SwitchButton extends StackPane {

    private Circle slider = new Circle(11);

    private Label label = new Label();

    private BooleanProperty switchedOn = new SimpleBooleanProperty(true);

    private static final PseudoClass SWITCHED_ON = PseudoClass.getPseudoClass("switched-on");

    private static final PseudoClass SWITCHED_OFF = PseudoClass.getPseudoClass("switched-off");

    public SwitchButton() {
        init();
        addAction();
    }

    private void init() {
        setStyle();
        getChildren().addAll(label, slider);
    }

    private void setStyle() {
        setMinSize(60, 30);
        setPrefSize(60, 30);
        setMaxSize(60, 30);

        label.setMinSize(45, 30);
        label.setPrefSize(45, 30);
        label.setMaxSize(45, 30);

        getStylesheets().add("css/switchButton.css");
        getStyleClass().add("switch-btn");
        slider.getStyleClass().add("switch-slider");

        setButtonState();
    }

    private void setButtonState(){
        if (switchedOn.get()) {
            pseudoClassStateChanged(SWITCHED_ON, true);
            pseudoClassStateChanged(SWITCHED_OFF, false);
            label.setText("Yes");
        } else {
            pseudoClassStateChanged(SWITCHED_ON, false);
            pseudoClassStateChanged(SWITCHED_OFF, true);
            label.setText("No");
        }
    }

    private void addAction() {

        switchedOn.addListener(e -> setButtonState());
    }

    public boolean isSwitchedOn() {
        return switchedOn.get();
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn.set(switchedOn);
    }
}