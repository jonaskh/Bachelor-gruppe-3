package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.Date;

enum CheckpointType{Collected, ReceivedFirstTerminal,LoadedOnCar,ReceivedFinalTerminal,UnderDelivery,Delivered}

@Entity
@Table(name = "checkpoint")
public class Checkpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkpoint_id;


    //current address of the checkpoint, received from terminal/sender/receiver
    private String location;

    private CheckpointType type;

    private int cost;

    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;


    private Date time;
}
