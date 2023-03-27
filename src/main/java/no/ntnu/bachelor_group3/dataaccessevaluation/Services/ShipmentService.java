package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private CheckpointService checkpointService;


    public Shipment findByID(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);

        return shipment.orElse(null);
    }
    //saves a shipment to the repository, and thus the database
    public Shipment add(Shipment shipment) {
        if (shipmentRepository.findById(shipment.getShipment_id()).isEmpty()) {
            addParcels(shipment);
            shipmentRepository.save(shipment);
        }
        return shipment;
    }

    //add a random number of parcels to the shipment
    //TODO: Right now it crashes if you add more than 2 parcels.
    public Shipment addParcels(Shipment shipment) {

            Random random = new Random();
            int bound = random.nextInt(5) + 1; //generate random number of parcels added, always add 1 to avoid zero values

            System.out.println("Adding " + bound + " parcels to the shipment");
            for (int i = 0; i < bound; i++) {
                double weight = random.nextDouble(5) + 1;
                Parcel parcel = new Parcel(weight);
                shipment.addParcel(parcel);
                parcelService.save(parcel);
            }

            System.out.println("Added " + bound + " parcels to shipment");
            return shipment;
        }


    //for testing
    public String printShipmentInfo(Shipment shipment) {
        if (!shipmentRepository.findById(shipment.getShipment_id()).isPresent()) {

            return shipmentRepository.findById(shipment.getShipment_id()).get().toString();

        } else {
            return "No shipment found";
        }
    }

    public void updateCheckpointsOnParcels(Shipment shipment, Checkpoint checkpoint) {
        if (!shipment.getParcels().isEmpty()) {
            for (Parcel parcel : shipment.getParcels()) {
                parcel.setLastCheckpoint(checkpoint);
                checkpointService.addCheckpoint(checkpoint);
            }
        } else {
            System.out.println("No parcels in shipment");
        }
    }

    //TODO: Update checkpoint method


    //TODO:Terminals

    //returns the terminal that matches the given zip code
    public void findNearestTerminalByZip(String zip) {
    }
}
