package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class ProductModel {

    SimpleIntegerProperty id;
    SimpleStringProperty name;
    SimpleIntegerProperty amount;
    SimpleStringProperty lowStockDate;
    SimpleStringProperty outOfStockDate;

    public ProductModel(int id, String name, int amount, LocalDate lowStockDate, LocalDate outOfStockDate){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.lowStockDate = new SimpleStringProperty(lowStockDate.toString());
        this.outOfStockDate = new SimpleStringProperty(outOfStockDate.toString());
    }

    public int getId () {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
    public int getAmount () {
        return amount.get();
    }

    public String getLowStockDate() {
        return lowStockDate.get();
    }

    public String getOutOfStockDate() {
        return outOfStockDate.get();
    }
}