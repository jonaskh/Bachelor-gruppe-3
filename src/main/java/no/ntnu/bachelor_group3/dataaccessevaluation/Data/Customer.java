package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import java.util.Set;

//TODO: Add connection to other tables
@Entity
@Table(name = "customer")
public class Customer {


    private static int counter_id = 1; //unique id, incremented each time a new customer is made.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

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
        this.customer_id = counter_id++;
        this.address = address;
        this.name = name;
        this.zip_code = zip_code;
    }

    public int getCustomerID() {
        return customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
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
                ", zip='" + zip_code + '\'' +
                '}';
    }
}
