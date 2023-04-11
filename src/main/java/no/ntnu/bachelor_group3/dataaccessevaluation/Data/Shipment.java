package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


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

    @OneToOne
    @JoinColumn(name = "terminal_id")
    private Terminal firstTerminal;

    @OneToOne
    @JoinColumn(name = "terminal_id")
    private Terminal finalTerminal;

    private boolean delivered;

    @JoinColumn(name = "customer_id")
    private Long payer_id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parcel_id")
    private List<Parcel> parcels = new ArrayList<>();

    private LocalDateTime timeCreated;

    private LocalDateTime expectedDeliveryDate;



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
        this.timeCreated = LocalDateTime.now();
        this.expectedDeliveryDate = LocalDateTime.now().plusSeconds(10); //sets expected delivery date 10s from creation
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
        if (parcels.isEmpty()) {
            System.out.println("Could not find any parcels");
        } else {
            for (Parcel parcel : parcels) {
                System.out.println(parcel.toString());
            }
        }
        return "Number of parcels: " +parcels.size();
    }


    @Override
    public String toString() {
        return "Shipment{" +
                "shipment_id=" + shipment_id +
                ", sender_id='" + sender + '\'' +
                ", receiver_zip='" + receiver + '\'' +
                "nr of parcels: " + parcels.size() +
                ", expected delivery: " + expectedDeliveryDate +
                '}';
    }
}
