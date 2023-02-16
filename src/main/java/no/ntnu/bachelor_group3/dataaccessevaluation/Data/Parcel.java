package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

//TODO: ADD CONNECTION TO OTHER TABLES
@Entity
@Table(name = "parcel")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parcel_id;

    private int weight;

    //price is evaluated with weight times a constant
    private int weight_class;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Shipment order_placed_in; //which order the parcel belongs to

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
