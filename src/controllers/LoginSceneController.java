package controllers;

import components.api.LoginTask;
import components.logic.Preference;
import components.view.PopupStage;
import entity.AuthRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController {

    @FXML protected AnchorPane scene;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML public Button signupButton;
    @FXML public Button loginButton;

    public void initialize() {
        setMode(Preference.isDarkMode());
        setPromptTexts();
    }


    private void setMode(boolean darkMode){
        if(darkMode)
            setDarkMode();
        else
            setLightMode();
    }

    private void setDarkMode(){
        scene.getStylesheets().remove("css/light-mode.css");
        scene.getStylesheets().add("css/dark-mode.css");
    }

    private void setLightMode(){
        scene.getStylesheets().remove("css/dark-mode.css");
        scene.getStylesheets().add("css/light-mode.css");
    }

    private void setPromptTexts() {
        usernameTextField.setPromptText("username");
        passwordTextField.setPromptText("password");
    }

    public void clearTextFields(){
        usernameTextField.clear();
        passwordTextField.clear();
    }

    @FXML
    private void handleLoginBtn() {

        if(isTextFieldEmpty()){
            popupAlert();

        }else{
            LoginTask loginTask = new LoginTask(this);
            loginTask.authenticate(getAuthRequest());
        }
    }

    public boolean isTextFieldEmpty(){
        return isUsernameTextFieldEmpty() || isPasswordTextFieldEmpty();
    }

    private boolean isUsernameTextFieldEmpty(){
        return usernameTextField.getText() == null || usernameTextField.getText().trim().isEmpty();
    }

    private boolean isPasswordTextFieldEmpty(){
        return passwordTextField.getText() == null || passwordTextField.getText().trim().isEmpty();
    }

    public void popupAlert(){

        String cause;

        if(isUsernameTextFieldEmpty()) cause = "Username";
        else cause = "Password";

        new PopupStage(String.format("%s can not be empty", cause));
    }

    private AuthRequest getAuthRequest(){
        return new AuthRequest(usernameTextField.getText(), passwordTextField.getText());
    }

    public void loadMainScene(){

        try {
            setUpMainScene();
            closeCurrentStage();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setUpMainScene() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainScene.fxml"));
        stage.getIcons().add(new Image("icons/logo.png"));
        stage.setTitle("Resuplify");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    private void closeCurrentStage(){
        Stage currentStage = (Stage) scene.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void handleSignupBtn() {

        try {
            loadSignupScene();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void loadSignupScene() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/SignupScene.fxml"));
        scene.getStylesheets().add("css/signup-scene.css");
        scene.getStylesheets().remove("css/login-scene.css");
        scene.getChildren().setAll(pane);
    }

    public void setDisableButton(boolean disable){
        loginButton.setDisable(disable);
        signupButton.setDisable(disable);
    }
}