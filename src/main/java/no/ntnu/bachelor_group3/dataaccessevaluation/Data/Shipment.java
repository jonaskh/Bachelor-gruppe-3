package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import com.github.javafaker.Faker;
import javax.persistence.*;

import java.util.Locale;
import java.util.Set;


@Entity
public class Shipment {

    private static Faker faker = new Faker(new Locale("nb-NO"));


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipment_id;

    @JoinColumn(name = "customer_id")
    private Long customer_id;

    private String receiving_address;

    private String receiving_zip;

    private String receiver_name;

    private boolean delivered;

    @JoinColumn(name = "customer_id")
    private Long payer_id;

    @OneToMany
    @JoinColumn(name = "parcel_id")
    private Set<Parcel> parcels;



    public Shipment() {
    }

    public Shipment(Long customer_id) {

    }


    //TODO: add total cost => weight * checkpoint cost


    public Long getShipment_id() {
        return shipment_id;
    }

    public String getReceiving_address() {
        return receiving_address;
    }

    public void setReceiving_address(String receiving_address) {
        this.receiving_address = receiving_address;
    }

    public String getReceiving_zip() {
        return receiving_zip;
    }

    public void setReceiving_zip(String receiving_zip) {
        this.receiving_zip = receiving_zip;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Long getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(Long payer_id) {
        this.payer_id = payer_id;
    }

    public Set<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(Set<Parcel> parcels) {
        this.parcels = parcels;
    }

    public void setShipment_id(Long shipment_id_id) {
        this.shipment_id = shipment_id;
    }

    public void addparcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public void setDelivered() {
    }

    public void setCustomer_id(Long customer_id) {

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
