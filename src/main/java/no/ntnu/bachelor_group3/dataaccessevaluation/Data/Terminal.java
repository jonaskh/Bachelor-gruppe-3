package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.Queue;

@Entity
@Table(name = "terminal")
public class Terminal {

    private static Integer counter = 1;

    @Id
    private Integer terminal_id;

    private String address;


    //list of shipments for a period of time. Stored in a queue for first in first out.
    @ManyToMany
    @JoinTable(name = "shipment_id")
    private Queue<Shipment> shipments_passed;

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

    public Queue<Shipment> getShipments_passed() {
        return shipments_passed;
    }

    public boolean addShipment(Shipment shipment) {
        for (Shipment ship: shipments_passed) {
            if (shipment.getShipment_id() == ship.getShipment_id()) {
                return false;
            }
        }
        shipments_passed.add(shipment);
        return true;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "\n terminal_id=" + terminal_id +
                "\n , address='" + address + '\'' +
                '}';
    }
}
