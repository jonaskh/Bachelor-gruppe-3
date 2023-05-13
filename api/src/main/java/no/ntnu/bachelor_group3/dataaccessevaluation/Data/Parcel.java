package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Entity
@Table(name = "parcel")
public class Parcel {

    private static Long counter = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parcel_id;

    private double weight;

    private int weight_class;

    @Version
    private Long parcel_version = null;

    //price is evaluated with weight times a constant
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "checkpoint_id")
    private List<Checkpoint> checkpoints = new CopyOnWriteArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;


    public Parcel() {
        this.parcel_id = counter++;
    }

    public Parcel(Shipment shipment, double weight) {
        this.shipment = shipment;
        this.parcel_id = counter++;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }



    public Long getParcel_id() {
        return this.parcel_id;
    }

    //assigns a weight class depending on weight. Used at checkpoints to estimate cost.


    //TODO: for each loop to generate cost through each checkpoint

    public double generateCostForEachParcelBasedOnCheckpoints() {
        double cost = 0;
        for (Checkpoint checkpoint : checkpoints) {
            cost += checkpoint.getCost() * this.weight;
        }
        return cost;
    }

    public void setLastCheckpoint(Checkpoint checkpoint) {
        checkpoints.remove(checkpoint);
        checkpoints.add(checkpoint);
    }


    public Checkpoint getLastCheckpoint() {
        if (!checkpoints.isEmpty()) {
            return checkpoints.get(checkpoints.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "parcel_id=" + parcel_id +
                ", weight=" + weight +
                ", lastCheckpoint=" + getLastCheckpoint() +
                '}';
    }
}
