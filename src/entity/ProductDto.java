package entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    int id;
    String name;

    @JsonProperty("stockLevel")
    int availability;
    int demand;

    public ProductDto(){}

    public ProductDto(int id, String name, int availability, int demand){
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.demand = demand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }
}