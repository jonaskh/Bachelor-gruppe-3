package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private String address;

    public customer() {

    }

    public customer(int customerID, String address) {
        this.customer_id = customerID;
        this.address = address;
    }

    public int getCustomerID() {
        return customer_id;
    }

    public void setCustomerID(int customerID) {
        this.customer_id = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
