package components.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.exceptions.APICallException;
import components.view.PopupStage;
import controllers.LogOutPaneController;
import controllers.PredictionPaneController;
import dto.ApiError;
import dto.Product;
import dto.ProductDto;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PredictionTask extends Task<List<ProductDto>> {

    private PredictionPaneController predictionPaneController;
    private LogOutPaneController logOutPaneController;
    private ObjectMapper mapper;
    private LocalDate date;

    public PredictionTask(PredictionPaneController predictionPaneController, LogOutPaneController logOutPaneController) {
        this.mapper = new ObjectMapper();
        this.predictionPaneController = predictionPaneController;
        this.logOutPaneController = logOutPaneController;
    }

    public void update(LocalDate date) {

        this.date = date;
        setDisableButtons(true);
        onSucceeded();
        onFailed();

        new Thread(this).start();
    }

    private void setDisableButtons(boolean disable) {
        predictionPaneController.setDisablePredictButton(disable);
        predictionPaneController.setDisableSaveToFileButton(disable);
    }

    public void onSucceeded() {

        setOnSucceeded(event -> {

            List<ProductDto> productsResult = (List<ProductDto>) event.getSource().getValue();

            List<Product> products = productsResult.stream()
                    .map(p -> new Product(p.getId(), p.getName(), p.getAvailability(), p.getDemand(), true))
                    .collect(Collectors.toList());

            setPredictionPaneView(products);
        });
    }

    private void setPredictionPaneView(List<Product> p) {
        predictionPaneController.tableViewController.setProducts(p);
        predictionPaneController.handleAmountLabel();
        setDisableButtons(false);
    }

    public void onFailed() {

        setOnFailed(event -> {

            Throwable exc = getException();
            String message;

            if (exc instanceof APICallException) {

                if (isUnauthorized(exc)) {
                    logOutPaneController.logOut();
                    predictionPaneController.closeCurrentStage();
                    message = "Token expired, please log in again";
                } else {
                    message = exc.getMessage();
                }
            } else {
                message = "Unexpected error, please try again";
            }

            new PopupStage(message);
            setDisableButtons(false);
        });
    }

    @Override
    protected List<ProductDto> call() throws IOException, InterruptedException, APICallException, URISyntaxException {

        HttpRequest httpRequest = parseRequest();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (isAuthenticationFailed(response)) {
            onErrorThrow(response);
        }

        return deserializeProducts(response.body());
    }

    private boolean isAuthenticationFailed(HttpResponse<String> response) {
        return response.statusCode() != 200;
    }

    private void onErrorThrow(HttpResponse<String> response) throws JsonProcessingException {
        ApiError error = mapper.readValue(response.body(), ApiError.class);
        throw new APICallException(error.getMessage(), response.statusCode());
    }

    private HttpRequest parseRequest() throws URISyntaxException {
        return HttpRequestBuilder.buildResupplyGET(date);
    }

    private List<ProductDto> deserializeProducts(String responseBody) throws JsonProcessingException {
        return mapper.readValue(responseBody, new TypeReference<>() {
        });
    }

    private boolean isUnauthorized(Throwable exc) {
        return ((APICallException) exc).getStatusCode() == 401;
    }
}