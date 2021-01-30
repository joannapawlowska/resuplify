package controllers;

import entity.ProductModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public TableView<ProductModel> tableView;

    @FXML
    public TableColumn<ProductModel, Integer> id;

    @FXML
    public TableColumn<ProductModel, String> name;

    @FXML
    public TableColumn<ProductModel, Integer> amount;

    @FXML
    public TableColumn<ProductModel, String> lowStockDate;

    @FXML
    public TableColumn<ProductModel, String> outOfStockDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        lowStockDate.setCellValueFactory(new PropertyValueFactory<>("LowStockDate"));
        outOfStockDate.setCellValueFactory(new PropertyValueFactory<>("OutOfStockDate"));

        tableView.setItems(productModels);
    }

    private ObservableList<ProductModel> productModels = FXCollections.observableArrayList(
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(210, "Torba damska shopper granat", 7, LocalDate.of(2020, 4, 11), LocalDate.of(2020, 4, 30)),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 2, 13)),
            new ProductModel(41, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(267, "Torba damska shopper granat", 7, LocalDate.of(2020, 4, 11), LocalDate.of(2020, 4, 30)),
            new ProductModel(97, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(21, "Torba plażowa flamingi beżowa", 8, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 2, 13)),
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(210, "Torba damska shopper granat", 7, LocalDate.of(2020, 4, 11), LocalDate.of(2020, 4, 30)),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 2, 13)),
            new ProductModel(41, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(267, "Torba damska shopper granat", 7, LocalDate.of(2020, 4, 11), LocalDate.of(2020, 4, 30)),
            new ProductModel(97, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(21, "Torba plażowa flamingi beżowa", 8, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 2, 13)),
            new ProductModel(47, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(210, "Torba damska shopper granat", 7, LocalDate.of(2020, 4, 11), LocalDate.of(2020, 4, 30)),
            new ProductModel(32, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(162, "Torba plażowa flamingi beżowa", 8, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 2, 13)),
            new ProductModel(41, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(267, "Torba damska shopper granat", 7, LocalDate.of(2020, 4, 11), LocalDate.of(2020, 4, 30)),
            new ProductModel(97, "Helwa Me torebka portfel 2w1 czarna Tom&Eva", 7, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 3, 15)),
            new ProductModel(21, "Torba plażowa flamingi beżowa", 8, LocalDate.of(2020, 2, 13), LocalDate.of(2020, 2, 13))
    );

}
