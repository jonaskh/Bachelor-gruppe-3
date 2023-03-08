package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

//TODO: ADD CONNECTION TO OTHER TABLES
@Entity
@Table(name = "parcel")
public class Parcel {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parcel_id;

    private int weight;

    //price is evaluated with weight times a constant
    private int weight_class;



    @JoinColumn(name = "checkpoint_id")
    @OneToMany
    private ArrayList<Checkpoint> checkpoints;


    //TODO: for each loop to generate cost through each checkpoint



    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment_placed_in; //which shipment the parcel belongs to

    public int getWeight() {
        return weight;
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




}
