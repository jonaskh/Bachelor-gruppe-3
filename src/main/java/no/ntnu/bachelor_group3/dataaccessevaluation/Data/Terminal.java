package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;

import java.util.Set;

@Entity
@Table(name = "terminal")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long terminal_id;

    private String address;

    //list of shipments for a period of time
    /*@OneToMany
    @JoinTable(name = "shipment_id")
    private Set<Shipment> shipments_passed;*/

    public Terminal() {
    }

    public Terminal(String address) {
        this.address = address;
        //this.shipments_passed = shipments_passed;
    }

    public Long getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Long terminal_id) {
        this.terminal_id = terminal_id;
    }

    public static Terminal getTerminalById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit-name");
        EntityManager em = emf.createEntityManager();
        Terminal terminal = null;
        try {
            terminal = em.find(Terminal.class, id);
        } finally {
            em.close();
            emf.close();
        }
        return terminal;
    }


    public void createTerminals(){
        TerminalService terminalService = new TerminalService();

        String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
                "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
                "NORDLAND", "TROMS", "FINNMARK"};


        for (int i=0; i<=18; i++) {
            Terminal terminal = new Terminal(terminalAddresses[i]);
            terminalService.add(terminal);
        }
    }
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*public Set<Shipment> getShipments_passed() {
        return shipments_passed;
    }

    public void setShipments_passed(Set<Shipment> shipments_passed) {
        this.shipments_passed = shipments_passed;
    }*/
}
