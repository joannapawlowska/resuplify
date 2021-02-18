package controllers;

import entity.ProductModel;
import javafx.collections.FXCollections;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PredictionPaneController implements Initializable {

    @FXML
    LocalDatePicker datePicker;

    @FXML
    public TableView<ProductModel> productTable;

    @FXML
    public TableColumn<ProductModel, Integer> idColumn;

    @FXML
    public TableColumn<ProductModel, String> nameColumn;

    @FXML
    public TableColumn<ProductModel, Integer> availabilityColumn;

    @FXML
    public TableColumn<ProductModel, String> demandColumn;

    @FXML
    public TableColumn<ProductModel, Void> buttonColumn;

    @FXML
    public TextField searchBox;

    @FXML
    public Button predictButton, saveToFileButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customizeDatePicker();
        initializeProductTableColumns();
        addButtonToTable();
        initializeSearchBox();

        productTable.setItems(filteredData);
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

        Callback<TableColumn<ProductModel, Void>, TableCell<ProductModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProductModel, Void> call(final TableColumn<ProductModel, Void> param) {
                final TableCell<ProductModel, Void> cell = new TableCell<>() {

                    private SwitchButton btn = new SwitchButton();

                    {
                        btn.setOnMouseClicked(mouseEvent -> {
                            btn.setSwitchedOn(!btn.isSwitchedOn());
                            ProductModel data = getTableView().getItems().get(getIndex());
                            data.setToStockUp(btn.isSwitchedOn());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ProductModel data = getTableView().getItems().get(getIndex());
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
                filteredData.setPredicate(createPredicate(newValue))
        );
    }

    private Predicate<ProductModel> createPredicate(String searchText){
        return productModel -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsProduct(searchText, productModel);
        };
    }

    private boolean searchFindsProduct(String searchText, ProductModel product) {
        return (Integer.valueOf(product.getId()).toString().equals(searchText)) ||
                (product.getName().toLowerCase().contains(searchText.toLowerCase())) ||
                (Integer.valueOf(product.getAvailability()).toString().equals(searchText)) ||
                (Integer.valueOf(product.getDemand()).toString().equals(searchText));
    }

    public void handleClearSearchBox(ActionEvent event) {
        searchBox.setText("");
        event.consume();
    }

    private ObservableList<ProductModel> productModels = FXCollections.observableArrayList(
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4, true),
            new ProductModel(210, "Torba damska shopper granat", 7, 3, true),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new ProductModel(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new ProductModel(267, "Torba damska shopper zielen", 100, 200,true),
            new ProductModel(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new ProductModel(21, "Torba duza z haftem 45cm", 8, 1,true),
            new ProductModel(48, "Big shopper bag Johnson", 73, 5,true),
            new ProductModel(211, "Torba damska shopper granat", 7, 20,true),
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new ProductModel(210, "Torba damska shopper granat", 7, 3,true),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new ProductModel(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new ProductModel(267, "Torba damska shopper zielen", 100, 200,true),
            new ProductModel(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new ProductModel(21, "Torba duza z haftem 45cm", 8, 1,true),
            new ProductModel(48, "Big shopper bag Johnson", 73, 5,true),
            new ProductModel(211, "Torba damska shopper granat", 7, 20,true),
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new ProductModel(210, "Torba damska shopper granat", 7, 3,true),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new ProductModel(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new ProductModel(267, "Torba damska shopper zielen", 100, 200,true),
            new ProductModel(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new ProductModel(21, "Torba duza z haftem 45cm", 8, 1,true),
            new ProductModel(48, "Big shopper bag Johnson", 73, 5,true),
            new ProductModel(211, "Torba damska shopper granat", 7, 20,true),
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new ProductModel(210, "Torba damska shopper granat", 7, 3,true),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new ProductModel(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new ProductModel(267, "Torba damska shopper zielen", 100, 200,true),
            new ProductModel(97, "Good Me torebka 2w3 czarna Tom&Tom", 75, 2,true),
            new ProductModel(21, "Torba duza z haftem 45cm", 8, 1,true),
            new ProductModel(48, "Big shopper bag Johnson", 73, 5,true),
            new ProductModel(211, "Torba damska shopper granat", 7, 20,true),
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 4,true),
            new ProductModel(210, "Torba damska shopper granat", 7, 3,true),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, 1,true),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, 2,true),
            new ProductModel(41, "Hash hash bag bag bag Tom&Eva", 7, 20,true),
            new ProductModel(267, "Torba damska shopper zielen", 100, 200,true));

    private FilteredList<ProductModel> filteredData = new FilteredList<>(FXCollections.observableList(productModels));
}