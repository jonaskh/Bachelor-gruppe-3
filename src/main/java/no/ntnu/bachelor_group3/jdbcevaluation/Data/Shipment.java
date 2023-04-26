package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private Long id;
    private Customer sender;

    private Customer receiver;
    private Customer payer;
    private double totalCost;
    private List<Parcel> parcels;
    private boolean delivered;
    private List<Terminal> terminals;

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    // Constructor
    public Shipment(Long id, Customer sender, Customer receiver,
                    Customer payer, double totalCost) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.payer = payer;
        this.totalCost = totalCost;
        this.parcels = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.delivered = false;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {
        this.parcels = parcels;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<Terminal> terminals) {
        this.terminals = terminals;
    }

}
