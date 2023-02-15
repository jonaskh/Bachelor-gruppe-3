package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Checkpoint_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkpoint_type_id;

    private String checkpoint_name;

    private String checkpoint_cost;
}
