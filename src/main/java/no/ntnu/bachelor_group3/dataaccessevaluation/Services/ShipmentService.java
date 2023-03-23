package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

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


    public Shipment findByID(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);

        return shipment.orElse(null);
    }
    //saves a shipment to the repository, and thus the database
    //TODO: Check if customers exist in db?
    public boolean add(Shipment shipment) {
        boolean success = false;
        if (shipmentRepository.findById(shipment.getShipment_id()).isEmpty()) {
            shipmentRepository.save(shipment);
            return true;
        } else {
            return false;
        }
    }

    //add a random number of parcels to the shipment
    //TODO: Right now it crashes if you add more than 2 parcels.
    public List<Parcel> addParcels(Long shipment_id) {
        Optional<Shipment> shipmentOpt = shipmentRepository.findById(shipment_id);

        if(shipmentOpt.isPresent()) {
            Random random = new Random();
            int bound = random.nextInt(5) + 1; //generate random number of parcels added, always add 1 to avoid zero values

            for (int i = 0; i < 8; i++) {
                double weight = random.nextDouble(5) + 1;
                Parcel parcel = new Parcel(shipmentOpt.get(), weight);
                shipmentOpt.get().addParcel(parcel);
                parcelService.save(parcel);

            }

        return shipmentOpt.get().getParcels();

        } else {
            System.out.println("Not a valid shipment");
            return null;
        }
    }

    public String printShipmentInfo(Shipment shipment) {
        if (!shipmentRepository.existsById(shipment.getShipment_id())) {

            return shipmentRepository.findById(shipment.getShipment_id()).get().toString();

        } else {
            return "No shipment found";
        }
    }

    //TODO: Update checkpoint method


    //TODO:Terminals

    //returns the terminal that matches the given zip code
    public void findNearestTerminalByZip(String zip) {
    }
}
