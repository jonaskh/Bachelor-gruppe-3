package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//TODO: Add connection to other tables
@Entity
public class Customer {

    @Id
    private int customerID;
    private String address;
    private String name;

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
