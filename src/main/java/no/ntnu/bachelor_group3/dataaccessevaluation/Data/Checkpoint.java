package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.Date;


@Entity
@Table(name = "checkpoint")
public class Checkpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkpoint_id;

    enum CheckpointType{Collected, ReceivedFirstTerminal,LoadedOnCar,ReceivedFinalTerminal,UnderDelivery,Delivered}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parcel")
    private Parcel parcel;


    private CheckpointType type;

    private double cost;

    private String location;

    public void setType(CheckpointType type) {
        this.type = type;

    }

    public Checkpoint() {

    }

    public Checkpoint(String location) {
        switch (type) {
            case Collected -> this.cost = 1;
            case ReceivedFinalTerminal, ReceivedFirstTerminal -> this.cost = 1.25;
            case LoadedOnCar -> this.cost = 1.5;
            case UnderDelivery -> this.cost = 1.8;
            case Delivered -> this.cost = 2;
        }
    }

    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;


    private Date time;
}
