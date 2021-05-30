package components.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.exceptions.APICallException;
import components.logic.Preference;
import components.view.PopupStage;
import controllers.LoginSceneController;
import entity.ApiError;
import entity.AuthRequest;
import entity.AuthResponse;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginTask extends Task<AuthResponse> {

    private LoginSceneController loginSceneController;
    private ObjectMapper mapper;

    public LoginTask(LoginSceneController loginSceneController) {
        this.loginSceneController = loginSceneController;
        this.mapper = new ObjectMapper();
    }

    public void authenticate(AuthRequest request) {

        setDisableButtons(true);
        onSucceeded();
        onFailed();

        new Thread(this).start();
    }

    private void setDisableButtons(boolean disable) {
        loginSceneController.setDisableButton(disable);
    }

    public void onSucceeded() {

        setOnSucceeded(event -> {

            AuthResponse authResponse = (AuthResponse) event.getSource().getValue();
            Preference.updateToken(authResponse.getAccessToken());
            loginSceneController.loadMainScene();

        });
    }

    public void onFailed() {

        setOnFailed(event -> {

            Throwable exc = getException();
            String message;

            if (exc instanceof APICallException) {
                message = exc.getMessage();
            } else if (exc instanceof URISyntaxException) {
                message = exc.getMessage();
            } else {
                message = "Unexpected error";
            }

            new PopupStage(message);
            clearTextFields();
            setDisableButtons(false);
        });
    }

    private void clearTextFields() {
        loginSceneController.clearTextFields();
    }

    @Override
    protected AuthResponse call() throws IOException, InterruptedException, APICallException, URISyntaxException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .uri(new URI("https://48032270-3579.mock.pstmn.io/auth/login")).build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {

            ApiError error = mapper.readValue(response.body(), ApiError.class);
            throw new APICallException(error.getMessage());
        }

        return mapper.readValue(response.body(), AuthResponse.class);
    }

    private URI parseUri(AuthRequest authRequest) throws JsonProcessingException {
        return HttpRequestBuilder.buildSignupPOST(authRequest).uri();
    }
}