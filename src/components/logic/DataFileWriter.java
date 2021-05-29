package components.logic;

import entity.Product;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataFileWriter {

    private final FileChooser fileChooser = new FileChooser();
    private File file;
    private List<Product> data;

    public DataFileWriter() {
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("TXT files (*.txt)", "*.txt"),
                new ExtensionFilter("CSV files (*.csv)", "*.csv"),
                new ExtensionFilter("XLS files (*.xls)", "*.xls"),
                new ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx")
        );
    }

    public void write(List<Product> products){
        data = products;
        file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            try {
                writeToSelectedExtension();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToSelectedExtension() throws IOException {
        if(file.getName().endsWith(".txt") || file.getName().endsWith(".csv")){
            writeAsCommaSeparatedValues();
        }
        else if(file.getName().endsWith(".xls")) {
            writeAsExcel(new HSSFWorkbook());
        }
        else if(file.getName().endsWith(".xlsx")){
            writeAsExcel(new XSSFWorkbook());
        }
        else{
            throw new IllegalArgumentException(file.getName() + "has no valid file extension");
        }
    }

    private void writeAsCommaSeparatedValues() throws IOException{
        FileWriter out = new FileWriter(file);
        out.write(convertDataToString());
        out.close();
    }

    private String convertDataToString(){
        return data.stream()
                .map(product ->
                        product.getId() + "," +
                        product.getName() + "," +
                        product.getAvailability() + "," +
                        product.getDemand())
                .collect(Collectors.joining("\n"));
    }


    private void writeAsExcel(Workbook workbook) throws IOException{
        Sheet sheet = workbook.createSheet("Products to stock up");
        fillWorkbookSheet(sheet);
        adjustSheetSizeToContent(sheet);
        writeWorkbookToFile(workbook);
    }

    private void fillWorkbookSheet(Sheet sheet){
        Row row = sheet.createRow(0);
        fillHeaderRow(row);
        int rowCount = 1;

        for(Product product : data){
            row = sheet.createRow(rowCount++);
            fillDataRow(product, row);
        }
    }

    private void fillHeaderRow(Row row){
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Availability");
        row.createCell(3).setCellValue("Demand");
    }

    private void fillDataRow(Product product, Row row){
        row.createCell(0).setCellValue(product.getId());
        row.createCell(1).setCellValue(product.getName());
        row.createCell(2).setCellValue(product.getAvailability());
        row.createCell(3).setCellValue(product.getDemand());
    }

    private void adjustSheetSizeToContent(Sheet sheet){
        for(int i = 0; i < 3; i ++)
            sheet.autoSizeColumn(i);
    }

    private void writeWorkbookToFile(Workbook workbook) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
    }
}