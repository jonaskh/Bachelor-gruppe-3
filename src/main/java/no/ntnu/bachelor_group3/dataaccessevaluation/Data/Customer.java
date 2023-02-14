package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private String address;
    private String name;

    public Customer() {

    }

    public Customer(int customerID, String address) {
        this.customer_id = customerID;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int customerID) {
        this.customer_id = customerID;
    }

    public int getID() {
        return customer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
