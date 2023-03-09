package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "terminal")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long terminal_id;

    private String address;

    //list of shipments for a period of time
    @OneToMany
    @JoinTable(name = "shipment_id")
    private Set<Shipment> shipments_passed;

    public Terminal() {
    }

    public Terminal(String address, Set<Shipment> shipments_passed) {
        this.address = address;
        this.shipments_passed = shipments_passed;
    }

    public Long getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Long terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Shipment> getShipments_passed() {
        return shipments_passed;
    }

    public void setShipments_passed(Set<Shipment> shipments_passed) {
        this.shipments_passed = shipments_passed;
    }
}
