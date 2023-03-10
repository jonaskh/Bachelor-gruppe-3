package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private Long id;
    private Customer sender;

    private String receiving_address;
    private String receiving_zip;
    private String receiver_name;
    private Customer payer;
    private double totalCost;
    private List<Parcel> parcels;
    private boolean delivered;

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    // Constructor
    public Shipment(Long id, Customer sender, String receiving_address, String receiving_zip, String receiver_name,
                    Customer payer, double totalCost) {
        this.id = id;
        this.sender = sender;
        this.receiving_address = receiving_address;
        this.receiving_zip = receiving_zip;
        this.receiver_name = receiver_name;
        this.payer = payer;
        this.totalCost = totalCost;
        this.parcels = new ArrayList<>();
        this.delivered = false;
    }

    // Getters and setters for each field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
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

    // Instance method to save this shipment to the database


    // Instance method to delete this shipment from the database


    // Static method to retrieve a shipment from the database given an ID

}
