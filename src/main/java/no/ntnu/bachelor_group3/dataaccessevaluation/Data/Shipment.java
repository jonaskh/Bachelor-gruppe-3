package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;


@Entity
public class Shipment {


    private static Long counter = 1L;



    @Id
    private Long shipment_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Customer sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Customer receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id")
    private Customer payer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_terminal_id")
    private Terminal firstTerminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_terminal_id")
    private Terminal finalTerminal;

    private boolean delivered;


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
        this.sender = sender;
        this.payer = payer;
        this.receiver = receiver;
    }

    public Shipment(Customer sender, Customer payer, String receiver_address, String receiver_zip, String receiver_name) {
        this.shipment_id = counter++;
        this.sender = sender;
        this.payer = payer;

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
