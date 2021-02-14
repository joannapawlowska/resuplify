package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class ProductModel {

    SimpleIntegerProperty id;
    SimpleStringProperty name;
    SimpleIntegerProperty availability;
    SimpleIntegerProperty demand;

    public ProductModel(int id, String name, int availability, int demand){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.availability = new SimpleIntegerProperty(availability);
        this.demand = new SimpleIntegerProperty(demand);
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
}