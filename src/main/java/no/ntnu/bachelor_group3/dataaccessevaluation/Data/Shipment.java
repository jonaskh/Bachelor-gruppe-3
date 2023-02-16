package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Shipment {

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

    //TODO: Either add total weight of parcels in order here, or link weight in parcels directly to checkpoint.

    @OneToMany
    @JoinColumn(name = "parcel_id")
    private ArrayList<Parcel> parcels;



    public void getTotalWeight() {
        for (Parcel parcel : parcels) {

        }
    }

}
