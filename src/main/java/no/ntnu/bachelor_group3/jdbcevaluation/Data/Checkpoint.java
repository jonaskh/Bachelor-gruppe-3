package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import java.util.Date;

public class Checkpoint {
    private Long id;
    private double cost;
    private String location;
    private Date time;
    private Parcel parcel;
    private Terminal terminal;

    public Checkpoint(Long id, double cost, String location, Date time, Parcel parcel) {
        this.id = id;
        this.cost = cost;
        this.location = location;
        this.time = time;
        this.parcel = parcel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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
