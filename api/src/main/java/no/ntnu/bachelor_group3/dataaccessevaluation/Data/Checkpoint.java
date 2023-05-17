package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "checkpoint")
public class Checkpoint {



    //format used for the timestamps
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Long counter = 1L;

    @Id
    private Long checkpoint_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;




    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;



    public enum CheckpointType{Collected, ReceivedFirstTerminal,LoadedOnCar,ReceivedFinalTerminal,UnderDelivery,Delivered}

    private CheckpointType type;

    private String location;

    private double cost;

    public Checkpoint() {
    }

    public Checkpoint(String location, CheckpointType type) {
        this.location = location;
        this.checkpoint_id = counter++;
        this.type = type;
        switch (type) {
            case Collected -> this.cost = 1;
            case ReceivedFinalTerminal, ReceivedFirstTerminal -> this.cost = 1.25;
            case LoadedOnCar -> this.cost = 1.5;
            case UnderDelivery -> this.cost = 1.8;
            case Delivered -> this.cost = 2;
        }
    }

    public Checkpoint(Terminal terminal, CheckpointType type) {
        this.checkpoint_id = counter++;
        this.type = type;
        this.terminal = terminal;
        switch (type) {
            case Collected -> this.cost = 1;
            case ReceivedFinalTerminal, ReceivedFirstTerminal -> this.cost = 1.25;
            case LoadedOnCar -> this.cost = 1.5;
            case UnderDelivery -> this.cost = 1.8;
            case Delivered -> this.cost = 2;
        }
    }


    public void setType(CheckpointType type) {
        this.type = type;

    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CheckpointType getType() {
        return this.type;
    }

    public Long getCheckpoint_id() {
        return this.checkpoint_id;
    }


    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "checkpoint_id=" + checkpoint_id +
                ", type=" + type +
                ", cost=" + cost +
                '}';
    }
}
