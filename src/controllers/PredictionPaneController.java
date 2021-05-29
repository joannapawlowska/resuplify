package controllers;

import components.api.PredictionTask;
import components.logic.DataFileWriter;
import components.logic.Preference;
import components.view.NonNegativeIntegerTextField;
import entity.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import jfxtras.scene.control.LocalDatePicker;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PredictionPaneController {

    @FXML private LocalDatePicker datePicker;
    @FXML private TextField searchBox;
    @FXML private Label amountLabel;
    @FXML private HBox deliveryBox;
    @FXML private Button predictButton;
    @FXML public TableViewController tableViewController;

    private NonNegativeIntegerTextField deliveryTextField;

    public void initialize() {
        addDeliveryTextFieldToPane();
        setEnglishLanguageInDatePicker();
        handleSearchBox();
        handleAmountLabel();
        updateAmountLabel();
        updateDeliveryTextField();
    }

    private void addDeliveryTextFieldToPane() {
        deliveryTextField = new NonNegativeIntegerTextField();
        deliveryTextField.setPrefSize(27, 20);
        deliveryTextField.getStyleClass().add("text-field-delivery");
        deliveryBox.getChildren().add(1, deliveryTextField);
    }

    private void setEnglishLanguageInDatePicker(){
        datePicker.setLocale(Locale.ENGLISH);
    }

    private void handleSearchBox(){
        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldValue, newValue) ->
                tableViewController.getFilteredProducts().setPredicate(createPredicate(newValue))
        );
    }

    private Predicate<Product> createPredicate(String searchText){
        return product -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsProduct(searchText, product);
        };
    }

    private boolean searchFindsProduct(String searchText, Product product) {
        return (Integer.valueOf(product.getId()).toString().equals(searchText)) ||
                (product.getName().toLowerCase().contains(searchText.toLowerCase())) ||
                (Integer.valueOf(product.getAvailability()).toString().equals(searchText)) ||
                (Integer.valueOf(product.getDemand()).toString().equals(searchText));
    }

    public void handleAmountLabel(){
        updateWhenProductTableViewWasModified();
        updateWhenProductsToStockUpPropertyWasModified();
    }

    private void updateWhenProductTableViewWasModified(){
        tableViewController.getProducts()
                .addListener((ListChangeListener.Change<? extends Product> product) -> updateAmountLabel());
    }

    private void updateWhenProductsToStockUpPropertyWasModified(){
        tableViewController.getProducts().forEach(new Consumer<Product>() {
            @Override
            public void accept(Product product) {
                product.getToStockUpBooleanProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        updateAmountLabel();
                    }
                });
            }
        });
    }

    public void updateAmountLabel(){
        amountLabel.setText(String.valueOf(tableViewController.getAmountOfProductsToBeStockedUp()));
    }

    public void updateDeliveryTextField() {
        deliveryTextField.setText(String.valueOf(Preference.getDeliveryDuration()));
    }

    @FXML
    public void handleClearSearchBox(ActionEvent event) {
        searchBox.setText("");
        event.consume();
    }

    @FXML
    public void handleSaveToFileButton(){
        DataFileWriter dataFileWriter = new DataFileWriter();
        List<Product> productsToSave = tableViewController.selectProductsToBeStockedUp();
        dataFileWriter.write(productsToSave);
    }

    @FXML
    public void handleCheckAllButton(){
        tableViewController.checkAllProducts();
        tableViewController.refreshTableView();
    }

    @FXML
    public void handleUncheckAllButton(){
        tableViewController.uncheckAllProducts();
        tableViewController.refreshTableView();
    }

    @FXML
    public void handlePredictButton(){
        LocalDate nextDeliveryDate = datePicker.getLocalDate().plusDays(Long.parseLong(deliveryTextField.getText()));
        PredictionTask predictionTask = new PredictionTask(this);
        predictionTask.update(nextDeliveryDate);
    }

    public void setDisablePredictButton(boolean disable){
        predictButton.setDisable(disable);
    }
}