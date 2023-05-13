package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ShipmentRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TerminalService terminalService;



    private static List<String> shipmentEvals = new CopyOnWriteArrayList<>();


    public ShipmentService() {

    }

    public List<String> getShipmentEvals() {
        return shipmentEvals;
    }



    @Transactional
    public Shipment findByID(Long id) {
        var before = Instant.now();
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        var duration = Duration.between(before, Instant.now()).toNanos();
        shipmentEvals.add(duration + ", shipment: " + shipment.get().getShipment_id() + ", find");
        return shipment.orElse(null);
    }

    public long returnNrOfParcels(Shipment ship) {
        return ship.getParcels().size();
    }

    public void saveParcelsToDatabaseFromShipment(Shipment shipment) {
        parcelService.saveAll(findByID(shipment.getShipment_id()).getParcels());
    }

    /**
     * adds shipment to the database, if any customers is not saved save them as well, also connects it to terminals.
     *
     * @param shipment
     */
    //TODO: CASCADING SAVE CHILD ENTITIES VS MANUAL
    @Transactional
    public void cascadingAdd(Shipment shipment) {

        if (customerService.findByID(shipment.getSenderID()).isEmpty()) {
            customerService.save(shipment.getSender());
        }
        if (customerService.findByID(shipment.getReceiverID()).isEmpty()) {
            customerService.save(shipment.getReceiver());
        }
        if (customerService.findByID(shipment.getPayerID()).isEmpty()) {
            customerService.save(shipment.getPayer());
        }
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
    public void updateCheckpointsOnParcels(Shipment shipment, Checkpoint checkpoint) {
        for (Parcel parcel : shipment.getParcels()) {
            parcelService.addCheckpointToParcel(checkpoint, parcel);
           checkpointService.addCheckpoint(checkpoint);
        }

        //adds the shipment to terminal if checkpoint is at a terminal and has not already passed it.
        if (checkpoint.getTerminal() != null) {
            addTerminal(checkpoint.getTerminal(), shipment);
        }
    }

    public void addTerminal(Terminal terminal, Shipment shipment) {
        shipment.addTerminal(terminal);
        terminalService.addShipment(shipment, terminal);
    }

    //returns the last checkpoint for the shipment, used by customer to track location in Runnable
    //assumes all parcels in shipment has same location.
    @Transactional
    public List<Checkpoint> getLocation(Shipment shipment) {
        return findByID(shipment.getShipment_id()).getParcels().get(0).getCheckpoints();
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

    //returns total number of shipments in the database, regardless of status.
    public long count() {
        var before = Instant.now();
        var count = shipmentRepository.count();
        var duration = Duration.between(before, Instant.now());
        var result = duration + ", shipment read all";

        shipmentEvals.add(result);
        return count;
    }

    @Transactional
    public void deleteOneShipment(Long shipmentId) {
        shipmentRepository.deleteById(shipmentId);
    }
//
//    //saves a shipment to the repository, and thus the database
//    public long add(Shipment shipment) {
//        long saveShipmentTimeEval = 0;
//        if (findByID(shipment.getShipment_id()) == null) {
//            if (customerService.findByID(shipment.getSenderID()).isEmpty()) {
//                customerService.add(shipment.getSender());
//            }
//            if (customerService.findByID(shipment.getReceiverID()).isEmpty()) {
//                customerService.add(shipment.getReceiver());
//            }
//
//            var beforeSaveShipment = System.currentTimeMillis();
//            shipmentRepository.save(shipment);
//            saveShipmentTimeEval = System.currentTimeMillis() - beforeSaveShipment;
//            System.out.println("Shipment: " + shipment.getShipment_id() + " has been added to the database");
//            saveParcelsToDatabaseFromShipment(shipment);
//
//
//        } else {
//            System.out.println("Shipment already exists in database");
//
//        }
//        return saveShipmentTimeEval;
//    }

}
