package controllers;

import components.api.SignupTask;
import components.view.PopupStage;
import entity.AuthRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignupSceneController extends SceneController {

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField shopUrlTextField;
    @FXML public Label loginLabel;

    public void initialize() {
        super.initialize();
        handleLoginLabel();
    }

    private void handleLoginLabel() {
        loginLabel.setOnMouseClicked(mouseEvent -> loadLoginScene());
    }

    protected void setPromptTexts() {
        usernameTextField.setPromptText("username");
        passwordTextField.setPromptText("password");
        shopUrlTextField.setPromptText("shop url");
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

    protected boolean isTextFieldEmpty(){
        return isUsernameTextFieldEmpty() || isPasswordTextFieldEmpty() || isShopUrlTextFieldEmpty();
    }

    protected boolean isUsernameTextFieldEmpty(){
        return usernameTextField.getText() == null || usernameTextField.getText().trim().isEmpty();
    }

    protected boolean isPasswordTextFieldEmpty(){
        return passwordTextField.getText() == null || passwordTextField.getText().trim().isEmpty();
    }

    private boolean isShopUrlTextFieldEmpty(){
        return shopUrlTextField.getText() == null || shopUrlTextField.getText().trim().isEmpty();
    }

    protected AuthRequest getAuthRequest(){
        return new AuthRequest(usernameTextField.getText(), passwordTextField.getText(), shopUrlTextField.getText());
    }

    public void popupAlert(){

        String cause;

        if(isUsernameTextFieldEmpty()) cause = "Username";
        else if(isPasswordTextFieldEmpty()) cause = "Password";
        else cause = "Shop url";

        new PopupStage(String.format("%s can not be empty", cause));
    }

    public void loadLoginScene(){

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/LoginScene.fxml"));
            scene.getChildren().setAll(pane);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}