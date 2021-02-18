package entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductModel {

    SimpleIntegerProperty id;
    SimpleStringProperty name;
    SimpleIntegerProperty availability;
    SimpleIntegerProperty demand;
    SimpleBooleanProperty toStockUp;

    public ProductModel(int id, String name, int availability, int demand, boolean toStockUp){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.availability = new SimpleIntegerProperty(availability);
        this.demand = new SimpleIntegerProperty(demand);
        this.toStockUp = new SimpleBooleanProperty(toStockUp);
    }

    public int getId () {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public int getAvailability() {
        return availability.get();
    }

    public int getDemand() {
        return demand.get();
    }

    public boolean isToStockUp() {
        return toStockUp.get();
    }

    public void setToStockUp(boolean toStockUp) {
        this.toStockUp.set(toStockUp);
    }
}