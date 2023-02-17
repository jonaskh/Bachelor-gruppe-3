package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Set;

//TODO: Add connection to other tables
@Entity
public class Customer {


    private static int counter_id = 1; //unique id, incremented each time a new customer is made.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;
    private String address;
    private String name;

    private String zip_code;


    //Set of orders for each customer
    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<Shipment> shipments;

    public Customer() {
    }

    public Customer(String address, String name, String zip_code) {
        this.customerID = counter_id++;
        this.address = address;
        this.name = name;
        this.zip_code = zip_code;
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
                ", zip='" + zip_code + '\'' +
                '}';
    }
}
