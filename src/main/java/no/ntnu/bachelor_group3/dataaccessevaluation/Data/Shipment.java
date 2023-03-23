package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;


@Entity
public class Shipment {

    //how to display the date
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Faker faker = new Faker(new Locale("nb-NO"));

    private static Long counter = 1L;

    @Id
    private Long shipment_id;

    @JoinColumn(name = "customer_id")
    private Long sender;

    @JoinColumn(name = "customer_id")
    private Long receiver;

    @JoinColumn(name = "customer_id")
    private Long payer;

    private boolean delivered;

    @JoinColumn(name = "customer_id")
    private Long payer_id;

    @OneToMany
    @JoinColumn(name = "parcel_id")
    private List<Parcel> parcels = new ArrayList<>();



    // if customer is both sender and receiver
    public Shipment() {
        this.shipment_id = counter++;
    }

    // if receiver is another customer than the one making the shipment
    public Shipment(Long customer_id) {
        this.shipment_id = counter++;
    }

    // if receiver is not an existing customer
    public Shipment(Customer sender, Customer payer, Customer receiver) {
        this.shipment_id = counter++;
        this.sender = sender.getCustomerID();
        this.payer = payer.getCustomerID();
        this.receiver = receiver.getCustomerID();
    }

    public Shipment(Customer sender, Customer payer, String receiver_address, String receiver_zip, String receiver_name) {
        this.shipment_id = counter++;
        this.sender = sender.getCustomerID();
        this.payer = payer.getCustomerID();

    }


    //TODO: add total cost => weight * checkpoint cost


    //TODO: Set terminals based on zip codes


    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Long getPayer() {
        return payer;
    }

    public void setPayer(Long payer) {
        this.payer = payer;
    }

    public Long getShipment_id() {
        return shipment_id;
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

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(ArrayList<Parcel> parcels) {
        this.parcels = parcels;
    }

    public void setShipment_id(Long shipment_id_id) {
        this.shipment_id = shipment_id;
    }

    public void addParcel(Parcel parcel) {

        this.parcels.add(parcel);
    }

    public void setDelivered() {
    }


    public int getTotalWeight() {
        int total_weight = 0;
        for (Parcel parcel : parcels) {
            total_weight += parcel.getWeight();
        }
        return total_weight;
    }

    //for testing
    public String printParcels() {
        for (Parcel parcel : parcels) {
            System.out.println(parcel.toString());
        }
        return "Number of parcels: " +parcels.size();
    }


    @Override
    public String toString() {
        return "Shipment{" +
                "shipment_id=" + shipment_id +
                ", sender_id='" + sender + '\'' +
                ", receiver_zip='" + receiver + '\'' +
                '}';
    }
}
