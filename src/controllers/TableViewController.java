package controllers;

import components.view.NonNegativeIntegerEditingCell;
import components.view.SwitchButton;
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

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private FilteredList<Product> filteredProducts = new FilteredList<>(FXCollections.observableList(products));

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
        demandColumn.setOnEditCommit(event -> (event
                        .getTableView()
                        .getItems()
                        .get(event.getTablePosition().getRow())
                ).setDemand(event.getNewValue()));
    }

    private void addButtonsToTable() {

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {

                    private final SwitchButton btn = new SwitchButton();

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
        return products
                .stream()
                .filter(Product::isToStockUp)
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

    public ObservableList<Product> getProducts(){
        return products;
    }

    public void setProducts(List<Product> products){

        this.products.clear();
        this.products.addAll(products);
        this.filteredProducts = new FilteredList<>(FXCollections.observableList(products));
        tableView.setItems(filteredProducts);
    }

}