package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import java.util.Date;

public class Checkpoint {
    private Long id;
    private float cost;
    private String location;
    private Date time;
    private Parcel parcel;
    private Terminal terminal;
    private CheckpointType type;

    public enum CheckpointType{
        Collected, ReceivedFirstTerminal,LoadedOnCar,ReceivedFinalTerminal,UnderDelivery,Delivered;

        public static CheckpointType fromInteger(int x) {
            switch(x) {
                case 0:
                    return Collected;
                case 1:
                    return ReceivedFirstTerminal;
                case 2:
                    return LoadedOnCar;
                case 3:
                    return ReceivedFinalTerminal;
                case 4:
                    return UnderDelivery;
                case 5:
                    return Delivered;
            }
            return null;
        }
    }

    public CheckpointType getType() {
        return type;
    }

    public void setType(CheckpointType type) {
        this.type = type;
    }

    public Checkpoint(Long id, float cost, String location, Parcel parcel, Terminal terminal, CheckpointType checkpointType) {
        this.id = id;
        this.cost = cost;
        this.location = location;
        this.time = new Date();
        this.parcel = parcel;
        this.terminal = terminal;
        this.type = checkpointType;
    }

    public Checkpoint(String location, CheckpointType type) {
        this.location = location;
        this.time = new Date();
        this.type = type;
        switch (type) {
            case Collected -> this.cost = 1f;
            case ReceivedFinalTerminal, ReceivedFirstTerminal -> this.cost = 1.25f;
            case LoadedOnCar -> this.cost = 1.5f;
            case UnderDelivery -> this.cost = 1.8f;
            case Delivered -> this.cost = 2f;
        }
    }

    public Checkpoint(Terminal terminal, CheckpointType type) {
        this.time = new Date();
        this.type = type;
        this.terminal = terminal;
        switch (type) {
            case Collected -> this.cost = 1f;
            case ReceivedFinalTerminal, ReceivedFirstTerminal -> this.cost = 1.25f;
            case LoadedOnCar -> this.cost = 1.5f;
            case UnderDelivery -> this.cost = 1.8f;
            case Delivered -> this.cost = 2f;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}
