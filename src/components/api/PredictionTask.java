package components.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.exceptions.APICallException;
import controllers.PredictionPaneController;
import entity.ApiError;
import entity.Product;
import entity.ProductDto;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PredictionTask extends Task<List<ProductDto>> {

    private PredictionPaneController predictionPaneController;
    private ObjectMapper mapper;

    public PredictionTask(PredictionPaneController predictionPaneController) {

        this.mapper = new ObjectMapper();
        this.predictionPaneController = predictionPaneController;
    }

    public void update(LocalDate date) {

        setDisablePredictBtn(true);
        onSucceeded();
        onFailed();

        new Thread(this).start();
    }

    private void setDisablePredictBtn(boolean disable) {
        predictionPaneController.setDisablePredictButton(disable);
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

    private void setPredictionPaneView(List<Product> p){
        predictionPaneController.tableViewController.setProducts(p);
        predictionPaneController.handleAmountLabel();
        setDisablePredictBtn(false);
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

            setDisablePredictBtn(false);
        });
    }

    @Override
    protected List<ProductDto> call() throws IOException, InterruptedException, APICallException, URISyntaxException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://48032270-3579.mock.pstmn.io/resupply")).build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() != 200) {
            ApiError error = mapper.readValue(response.body(), ApiError.class);
            throw new APICallException(error.getMessage());
        }

        return mapper.readValue(response.body(), new TypeReference<>(){});
    }

    private URI parseUri(LocalDate date){

        HashMap<String, String> paramValuePairs = new HashMap<>();
        paramValuePairs.put("date", date.toString());

        return HttpRequestBuilder.buildResupplyGET(paramValuePairs).uri();
    }
}