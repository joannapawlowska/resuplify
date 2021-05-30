package controllers;

import components.logic.Preference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogOutPaneController {

    @FXML AnchorPane logoutPane;

    @FXML
    public void handleYesBtn() {
        try {
            Preference.clearToken();
            setUpLoginScene();
            closeCurrentStage();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setUpLoginScene() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/LoginScene.fxml"));
        stage.setTitle("Resuplify");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void closeCurrentStage(){
        Stage currentStage = (Stage) logoutPane.getScene().getWindow();
        currentStage.close();
    }
}
