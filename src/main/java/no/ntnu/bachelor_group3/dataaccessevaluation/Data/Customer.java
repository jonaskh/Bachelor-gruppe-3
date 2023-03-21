package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import com.github.javafaker.Faker;
import javax.persistence.*;

import java.util.Locale;
import java.util.Set;

//TODO: Add connection to other tables
@Entity
@Table(name = "customer")
public class Customer {


    //private static int counter_id = 1; //unique id, incremented each time a new customer is made.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Customer_id;

    private String address;
    private String name;
    private String zip_code;

    //Set of shipments for each customer
    @OneToMany
    @JoinColumn(name = "shipment_id")
    private Set<Shipment> shipments;

    /**
     * Cosntructor that user JavcFaker to generate values
     */
    public Customer() {
        Faker faker = new Faker(new Locale("nb-NO"));

        this.address = faker.address().streetAddress();
        this.name = faker.name().fullName();
        this.zip_code = faker.address().zipCode(); //TODO: Check for valid codes
    }


    /**
     * Constructor for manual input of values
     */
    public Customer(String address, String name, String zip_code) {
        this.address = address;
        this.name = name;
        this.zip_code = zip_code;
    }

    public Long getCustomerID() {
        return Customer_id;
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


    /**
     * Create a shipment where receiver and sender is same customer
     */
    public void createShipment() {
        Shipment newShipment = new Shipment(this.Customer_id);
    }

    public void createShipment(Customer customer) {

    }


    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + Customer_id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", zip='" + zip_code + '\'' +
                '}';
    }


}
