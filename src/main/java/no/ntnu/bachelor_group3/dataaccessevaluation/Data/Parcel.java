package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

//TODO: ADD CONNECTION TO OTHER TABLES
@Entity
@Table(name = "parcel")
public class Parcel {

    private static Long counter = 1L;

    @Id
    private Long parcel_id;

    private double weight;

    @OneToOne
    private Checkpoint lastCheckpoint;

    //price is evaluated with weight times a constant
    private int weight_class;



    @JoinColumn(name = "checkpoint_id")
    @OneToMany
    private ArrayList<Checkpoint> checkpoints;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment_placed_in; //which shipment the parcel belongs to


    public Parcel() {
    }

    public Parcel(Shipment shipment, double weight) {
        this.shipment_placed_in = shipment;
        this.parcel_id = counter++;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Shipment getShipment_placed_in() {
        return this.shipment_placed_in;
    }

    public Long getParcel_id() {
        return this.parcel_id;
    }

    //assigns a weight class depending on weight. Used at checkpoints to estimate cost.
    public void setWeight_class() {
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

    //TODO: for each loop to generate cost through each checkpoint

    public double generateCostForEachParcelBasedOnCheckpoints() {
        double cost = 0;
        for (Checkpoint checkpoint : checkpoints) {
            cost += checkpoint.getCost() * this.weight;
        }
        return cost;
    }

    public void setLastCheckpoint(Checkpoint checkpoint) {
        this.lastCheckpoint = checkpoint;
        checkpoints.add(checkpoint);
    }


    public Checkpoint getLastCheckpoint() {
        return lastCheckpoint;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "parcel_id=" + parcel_id +
                ", weight=" + weight +
                ", lastCheckpoint=" + lastCheckpoint +
                "shipment: " + shipment_placed_in +
                '}';
    }
}
