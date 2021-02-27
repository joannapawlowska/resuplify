package components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


public class SwitchButton extends StackPane {

    private final Circle slider = new Circle();
    private final BooleanProperty switchedOn = new SimpleBooleanProperty(true);
    private static final PseudoClass SWITCHED_ON = PseudoClass.getPseudoClass("switched-on");
    private static final PseudoClass SWITCHED_OFF = PseudoClass.getPseudoClass("switched-off");

    public SwitchButton() {
        setSize();
        setStylesheetAndStyleClasses();
        setButtonCurrentStyle();
        addAction();
        getChildren().addAll(slider);
    }

    private void setSize(){
        setMinSize(45, 25);
        setPrefSize(45, 25);
        setMaxSize(45, 25);
        slider.setRadius(9);
    }

    private void setStylesheetAndStyleClasses() {
        getStylesheets().add("css/switchButton.css");
        getStyleClass().add("switch-btn");
        slider.getStyleClass().add("switch-slider");
    }

    private void setButtonCurrentStyle(){
        if (switchedOn.get()) {
            setSwitchedOnStyle();
        } else {
            setSwitchedOffStyle();
        }
    }

    private void setSwitchedOnStyle(){
        pseudoClassStateChanged(SWITCHED_ON, true);
        pseudoClassStateChanged(SWITCHED_OFF, false);
    }

    private void setSwitchedOffStyle(){
        pseudoClassStateChanged(SWITCHED_ON, false);
        pseudoClassStateChanged(SWITCHED_OFF, true);
    }

    private void addAction() {
        switchedOn.addListener(e -> setButtonCurrentStyle());
    }

    public boolean isSwitchedOn() {
        return switchedOn.get();
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn.set(switchedOn);
    }
}