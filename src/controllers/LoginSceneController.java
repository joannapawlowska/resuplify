package controllers;

import components.api.LoginTask;
import components.view.PopupStage;
import entity.AuthRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController extends SceneController{

    @FXML public Button signupButton;

    public void initialize() {
        super.initialize();
    }

    @Override
    protected void setPromptTexts() {
        usernameTextField.setPromptText("username");
        passwordTextField.setPromptText("password");
    }

    @Override
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

    @Override
    public void popupAlert(){

        String cause;

        if(isUsernameTextFieldEmpty()) cause = "Username";
        else cause = "Password";

        new PopupStage(String.format("%s can not be empty", cause));
    }

    @Override
    public boolean isTextFieldEmpty(){
        return isUsernameTextFieldEmpty() || isPasswordTextFieldEmpty();
    }

    @Override
    protected boolean isUsernameTextFieldEmpty(){
        return usernameTextField.getText() == null || usernameTextField.getText().trim().isEmpty();
    }

    @Override
    protected boolean isPasswordTextFieldEmpty(){
        return passwordTextField.getText() == null || passwordTextField.getText().trim().isEmpty();
    }

    @Override
    protected AuthRequest getAuthRequest(){
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
        scene.getChildren().setAll(pane);
    }

    @Override
    public void setDisableButton(boolean disable){
        super.setDisableButton(disable);
        signupButton.setDisable(disable);
    }
}