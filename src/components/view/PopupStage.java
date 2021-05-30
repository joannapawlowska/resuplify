package components.view;

import components.logic.Preference;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupStage extends Stage{

    private double xOffset = 0;
    private double yOffset = 0;
    private Label label;
    private VBox layout;
    private Scene scene;
    private Button button;

    public PopupStage(String message){
        initialize(message);
        setSize();
        setStylesheetAndStyleClasses();
        setStageBorderless();
        setUpStage();
    }


    private void initialize(String message){
        label = new Label(message);

        button = new Button("Close");
        button.setOnAction(e -> close());

        layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 270, 150);
        scene.setFill(Color.TRANSPARENT);
    }

    private void setSize(){
        button.setMinSize(85, 30);
        button.setPrefSize(85, 30);
        button.setMaxSize(85, 30);

        layout.setMinSize(270, 150);
        layout.setPrefSize(270, 150);
        layout.setMaxSize(270, 150);
    }

    private void setStylesheetAndStyleClasses() {

        if(Preference.isDarkMode())
            layout.getStylesheets().add("css/dark-mode.css");
        else
            layout.getStylesheets().add("css/light-mode.css");

        layout.getStylesheets().add("css/popup-stage.css");
        layout.getStyleClass().add("vbox");
        button.getStyleClass().add("close-btn");
        label.getStyleClass().add("label");
    }

    private void setStageBorderless(){

        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        layout.setOnMouseDragged(event -> {
            setX(event.getScreenX() - xOffset);
            setY(event.getScreenY() - yOffset);
        });
    }

    private void setUpStage(){
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        setScene(scene);
        showAndWait();
    }
}
