package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;


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

    public ShipmentService() {

    }

    public void printParcelsFromDB(Shipment shipment) {
        if (findByID(shipment.getShipment_id())!= null) {
            for (Parcel parcel : findByID(shipment.getShipment_id()).getParcels()) {
                System.out.println(parcel.getParcel_id());
            }
        }
    }


    public Shipment findByID(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);

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

    //TODO: CASCADING SAVE CHILD ENTITIES VS MANUAL
    @Transactional
    public String concurrentAdd(Shipment shipment) {
        var current = System.currentTimeMillis();
        shipmentRepository.save(shipment);
        return System.currentTimeMillis() - current + ", saveShipmentCascading";
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
                checkpointService.addCheckpoint(checkpoint);
                System.out.println("Successfully updated checkpoint on parcel " + parcelService.findByID(
                        parcel.getParcel_id()) + " to " + checkpoint);
            }
        } else {
            System.out.println("No parcels in shipment, couldn't update checkpoint");
        }


        try {
            System.out.println("Shipment is now being transported to next checkpoint...");
            Thread.sleep(2000);
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
        return shipmentRepository.count();
    }
}
