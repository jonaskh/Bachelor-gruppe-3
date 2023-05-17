package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


@Entity
public class Shipment {


    private static Long counter = 1L;

    @Version
    private Long shipment_version = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipment_id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Customer receiver;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Customer payer;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "start_terminal_id")
//    private Terminal firstTerminal;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "end_terminal_id")
//    private Terminal finalTerminal;

    private boolean delivered;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY, mappedBy = "parcel_id")
    private List<Parcel> parcels = new CopyOnWriteArrayList<>();

    private LocalDateTime timeCreated;

    private LocalDateTime expectedDeliveryDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_terminal_id")
    private Terminal firstTerminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_terminal_id")
    private Terminal finalTerminal;


    // if customer is both sender and receiver
    public Shipment() {
        this.shipment_id = counter++;
    }

    // if receiver is not an existing customer
    public Shipment(Customer sender, Customer payer, Customer receiver) {
        this.shipment_id = counter++;
        this.timeCreated = LocalDateTime.now();
        this.expectedDeliveryDate = LocalDateTime.now().plusSeconds(10); //sets expected delivery date 10s from creation
        this.sender = sender;
        this.payer = payer;
        this.receiver = receiver;
        addParcels();
    }

    public Shipment(Customer sender, Customer payer, String receiver_address, String receiver_zip, String receiver_name) {
        this.shipment_id = counter++;
        this.sender = sender;
        this.payer = payer;
        addParcels();

    }


    //TODO: add total cost => weight * checkpoint cost


    //TODO: Set terminals based on zip codes


    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public Customer getPayer() {
        return payer;
    }

    public void setPayer(Customer payer) {
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

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(ArrayList<Parcel> parcels) {
        this.parcels = parcels;
    }

    public Long getSenderID() {
        return this.sender.getCustomerID();
    }
    public Long getReceiverID() {
        return this.receiver.getCustomerID();
    }
    public Long getPayerID() {
        return this.payer.getCustomerID();
    }

    public void addParcel(Parcel parcel) {

        this.parcels.add(parcel);
    }

    public void setDelivered() {
    }

    public void addParcels() {

        Random random = new Random();
        int bound = random.nextInt(5) + 1; //generate random number of parcels added, always add 1 to avoid zero values


        for (int i = 0; i < bound; i++) {
            float weight = random.nextFloat(10); //returns a double value up to 10kg with 2 decimal places
            Parcel parcel = new Parcel(this, weight);
            addParcel(parcel);
        }
    }


    public int getTotalWeight() {
        int total_weight = 0;
        for (Parcel parcel : parcels) {
            total_weight += parcel.getWeight();
        }
        return total_weight;
    }

    //for testing
    public void printParcels() {
        if (parcels.isEmpty()) {
            System.out.println("Could not find any parcels");
        } else {
            for (Parcel parcel : parcels) {
                System.out.println(parcel.toString());
            }
        }
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