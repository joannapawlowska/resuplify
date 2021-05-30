package controllers;

import components.logic.Preference;
import entity.AuthRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public abstract class SceneController {

    @FXML protected AnchorPane scene;
    @FXML protected TextField usernameTextField;
    @FXML protected PasswordField passwordTextField;
    @FXML protected Button button;

    public void initialize() {
        setPromptTexts();
        setMode(Preference.isDarkMode());
    }

    protected abstract void setPromptTexts();

    public abstract void  clearTextFields();

    protected abstract boolean isTextFieldEmpty();

    protected abstract boolean isUsernameTextFieldEmpty();

    protected abstract boolean isPasswordTextFieldEmpty();

    protected abstract AuthRequest getAuthRequest();

    public abstract void popupAlert();

    protected void setMode(boolean darkMode){
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

    public void setDisableButton(boolean disable){
        button.setDisable(disable);
    }
}
