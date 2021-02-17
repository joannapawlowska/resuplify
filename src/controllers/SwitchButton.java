package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class SwitchButton extends HBox {

    private boolean switchedOn = true;

    private final Label onLabel = new Label();

    private final Label offLabel = new Label();

    public SwitchButton(){
        init();
        addEventHandler();
    }

    private void init(){
        setStyle();
        setLabelsStyle();
        getChildren().addAll(onLabel, offLabel);
    }

    private void setStyle(){
        getStylesheets().add("css/switchButton.css");
        getStyleClass().add("switch-btn");
        setMinSize(70, 30);
        setMaxSize(70, 30);
        setPrefSize(70, 30);
        setAlignment(Pos.CENTER);
    }

    private void setLabelsStyle(){
        onLabel.setText("Yes");
        offLabel.setText("No");

        onLabel.setMinSize(35, 30);
        offLabel.setMinSize(35, 30);

        onLabel.setPrefSize( 35, 30);
        offLabel.setPrefSize(35, 30);

        onLabel.getStyleClass().add("on-label-selected");
        offLabel.getStyleClass().add("off-label");
    }




    private void addEventHandler() {
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(switchedOn){
                    onLabel.getStyleClass().add("on-label");
                    offLabel.getStyleClass().add("off-label-selected");
                    switchedOn = false;
                }
                else {
                    onLabel.getStyleClass().add("on-label-selected");
                    offLabel.getStyleClass().add("off-label");
                    switchedOn = true;
                }
            }
        };

        setOnMouseClicked(click);
    }

    public boolean isSwitchedOn() {
        return switchedOn;
    }
}