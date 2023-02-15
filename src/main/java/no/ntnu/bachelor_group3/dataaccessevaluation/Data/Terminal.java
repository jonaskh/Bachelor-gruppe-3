package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "terminal")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int terminal_id;

    private String address;
}
