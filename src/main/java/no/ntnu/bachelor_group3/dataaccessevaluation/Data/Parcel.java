package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;


@Entity
@Table(name = "parcel")
public class Parcel {

    private static Long counter = 1L;

    @Id
    private Long parcel_id;

    private double weight;

    private int weightClass;


    @Version
    private Long version = null;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "parcel_id")
    private List<Checkpoint> checkpoints = new ArrayList<>();





    public Parcel() {
        this.parcel_id = counter++;
    }

    public Parcel(Shipment shipment, double weight) {
        this.parcel_id = counter++;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getWeightClass() {
        return weightClass;
    }

    public void setWeightClass(int weightClass) {
        this.weightClass = weightClass;
    }

    public Long getId() {
        return this.parcel_id;
    }

    public Long getVersion() {
        return version;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }




    //assigns a weight class depending on weight. Used at checkpoints to estimate cost.
    public void assignWeightClass() {
        if (weight < 1) {
            weightClass = 1;
        } else if (weight < 5) {
            weightClass = 2;
        } else if (weight < 10) {
            weightClass = 3;
        } else {
            weightClass = 4;
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

    @Transactional
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



