package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "terminal")
public class Terminal {

    private static Integer counter = 1;


    @Id
    private Integer terminal_id;

    private String address;

    //contains all checkpoints that are registered in a terminal
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "checkpoint_id_terminal")
    private List<Checkpoint> checkpoints = new ArrayList<>();


    //list of shipments for a period of time. Stored in a queue for first in first out.
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipments_terminal")
    private List<Shipment> shipments_passed = new ArrayList<>();

    public Terminal() {
    }

    public Terminal(String address) {
        this.terminal_id = counter++;
        this.address = address;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
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

    public long getCheckpointNumber() {
        return checkpoints.size();
    }

    public void addShipment(Shipment shipment) {
        shipments_passed.add(shipment);
    }
//    public void addShipment(Shipment shipment) {
//        boolean exists = false;
//        if (!shipments_passed.isEmpty()) {
//            for (Shipment ship : shipments_passed) {
//                if (ship == shipment) {
//                    exists = true;
//                }
//                if (!exists) {
//                    shipments_passed.add(shipment);
//                    System.out.println("Added shipment to terminal");
//                } else {
//                    System.out.println("Shipment already exists");
//                }
//            }
//        } else {
//            shipments_passed.add(shipment);
//        }
//    }

    public void addCheckpoint(Checkpoint cp) {
        checkpoints.add(cp);
    }




    @Override
    public String toString() {
        return "Terminal{" +
                "\n terminal_id=" + terminal_id +
                "\n , address='" + address + '\'' +
                '}';
    }
}
