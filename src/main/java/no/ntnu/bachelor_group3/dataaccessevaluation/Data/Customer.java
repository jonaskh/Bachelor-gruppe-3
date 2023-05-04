package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "customer")
public class Customer {

    private static Long counter = 1L;
    private static final Faker faker = new Faker(new Locale("nb-NO"));

    //private static int counter_id = 1; //unique id, incremented each time a new customer is made.
    @Id
    @Column(nullable = false)
    private Long customer_id;

    private String address;
    private String name;
    private String zip_code;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;



    //Set of shipments for each customer
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_shipments")
    private List<Shipment> shipments = new ArrayList<>();

    /**
     * Constructor that user JavaFaker to generate values
     */
    public Customer() {

        this.customer_id = counter++;
        this.address = faker.address().streetAddress();
        this.name = faker.name().fullName();
        this.zip_code = faker.address().zipCode(); //TODO: Check for valid codes
    }


    /**
     * Constructor for manual input of values
     */
    public Customer(String address, String name, String zip_code) {

        this.customer_id = counter++;
        this.address = address;
        this.name = name;
        this.zip_code = zip_code;
    }

    public Long getCustomerID() {
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

    public List<Shipment> getShipments() {
        return this.shipments;
    }

    public void addShipment(Shipment shipment) {
        shipments.add(shipment);
    }

    /**
     * Add a shipment to the customer
     */



    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
