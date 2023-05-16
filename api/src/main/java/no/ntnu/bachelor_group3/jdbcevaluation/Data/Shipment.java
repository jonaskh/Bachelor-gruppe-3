package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private Long id;
    private Customer sender;

    private Customer receiver;
    private Customer payer;
    private float totalCost;
    private List<Parcel> parcels;
    private boolean delivered;

    private Terminal startTerminal;
    private Terminal endTerminal;

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    // Constructor
    public Shipment(Long id, Customer sender, Customer receiver,
                    Customer payer, float totalCost) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.payer = payer;
        this.totalCost = totalCost;
        this.parcels = new ArrayList<>();
        this.delivered = false;
    }

    public Terminal getStartTerminal() {
        return startTerminal;
    }

    public void setStartTerminal(Terminal startTerminal) {
        this.startTerminal = startTerminal;
    }

    public Terminal getEndTerminal() {
        return endTerminal;
    }

    public void setEndTerminal(Terminal endTerminal) {
        this.endTerminal = endTerminal;
    }

    // Getters and setters for each field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getPayer() {
        return payer;
    }

    public void setPayer(Customer payer) {
        this.payer = payer;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {
        this.parcels = parcels;
    }
}
