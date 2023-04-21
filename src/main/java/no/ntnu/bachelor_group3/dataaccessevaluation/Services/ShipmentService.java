package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

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
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class ShipmentService{

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

    private static List<String> shipmentEvals = new CopyOnWriteArrayList<>();

    public ShipmentService() {

    }

    public List<String> getShipmentEvals() {
        return shipmentEvals;
    }

    public void printParcelsFromDB(Shipment shipment) {
        if (findByID(shipment.getShipment_id())!= null) {
            for (Parcel parcel : findByID(shipment.getShipment_id()).getParcels()) {
                System.out.println(parcel.getParcel_id());
            }
        }
    }


    public Shipment findByID(Long id) {
        var before = Instant.now();
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        var duration = Duration.between(before,Instant.now());
        shipmentEvals.add(duration.get(ChronoUnit.NANOS) + " shipment find");
        return shipment.orElse(null);
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

    public long returnNrOfParcels(Shipment ship) {
        return ship.getParcels().size();
    }

    public void saveParcelsToDatabaseFromShipment(Shipment shipment) {
        parcelService.saveAll(findByID(shipment.getShipment_id()).getParcels());
    }


    //for testing
    public void printShipmentInfo(Shipment shipment) {
        if (shipmentRepository.findById(shipment.getShipment_id()).isPresent()) {
            if (customerService.findByID(shipment.getSenderID()).isPresent()) {
                System.out.println("Shipment "+ shipment.getShipment_id() + " has sender as customer: " + findByID(shipment.getShipment_id()).getSenderID());
            } else {
                System.out.println("Shipment " + shipment.getShipment_id() + " has no sender in database");
            }
            if (customerService.findByID(shipment.getReceiverID()).isPresent()) {
                System.out.println("Shipment "+ shipment.getShipment_id() + " has receiver as customer: " + findByID(shipment.getShipment_id()).getReceiverID());
            } else {
                System.out.println("Shipment " + shipment.getShipment_id() + " has no receiver in database");
            }

        }
    }

    /**
     * returns void to use in runnable interface
     * @param shipment
     */
    //TODO: CASCADING SAVE CHILD ENTITIES VS MANUAL
    @Transactional
    public void cascadingAdd(Shipment shipment){
        var before = Instant.now();
        try {
            shipmentRepository.save(shipment);
        } catch (HibernateException e) {
            System.out.println("Shipment with that ID already exists");
        }
        var duration = Duration.between(before, Instant.now());
        shipmentEvals.add(duration.get(ChronoUnit.NANOS) + " shipment create");
    }

    /**
     * returns a string containing the time used by the save method to use in a callable
     * @param shipment
     * @return time taken by save method and type of CRUD operation
     */
    @Transactional
    public void cascadingAddCallable(Shipment shipment) {
        var before = Instant.now();
        shipmentRepository.save(shipment);
        var duration = Duration.between(before, Instant.now());
        shipmentEvals.add(duration.get(ChronoUnit.NANOS) + ", shipment create");
    }

    /**
     * Adds a checkpoint to all parcels in the shipment, with a 2s delay to simulate travel time
     * @param shipment to add checkpoint to
     * @param checkpoint which checkpoint to add
     */
    public void updateCheckpointsOnParcels(Shipment shipment, Checkpoint checkpoint) {
        if (!findByID(shipment.getShipment_id()).getParcels().isEmpty()) {
            for (Parcel parcel : shipment.getParcels()) {
                parcel.setLastCheckpoint(checkpoint);
//                checkpointService.addCheckpoint(checkpoint);
            }
            System.out.println("Successfully added checkpoint" + checkpointService.findByID(checkpoint.getCheckpoint_id()).getType() + " to all parcels in shipment" + findByID(shipment.getShipment_id()));
        } else {
            System.out.println("No parcels in shipment, couldn't update checkpoint");
        }

        try {
            System.out.println("Shipment is now being transported to next checkpoint...");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public String getShipmentSenderAddress(Shipment shipment) {
        if (shipmentRepository.findById(shipment.getShipment_id()).isPresent()) {
            Optional<Customer> customerOpt = customerService.findByID(shipmentRepository.findById(shipment.getShipment_id()).get().getSender().getCustomerID());
            if (customerOpt.isPresent()) {
                return customerOpt.get().getAddress();
            } else {
                return "Could not find address";
            }
        } else {
            return "Could not find shipment";
        }
    }


    public String getShipmentReceiverAddress(Shipment shipment) {
        if (shipmentRepository.findById(shipment.getShipment_id()).isPresent()) {
            Optional<Customer> customerOpt = customerService.findByID(shipmentRepository.findById(shipment.getShipment_id()).get().getReceiver().getCustomerID());
            if (customerOpt.isPresent()) {
                return customerOpt.get().getAddress();
            } else {
                return "Could not find receiver address";
            }
        } else {
            return "could not find shipment";
        }
    }

    //returns the terminal connected to zip code of the shipments sender

    public Terminal findFirstTerminalToShipment(Shipment shipment) {
        return terminalService.returnTerminalFromZip(customerService.findByID(shipmentRepository.findById(shipment.getShipment_id()).get().getSenderID()).get().getZip_code());
    }

    //returns the terminal connected to zip code of the shipments receiver
    public Terminal  findFinalTerminalToShipment(Shipment shipment) {

        return terminalService.returnTerminalFromZip(customerService.findByID(shipmentRepository.findById(shipment.getShipment_id()).get().getReceiverID()).get().getZip_code());
    }

    public long count() {
        var before = Instant.now();
        var count = shipmentRepository.count();
        var duration = Duration.between(before, Instant.now());
        var result = duration + ", shipment read all";

        shipmentEvals.add(result);
        return count;
    }
}
