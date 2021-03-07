package controllers;

import components.NonNegativeIntegerEditingCell;
import components.SwitchButton;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;

public class TableViewController {

    @FXML private TableView<Product> tableView;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Integer> availabilityColumn;
    @FXML private TableColumn<Product, Integer> demandColumn;
    @FXML private TableColumn<Product, Void> buttonColumn;

    public void initialize(){
        initializeProductTableColumns();
        makeDemandColumnEditable();
        addButtonsToTable();
        tableView.setItems(filteredProducts);
    }

    private void initializeProductTableColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        demandColumn.setCellValueFactory(new PropertyValueFactory<>("Demand"));
    }

    private void makeDemandColumnEditable() {
        tableView.setEditable(true);
        demandColumn.setEditable(true);
        allowColumnToBeEditedOnlyWithNonNegativeIntegers();
        saveChangesWhenColumnCellEdited();
    }

    private void allowColumnToBeEditedOnlyWithNonNegativeIntegers(){
        demandColumn.setCellFactory(col -> new NonNegativeIntegerEditingCell());
    }

    private void saveChangesWhenColumnCellEdited(){
        demandColumn.setOnEditCommit(event -> (event.getTableView().getItems().get(event.getTablePosition().getRow())).setDemand(event.getNewValue()));
    }

    private void addButtonsToTable() {

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {

                    private SwitchButton btn = new SwitchButton();

                    {
                        btn.setOnMouseClicked(mouseEvent -> {
                            btn.setSwitchedOn(!btn.isSwitchedOn());
                            Product data = getTableView().getItems().get(getIndex());
                            data.setToStockUp(btn.isSwitchedOn());
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
            }
        };

        buttonColumn.setCellFactory(cellFactory);
    }

    public int getAmountOfProductsToBeStockedUp() {
        return selectProductsToBeStockedUp().size();
    }

    public List<Product> selectProductsToBeStockedUp(){
        return filteredProducts
                .stream()
                .filter(product -> product.isToStockUp())
                .collect(Collectors.toList());
    }

    public void checkAllProducts() {
        filteredProducts.forEach(product -> product.setToStockUp(true));
    }

    public void uncheckAllProducts() {
        filteredProducts.forEach(product -> product.setToStockUp(false));
    }

    public void refreshTableView(){
        tableView.refresh();
    }

    public FilteredList<Product> getFilteredProducts(){
        return filteredProducts;
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
