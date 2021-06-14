package controllers;

import components.api.SignupTask;
import components.logic.Preference;
import components.view.PopupStage;
import entity.AuthRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignupSceneController {

    @FXML protected AnchorPane scene;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField shopUrlTextField;
    @FXML public Label loginLabel;
    @FXML public Button signupButton;

    public void initialize() {
        setMode(Preference.isDarkMode());
        setPromptTexts();
        handleLoginLabel();
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
        shopUrlTextField.setPromptText("shop url");
    }

    private void handleLoginLabel() {
        loginLabel.setOnMouseClicked(mouseEvent -> loadLoginScene());
    }

    public void loadLoginScene(){

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/LoginScene.fxml"));
            scene.getStylesheets().add("css/login-scene.css");
            scene.getStylesheets().remove("css/signup-scene.css");
            scene.getChildren().setAll(pane);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void clearTextFields(){
        usernameTextField.clear();
        passwordTextField.clear();
        shopUrlTextField.clear();
    }

    @FXML
    private void handleSignupBtn() {
        if(isTextFieldEmpty()){
            popupAlert();
        }
        else{
            SignupTask signupTask = new SignupTask(this);
            signupTask.authenticate(getAuthRequest());
        }
    }

    private boolean isTextFieldEmpty(){
        return isUsernameTextFieldEmpty() || isPasswordTextFieldEmpty() || isShopUrlTextFieldEmpty();
    }

    private boolean isUsernameTextFieldEmpty(){
        return usernameTextField.getText() == null || usernameTextField.getText().trim().isEmpty();
    }

    private boolean isPasswordTextFieldEmpty(){
        return passwordTextField.getText() == null || passwordTextField.getText().trim().isEmpty();
    }

    private boolean isShopUrlTextFieldEmpty(){
        return shopUrlTextField.getText() == null || shopUrlTextField.getText().trim().isEmpty();
    }

    private AuthRequest getAuthRequest(){
        return new AuthRequest(usernameTextField.getText(), passwordTextField.getText(), shopUrlTextField.getText());
    }

    public void popupAlert(){

        String cause;

        if(isUsernameTextFieldEmpty()) cause = "Username";
        else if(isPasswordTextFieldEmpty()) cause = "Password";
        else cause = "Shop url";

        new PopupStage(String.format("%s can not be empty", cause));
    }

    public void setDisableButton(boolean disable){
        signupButton.setDisable(disable);
    }
}