package no.ntnu.bachelor_group3.dataaccessevaluation.Data;


import javax.persistence.*;


@Entity
public class AdditionalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int service_id;

    private String name;

}
