package components.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.exceptions.APICallException;
import components.view.PopupStage;
import controllers.SignupSceneController;
import entity.ApiError;
import entity.AuthRequest;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SignupTask extends Task<Void> {

    private SignupSceneController signupSceneController;
    private ObjectMapper mapper;
    private AuthRequest authRequest;

    public SignupTask(SignupSceneController signupSceneController) {
        this.signupSceneController = signupSceneController;
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
        signupSceneController.setDisableButton(disable);
    }

    private void clearSignUpTextFields() { signupSceneController.clearTextFields(); }

    public void onSucceeded() {

        setOnSucceeded(event -> new PopupStage("Successfully signed up!"));
        clearSignUpTextFields();
        setDisableButtons(false);
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
            clearSignUpTextFields();
            setDisableButtons(false);
        });
    }

    @Override
    protected Void call() throws IOException, InterruptedException, APICallException, URISyntaxException {

        HttpRequest httpRequest = parseRequest();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (isAuthenticationFailed(response)) {
            onErrorThrow(response);
        }

        return null;
    }

    private HttpRequest parseRequest() throws JsonProcessingException, URISyntaxException {
        return HttpRequestBuilder.buildSignupPOST(authRequest);
    }

    private boolean isAuthenticationFailed(HttpResponse<String> response){
        return response.statusCode() != 201;
    }

    private void onErrorThrow(HttpResponse<String> response) throws JsonProcessingException{
        ApiError error = mapper.readValue(response.body(), ApiError.class);
        throw new APICallException(error.getMessage());
    }
}