package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "parcel")
public class Parcel {

    private static Long counter = 1L;

    @Id
    private Long parcel_id;

    private double weight;

    private int weight_class;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    //price is evaluated with weight times a constant
    @ManyToMany(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "cp_parcels",
            joinColumns = { @JoinColumn(name = "parcel_id") },
            inverseJoinColumns = { @JoinColumn(name = "checkpoint_id") })
    private List<Checkpoint> checkpoints = new ArrayList<>();


//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "shipment_id")
//    private Shipment shipment;


    public Parcel() {
        this.parcel_id = counter++;
    }

    public Parcel(Shipment shipment, double weight) {
//        this.shipment = shipment;
        this.parcel_id = counter++;
        this.weight = weight;
        setWeightClass();
    }

    public double getWeight() {
        return weight;
    }



    public Long getParcel_id() {
        return this.parcel_id;
    }

    //assigns a weight class depending on weight. Used at checkpoints to estimate cost.

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }


    //TODO: for each loop to generate cost through each checkpoint

    public double generateCostForEachParcelBasedOnCheckpoints() {
        double cost = 0;
        for (Checkpoint checkpoint : checkpoints) {
            cost += checkpoint.getCost() * this.weight;
        }
        return cost;
    }

    //TODO: check performance
    public void setWeightClass() {
        if (this.weight < 1) {
            this.weight_class = 1;
        }
        if (1 < this.weight && this.weight < 5) {
            this.weight_class = 2;
        }
        if (5 < this.weight && this.weight < 10) {
            this.weight_class = 3;
        }
        if (10 < this.weight && this.weight < 20) {
            this.weight_class = 4;
        }
        if (this.weight > 20) {
            this.weight_class = 5;
        }
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
    }


    public Checkpoint getLastCheckpoint() {
        if (!checkpoints.isEmpty()) {
            return checkpoints.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "parcel_id=" + parcel_id +
                ", weight=" + weight +
                ", checkpoints=" + getLastCheckpoint() +
                '}';
    }
}
