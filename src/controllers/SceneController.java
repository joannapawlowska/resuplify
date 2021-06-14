//package controllers;
//
//import components.logic.Preference;
//import entity.AuthRequest;
//import javafx.fxml.FXML;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//
//public abstract class SceneController {
//
//    @FXML protected AnchorPane scene;
//    @FXML protected TextField usernameTextField;
//    @FXML protected PasswordField passwordTextField;
//
//    public void initialize() {
//        setPromptTexts();
//        setMode(Preference.isDarkMode());
//    }
//
//    protected abstract void setPromptTexts();
//
//    public abstract void  clearTextFields();
//
//    protected abstract boolean isTextFieldEmpty();
//
//    protected abstract boolean isUsernameTextFieldEmpty();
//
//    protected abstract boolean isPasswordTextFieldEmpty();
//
//    protected abstract AuthRequest getAuthRequest();
//
//    public abstract void popupAlert();
//
//    public abstract void setDisableButton(boolean disable);
//
//}
