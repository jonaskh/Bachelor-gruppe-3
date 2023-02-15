package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    // foreign key to sending customer id so can link to zip code for receiving terminal
    private int sender_id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String receiving_address;

    private String receiving_zip;

    private String receiver_name;
}
