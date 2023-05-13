package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import java.util.ArrayList;
import java.util.List;

public class Parcel {
    private Long id;
    private Shipment shipment;
    private double weight;
    private List<Checkpoint> checkpoints;

    public int getWeight_class() {
        return weight_class;
    }

    public void setWeight_class(int weight_class) {
        this.weight_class = weight_class;
    }

    private int weight_class;

    // Constructor
    public Parcel(Long id, double weight) {
        this.id = id;
        this.weight = weight;
        this.checkpoints = new ArrayList<>();

        if (weight <= 5) {
            weight_class = 1;

        }
        else if(weight <= 20) {
            weight_class = 2;
        }
        else if(weight <= 50) {
            weight_class = 3;
        }
        else {
            weight_class = 4;
        }
    }

    // Getters and setters for each field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    // Instance method to save this parcel to the database


    // Instance method to delete this parcel from the database


}

