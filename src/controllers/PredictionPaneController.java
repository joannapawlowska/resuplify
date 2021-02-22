package controllers;

import components.DataFileWriter;
import components.SwitchButton;
import entity.Product;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import jfxtras.scene.control.LocalDatePicker;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredictionPaneController implements Initializable {

    @FXML
    LocalDatePicker datePicker;

    @FXML
    public TableView<Product> productTable;

    @FXML
    public TableColumn<Product, Integer> idColumn;

    @FXML
    public TableColumn<Product, String> nameColumn;

    @FXML
    public TableColumn<Product, Integer> availabilityColumn;

    @FXML
    public TableColumn<Product, String> demandColumn;

    @FXML
    public TableColumn<Product, Void> buttonColumn;

    @FXML
    public TextField searchBox;

    @FXML
    public Button predictButton, saveToFileButton;

    @FXML Label amountLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customizeDatePicker();
        initializeProductTableColumns();
        addButtonToTable();
        initializeSearchBox();
        initializeAmountLabel();
        productTable.setItems(filteredProducts);
    }

    private void customizeDatePicker(){
        datePicker.setLocale(Locale.ENGLISH);
    }

    private void initializeProductTableColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        demandColumn.setCellValueFactory(new PropertyValueFactory<>("Demand"));
    }

    private void addButtonToTable() {

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<>() {

                    private SwitchButton btn = new SwitchButton();

                    {
                        btn.setOnMouseClicked(mouseEvent -> {
                            btn.setSwitchedOn(!btn.isSwitchedOn());
                            Product data = getTableView().getItems().get(getIndex());
                            data.setToStockUp(btn.isSwitchedOn());
                            amountLabel.setText(String.valueOf(selectProductsToSave().size()));
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Product data = getTableView().getItems().get(getIndex());
                            btn.setSwitchedOn(data.isToStockUp());
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        buttonColumn.setCellFactory(cellFactory);
    }

    private void initializeSearchBox(){
        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldValue, newValue) ->
                filteredProducts.setPredicate(createPredicate(newValue))
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

    private void initializeAmountLabel(){
        filteredProducts.addListener((ListChangeListener.Change<? extends Product> product) ->
            amountLabel.setText(String.valueOf(selectProductsToSave().size()))
        );
    }

    @FXML
    public void handleClearSearchBox(ActionEvent event) {
        searchBox.setText("");
        event.consume();
    }

    @FXML
    public void handleSaveToFileButton(ActionEvent event){
        DataFileWriter dataFileWriter = new DataFileWriter();
        List<Product> productsToSave = selectProductsToSave();
        dataFileWriter.write(productsToSave);
    }

    private List<Product> selectProductsToSave(){
        return filteredProducts
                .stream()
                .filter(product -> product.isToStockUp())
                .collect(Collectors.toList()
                );
    }

    private ObservableList<Product> products = FXCollections.observableArrayList(
            new Product(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4, true),
            new Product(210, "Torba damska shopper granat", 7, 3, true),
            new Product(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new Product(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new Product(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new Product(267, "Torba damska shopper zielen", 100, 200,true),
            new Product(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new Product(21, "Torba duza z haftem 45cm", 8, 1,true),
            new Product(48, "Big shopper bag Johnson", 73, 5,true),
            new Product(211, "Torba damska shopper granat", 7, 20,true),
            new Product(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new Product(210, "Torba damska shopper granat", 7, 3,true),
            new Product(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new Product(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new Product(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new Product(267, "Torba damska shopper zielen", 100, 200,true),
            new Product(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new Product(21, "Torba duza z haftem 45cm", 8, 1,true),
            new Product(48, "Big shopper bag Johnson", 73, 5,true),
            new Product(211, "Torba damska shopper granat", 7, 20,true),
            new Product(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new Product(210, "Torba damska shopper granat", 7, 3,true),
            new Product(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new Product(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new Product(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new Product(267, "Torba damska shopper zielen", 100, 200,true),
            new Product(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new Product(21, "Torba duza z haftem 45cm", 8, 1,true),
            new Product(48, "Big shopper bag Johnson", 73, 5,true),
            new Product(211, "Torba damska shopper granat", 7, 20,true),
            new Product(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new Product(210, "Torba damska shopper granat", 7, 3,true),
            new Product(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new Product(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new Product(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new Product(267, "Torba damska shopper zielen", 100, 200,true),
            new Product(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new Product(21, "Torba duza z haftem 45cm", 8, 1,true),
            new Product(48, "Big shopper bag Johnson", 73, 5,true),
            new Product(211, "Torba damska shopper granat", 7, 20,true),
            new Product(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new Product(210, "Torba damska shopper granat", 7, 3,true),
            new Product(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new Product(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new Product(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new Product(267, "Torba damska shopper zielen", 100, 200,true));

    private FilteredList<Product> filteredProducts = new FilteredList<>(FXCollections.observableList(products));
}