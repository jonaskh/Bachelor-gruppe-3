package no.ntnu.bachelor_group3.dataaccessevaluation.Data;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int service_id;

    private String name;

}
