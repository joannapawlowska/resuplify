package components.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.exceptions.APICallException;
import components.logic.Preference;
import components.view.PopupStage;
import controllers.LoginSceneController;
import dto.ApiError;
import dto.AuthRequest;
import dto.AuthResponse;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginTask extends Task<AuthResponse> {

    private LoginSceneController loginSceneController;
    private ObjectMapper mapper;
    private AuthRequest authRequest;

    public LoginTask(LoginSceneController loginSceneController) {
        this.loginSceneController = loginSceneController;
        this.mapper = new ObjectMapper();
    }

    public void authenticate(AuthRequest request) {

        this.authRequest = request;
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

        HttpRequest httpRequest = parseRequest();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (isAuthenticationFailed(response)) {
            onErrorThrow(response);
        }

        return deserializeAuthResponse(response.body());
    }

    private HttpRequest parseRequest() throws JsonProcessingException, URISyntaxException {
        return HttpRequestBuilder.buildLoginPOST(authRequest);
    }

    private boolean isAuthenticationFailed(HttpResponse<String> response) {
        return response.statusCode() != 200;
    }

    private void onErrorThrow(HttpResponse<String> response) throws JsonProcessingException {
        ApiError error = mapper.readValue(response.body(), ApiError.class);
        throw new APICallException(error.getMessage());
    }

    private AuthResponse deserializeAuthResponse(String responseBody) throws JsonProcessingException {
        return mapper.readValue(responseBody, AuthResponse.class);
    }
}