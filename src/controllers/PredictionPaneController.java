package controllers;

import components.DataFileWriter;
import components.NonNegativeIntegerTextField;
import components.Preference;
import entity.Product;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import jfxtras.scene.control.LocalDatePicker;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class PredictionPaneController {

    @FXML private LocalDatePicker datePicker;
    @FXML private TextField searchBox;
    @FXML private Label amountLabel;
    @FXML private HBox deliveryBox;
    @FXML public ProductsTableController productsTableController;

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
                productsTableController.getFilteredProductsTable().setPredicate(createPredicate(newValue))
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

    private void handleAmountLabel(){
        productsTableController.getFilteredProductsTable().addListener((ListChangeListener.Change<? extends Product> product) -> updateAmountLabel());
    }

    private void updateAmountLabel(){
        amountLabel.setText(String.valueOf(productsTableController.selectProductsToBeStockedUp().size()));
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
    public void handleSaveToFileButton(ActionEvent event){
        DataFileWriter dataFileWriter = new DataFileWriter();
        List<Product> productsToSave = productsTableController.selectProductsToBeStockedUp();
        dataFileWriter.write(productsToSave);
    }

    @FXML
    public void handleCheckAllButton(ActionEvent event){
        productsTableController.checkAllProducts();
        productsTableController.refreshTableView();
        updateAmountLabel();
    }

    @FXML
    public void handleUncheckAllButton(ActionEvent event){
        productsTableController.uncheckAllProducts();
        productsTableController.refreshTableView();
        updateAmountLabel();
    }
}