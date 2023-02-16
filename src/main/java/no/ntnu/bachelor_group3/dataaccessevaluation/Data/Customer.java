package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.HashMap;

//TODO: Add connection to other tables
@Entity
public class Customer {

    @Id
    private int customerID;
    private String address;
    private String name;


    //HashMap of orders for each customer
    @OneToMany
    @JoinColumn(name = "order_id")
    private HashMap<Integer, Shipment> orders;

    public Customer() {

    }

    public Customer(int customerID, String address, String name) {
        this.customerID = customerID;
        this.address = address;
        this.name = name;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
