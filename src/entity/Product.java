package entity;


import java.time.LocalDate;

public class Product {

    int id;
    String name;
    int amount;
    LocalDate lowStockDate;
    LocalDate outOfStockDate;

    public Product(int id, String name, int amount, LocalDate lowStockDate, LocalDate outOfStockData) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.lowStockDate = lowStockDate;
        this.outOfStockDate = outOfStockData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLowStockDate() {
        return lowStockDate;
    }

    public void setLowStockDate(LocalDate lowStockDate) {
        this.lowStockDate = lowStockDate;
    }

    public LocalDate getOutOfStockDate() {
        return outOfStockDate;
    }

    public void setOutOfStockDate(LocalDate outOfStockDate) {
        this.outOfStockDate = outOfStockDate;
    }
}
