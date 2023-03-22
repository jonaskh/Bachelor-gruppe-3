package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private Long id;
    private String location;
    private List<Shipment> shipments;

    public Terminal(Long id, String location) {
        this.id = id;
        this.location = location;
        shipments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }
}
