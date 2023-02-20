package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Locale;


@Entity
public class Shipment {

    //static variable that is incremented each time a new shipment is created to provide a unique id.
    private static int counter = 1;

    private static Faker faker = new Faker(new Locale("nb-NO"));

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    // foreign key to sending customer id so can link to zip code for receiving terminal
    private int sender_id;


    @JoinColumn(name = "customer_id")
    private int customer_id;

    private String receiving_address;

    private String receiving_zip;

    private String receiver_name;

    private boolean delivered;

    @JoinColumn(name = "customer_id")
    private int payer_id;

    @JoinColumn(name = "checkpoint_id")
    @OneToOne
    private Checkpoint current_checkpoint; //current checkpoint so customer can see where order is

    @JoinColumn(name = "checkpoint_id")
    @OneToMany
    private ArrayList<Checkpoint> total_checkpoints; //total checkpoints, estimate cost from each.


    public Shipment() {

    }

    //constructor is either called by existing user, or a non-existent one,
    // in which case a new customer must be made.
    public Shipment(String address, String zip_code, String name) {
        this.order_id = counter++;
        this.receiving_address = address;
        this.receiving_zip = zip_code;
        this.receiver_name = name;

    }

    //TODO: add total cost => weight * checkpoint cost

    @OneToMany
    @JoinColumn(name = "parcel_id")
    private ArrayList<Parcel> parcels;

    public void addparcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public void setDelivered() {
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }



    public int getTotalWeight() {
        int total_weight = 0;
        for (Parcel parcel : parcels) {
            total_weight += parcel.getWeight();
        }
        return total_weight;
    }

}
