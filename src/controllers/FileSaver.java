package controllers;

import entity.ProductModel;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.*;
import java.security.cert.Extension;
import java.util.List;
import java.util.stream.Collectors;

public class FileSaver {

    private FileChooser fileChooser;

    private Stage stage;

    public FileSaver(Stage stage){
        fileChooser = new FileChooser();
        this.stage = stage;
        customizeFileChooser();
    }

    private void customizeFileChooser() {
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("TXT files (*.txt)", "*.txt"),
            new ExtensionFilter("CSV files (*.csv)", "*.csv"),
                new ExtensionFilter("All files (*.*)", "*.*")
        );
    }

    public void saveData(List<ProductModel> data){
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(convertToCSVFormat(data));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertToCSVFormat(List<ProductModel> data){
        return data.stream()
                .map(productModel ->
                        productModel.getId() + "," +
                                productModel.getName() + "," +
                                productModel.getAvailability() + "," +
                                productModel.getDemand())
                .collect(Collectors.joining("\n"));
    }
}
