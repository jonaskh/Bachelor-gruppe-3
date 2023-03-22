package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "checkpoint")
public class Checkpoint {

    //format used for the timestamps
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Long counter = 1L;

    @Id
    private Long checkpoint_id;

    public enum CheckpointType{Collected, ReceivedFirstTerminal,LoadedOnCar,ReceivedFinalTerminal,UnderDelivery,Delivered}



    private CheckpointType type;

    private double cost;

    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;


    private Date time;

    public Checkpoint() {
    }

    public Checkpoint(String location, CheckpointType type) {
        this.time = new Date();
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

    public void setType(CheckpointType type) {
        this.type = type;

    }

    public CheckpointType getType() {
        return this.type;
    }

    public Long getCheckpoint_id() {
        return this.checkpoint_id;
    }

    public String getTime() {
        return sdf3.format(time);
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "checkpoint_id=" + checkpoint_id +
                ", type=" + type +
                ", cost=" + cost +
                ", terminal=" + terminal +
                ", time=" + sdf3.format(time) +
                '}';
    }
}
