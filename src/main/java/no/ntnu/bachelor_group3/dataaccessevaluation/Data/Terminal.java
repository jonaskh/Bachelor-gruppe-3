package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "terminal")
public class Terminal {

    private static Integer counter = 1;

    @Id
    private Integer terminal_id;

    private String address;

    //list of shipments for a period of time. Stored in a queue for first in first out.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "shipment_id")
    private List<Shipment> shipments_passed = new CopyOnWriteArrayList<>();

    public Terminal() {
    }

    public Terminal(String address) {
        this.terminal_id = counter++;
        this.address = address;
    }

    public Integer getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Shipment> getShipments_passed() {
        return shipments_passed;
    }

    public long getShipmentNumber() {
        return shipments_passed.size();
    }

    public void addShipment(Shipment shipment) {
        var exists = false;
        for (Shipment ship: shipments_passed) {
            if (Objects.equals(shipment.getShipment_id(), ship.getShipment_id())) {
                exists = true;
            }
            if (!exists) {
                shipments_passed.add(shipment);
            }
        }
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "\n terminal_id=" + terminal_id +
                "\n , address='" + address + '\'' +
                '}';
    }
}
