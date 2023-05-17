package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ShipmentRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private TerminalService terminalService;

    private static List<String> shipmentEvals = new ArrayList<>();


    public ShipmentService() {
    }

    public List<String> getShipmentEvals() {
        return shipmentEvals;
    }


    public Shipment findByID(Long id) {
        var before = Instant.now();
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        var duration = Duration.between(before, Instant.now()).toNanos();
        shipmentEvals.add(duration + ", shipment: " + shipment.get().getShipment_id() + ", find");
        return shipment.orElse(null);
    }

    public List<Shipment> findByCustomerID(Long id) {
        List<Shipment> customerShipmentList = new ArrayList<>();
        Iterable<Shipment> shipmentList = shipmentRepository.findAll();
        for(Shipment shipment: shipmentList){
            if(shipment.getPayerID() == id) {
                customerShipmentList.add(shipment);
            }
        }

        return customerShipmentList;
    }

    public long returnNrOfParcels(Shipment ship) {
        return ship.getParcels().size();
    }

    /**
     * adds shipment to the database, if any customers is not saved, save them as well, also connects it to terminals.
     *
     *
     * @param shipment
     */
    @Transactional
    public void addShipment(Shipment shipment) {

        setFirstTerminalToShipment(shipment);
        setEndTerminalToShipment(shipment);

        shipment.getSender().addShipment(shipment);

        try {
            var before = Instant.now(); //evaluates the time taken by repository method.

            shipmentRepository.save(shipment);
            var duration = Duration.between(before, Instant.now());

            shipmentEvals.add(duration.get(ChronoUnit.NANOS) + ", shipment: " + shipment.getShipment_id() + ", create");
        } catch (HibernateException e) {
            System.out.println("Shipment with that ID already exists");
        }
    }

    /**
     * Adds a checkpoint to all parcels in the shipment, if the checkpoint is connected to a terminal, add shipment to it.
     * @param shipment   to add checkpoint to
     * @param checkpoint which checkpoint to add
     */
    @Transactional
    public void updateCheckpointsOnParcels(Shipment shipment, Checkpoint checkpoint, Checkpoint cp2) {

        checkpointService.addCheckpoint(cp2);
        checkpointService.addCheckpoint(checkpoint);
        parcelService.addCheckpointToParcel(checkpoint, shipment.getParcels().get(0));
        parcelService.addCheckpointToParcel(cp2, shipment.getParcels().get(1));
    }




    //returns the last checkpoint for the shipment, used by customer to track location in Runnable
    //assumes all parcels in shipment has same location.
    @Transactional
    public Checkpoint getLocation(Shipment shipment) {
        return findByID(shipment.getShipment_id()).getParcels().get(0).getLastCheckpoint();
    }

    @Transactional
    public void setDelivered(Shipment shipment) {
        var before = Instant.now();
        findByID(shipment.getShipment_id()).setDelivered();
        var duration = Duration.between(before, Instant.now()).toNanos();
        shipmentEvals.add(duration  + " , shipment update");
    }


    //set the terminal connected to zip code of the shipments sender
    @Transactional
    public void setFirstTerminalToShipment(Shipment shipment) {
        shipment.setFirstTerminal(terminalService.returnTerminalFromZip(shipment.getSender().getZip_code()));
    }


    @Transactional
    //set the terminal connected to zip code of the shipments receiver
    public void setEndTerminalToShipment(Shipment shipment) {
        shipment.setFinalTerminal(terminalService.returnTerminalFromZip(shipment.getReceiver().getZip_code()));
    }

    @Transactional
    //returns total number of shipments in the database, regardless of status.
    public long count() {
        var before = Instant.now();
        var count = shipmentRepository.count();
        var duration = Duration.between(before, Instant.now());
        var result = duration + ", shipment read all";

        shipmentEvals.add(result);
        return count;
    }

    public void returnTerminals(Shipment shipment) {
        shipmentRepository.findAllByFinalTerminal(terminalService.returnTerminalFromZip(shipment.getSender().getZip_code()));
    }

    @Transactional
    public void deleteOneShipment(Long shipmentId) {
        var before = Instant.now();
        shipmentRepository.deleteById(shipmentId);
        var duration = Duration.between(before, Instant.now()).toNanos();
        shipmentEvals.add(duration + " , shipment delete");
    }

}
