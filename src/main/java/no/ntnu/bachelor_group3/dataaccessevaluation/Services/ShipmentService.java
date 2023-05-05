package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import com.sun.istack.NotNull;
import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Generators.WriteToEval;
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
import java.util.Objects;
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

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final WriteToEval writeToEval = new WriteToEval();

    private static List<String> shipmentEvals = new ArrayList<>();

    private static long totalShipments = 0;

    public ShipmentService() {

    }

    public List<String> getShipmentEvals() {
        return shipmentEvals;
    }

    public void printParcelsFromDB(Shipment shipment) {
        if (findByID(shipment.getShipment_id()) != null) {
            for (Parcel parcel : findByID(shipment.getShipment_id()).getParcels()) {
                System.out.println(parcel.getParcel_id());
            }
        }
    }

    @Transactional
    public Shipment findByID(Long id) {
        var before = Instant.now();
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        var duration = Duration.between(before, Instant.now()).toNanos();
        shipmentEvals.add(duration + ", shipment find");
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
    public Shipment cascadingAdd(Shipment shipment) {
        Shipment ship = null;
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
        customerService.addShipment(shipment, shipment.getSender());

        var before = Instant.now(); //evaluates the time taken by repository method.
        try {

            ship = shipmentRepository.save(shipment);

        } catch (HibernateException e) {
            System.out.println("Shipment with that ID already exists");
        }
        var duration = Duration.between(before, Instant.now());
        shipmentEvals.add(duration.get(ChronoUnit.NANOS) + ", shipment," + shipment.getShipment_id() + ", create");

        return ship;
    }


    /**
     * Adds a checkpoint to all parcels in the shipment, if the checkpoint is connected to a terminal, add shipment to it.
     * @param shipment   to add checkpoint to
     * @param checkpoint which checkpoint to add
     */
    @Transactional
    public void updateCheckpointsOnParcels(Shipment shipment, Checkpoint checkpoint) {
        for (Parcel parcel : shipment.getParcels()) {
            parcel.setLastCheckpoint(checkpoint);
            //parcelService.addCheckpointToParcel(checkpoint,parcel);
            var before = Instant.now();
            checkpointService.addCheckpoint(checkpoint);
            var duration = Duration.between(before, Instant.now()).toNanos();
            shipmentEvals.add(duration + " , checkpoint, create");
        }

        //adds the shipment to terminal if checkpoint is at a terminal and has not already passed it.
        if (checkpoint.getTerminal() != null) {
            terminalService.addShipment(shipment, checkpoint.getTerminal());

            terminalService.addCheckpoint(checkpoint, checkpoint.getTerminal());
        }
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
        totalShipments++;
    }

    //saves a shipment to the repository, and thus the database
    public long add(Shipment shipment) {
        long saveShipmentTimeEval = 0;
        if (findByID(shipment.getShipment_id()) == null) {
            if (customerService.findByID(shipment.getSenderID()).isEmpty()) {
                customerService.add(shipment.getSender());
            }
            if (customerService.findByID(shipment.getReceiverID()).isEmpty()) {
                customerService.add(shipment.getReceiver());
            }

            var beforeSaveShipment = System.currentTimeMillis();
            shipmentRepository.save(shipment);
            saveShipmentTimeEval = System.currentTimeMillis() - beforeSaveShipment;
            System.out.println("Shipment: " + shipment.getShipment_id() + " has been added to the database");
            saveParcelsToDatabaseFromShipment(shipment);

            //TODO: EVALUATE

        } else {
            System.out.println("Shipment already exists in database");

        }
        return saveShipmentTimeEval;
    }

//    @Transactional
//    public void updateFirstCheckpointsOnParcels(Shipment shipment) {
//        Checkpoint checkpoint = new Checkpoint(shipment.getSender().getAddress(), Checkpoint.CheckpointType.Collected);
//        for (Parcel parcel : shipment.getParcels()) {
//            parcel.setLastCheckpoint(checkpoint);
//            var before = Instant.now();
//            checkpointService.addCheckpoint(checkpoint);
//            var duration = Duration.between(before, Instant.now()).toNanos();
//            shipmentEvals.add(duration + " , checkpoint create");
//        }
//        //adds the shipment to terminal
//    }
//
//
//    @Transactional
//    public void updateSecondCheckpointsOnParcels(Shipment shipment) {
//        Checkpoint checkpoint = new Checkpoint(shipment.getFirstTerminal(), Checkpoint.CheckpointType.ReceivedFirstTerminal);
//        for (Parcel parcel : shipment.getParcels()) {
//            parcel.setLastCheckpoint(checkpoint);
//            var before = Instant.now();
//            checkpointService.addCheckpoint(checkpoint);
//            var duration = Duration.between(before, Instant.now()).toNanos();
//            shipmentEvals.add(duration + " , checkpoint create");
//        }
//        //adds the shipment to terminal
//    }
//
//    @Transactional
//    public Shipment updateThirdCheckpointsOnParcels(Shipment shipment) {
//        Checkpoint checkpoint = new Checkpoint(shipment.getFirstTerminal(), Checkpoint.CheckpointType.LoadedOnCar);
//
//        for (Parcel parcel : shipment.getParcels()) {
//            parcel.setLastCheckpoint(checkpoint);
//            var before = Instant.now();
//            checkpointService.addCheckpoint(checkpoint);
//            var duration = Duration.between(before, Instant.now()).toNanos();
//            shipmentEvals.add(duration + " , checkpoint create");
//        }
//        return shipment;
//        //adds the shipment to terminal
//    }
//
//    @Transactional
//    public Shipment updateFourthCheckpointsOnParcels(Shipment shipment) {
//        Checkpoint checkpoint = new Checkpoint(shipment.getFinalTerminal(), Checkpoint.CheckpointType.ReceivedFinalTerminal);
//
//        for (Parcel parcel : shipment.getParcels()) {
//            parcel.setLastCheckpoint(checkpoint);
//            var before = Instant.now();
//            checkpointService.addCheckpoint(checkpoint);
//            var duration = Duration.between(before, Instant.now()).toNanos();
//            shipmentEvals.add(duration + " , checkpoint create");
//        }
//        return shipment;
//        //adds the shipment to terminal
//    }
//
//    @Transactional
//    public Shipment updateSixthCheckpointsOnParcels(Shipment shipment) {
//        Checkpoint checkpoint = new Checkpoint(shipment.getReceiver().getAddress(), Checkpoint.CheckpointType.UnderDelivery);
//
//        for (Parcel parcel : shipment.getParcels()) {
//            parcel.setLastCheckpoint(checkpoint);
//            var before = Instant.now();
//            checkpointService.addCheckpoint(checkpoint);
//            var duration = Duration.between(before, Instant.now()).toNanos();
//            shipmentEvals.add(duration + " , checkpoint create");
//        }
//        return shipment;
//        //adds the shipment to terminal
//    }
//
//    @Transactional
//    public String getShipmentSenderAddress(Shipment shipment) {
//        if (shipmentRepository.findById(shipment.getShipment_id()).isPresent()) {
//            Optional<Customer> customerOpt = customerService.findByID(shipmentRepository.findById(shipment.getShipment_id()).get().getSender().getCustomerID());
//            if (customerOpt.isPresent()) {
//                return customerOpt.get().getAddress();
//            } else {
//                return "Could not find address";
//            }
//        } else {
//            return "Could not find shipment";
//        }
//    }
//
//
//    @Transactional
//    public String getShipmentReceiverAddress(Shipment shipment) {
//        if (shipmentRepository.findById(shipment.getShipment_id()).isPresent()) {
//            Optional<Customer> customerOpt = customerService.findByID(shipmentRepository.findById(shipment.getShipment_id()).get().getReceiver().getCustomerID());
//            if (customerOpt.isPresent()) {
//                return customerOpt.get().getAddress();
//            } else {
//                return "Could not find receiver address";
//            }
//        } else {
//            return "could not find shipment";
//        }
//    }
}
