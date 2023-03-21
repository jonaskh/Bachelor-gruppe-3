package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
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


    public Shipment findByID(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);

        return shipment.orElse(null);
    }
    //saves a shipment to the repository, and thus the database
    @Transactional
    public boolean add(Shipment shipment) {
        boolean success = false;


            shipmentRepository.save(shipment);
            success = true;
        return success;
    }

    //add a random number of parcels to the shipment
    public List<Parcel> addParcels(Long shipment_id) {
        Optional<Shipment> shipmentOpt = shipmentRepository.findById(shipment_id);

        if(shipmentOpt.isPresent()) {
            Random random = new Random();
            int bound = random.nextInt(6) + 1; //generate random number of parcels added, always add 1 to avoid zero values

            for (int i = 0; i < bound; i++) {
                double weight = random.nextDouble(5) + 1;
                Parcel parcel = new Parcel(shipmentOpt.get(), weight);
            }

        return shipmentOpt.get().getParcels();

        } else {
            System.out.println("Not a valid shipment");
            return null;
        }
    }
}
