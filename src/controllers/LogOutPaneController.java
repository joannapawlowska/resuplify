package controllers;

import components.logic.Preference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogOutPaneController {

    @FXML
    AnchorPane logoutPane;

    @FXML
    public void handleYesBtn() {
        logOut();
        closeCurrentStage();
    }

    public void logOut() {
        try {
            Preference.clearToken();
            setUpLoginScene();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setUpLoginScene() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/LoginScene.fxml"));
        stage.getIcons().add(new Image("icons/logo.png"));
        stage.setTitle("Resuplify");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void closeCurrentStage() {
        Stage currentStage = (Stage) logoutPane.getScene().getWindow();
        currentStage.close();
    }
}