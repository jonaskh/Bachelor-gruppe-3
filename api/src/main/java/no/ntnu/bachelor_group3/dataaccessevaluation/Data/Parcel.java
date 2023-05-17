package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "parcel")
public class Parcel {

    private int weight;

    private static Long counter = 1L;

    @Id
    private Long parcel_id;



    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parcel_id")
    private List<Checkpoint> checkpoints = new ArrayList<>();


    public Parcel() {
        this.parcel_id = counter++;
    }

    public Parcel(Shipment shipment, int weight) {
        this.parcel_id = counter++;
        this.weight = weight;
    }

    public int getWeight() {
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

    public void addCheckpoint(Checkpoint checkpoint) {
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
                ", checkpoints=" + getLastCheckpoint() +
                '}';
    }
}
