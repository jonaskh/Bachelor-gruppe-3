package no.ntnu.bachelor_group3.dataaccessevaluation.informix.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories.InformixShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class InformixShipmentService {

    @Autowired
    private InformixShipmentRepository shipmentRepository;


    public Shipment findByID(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);

        return shipment.orElse(null);
    }
    //saves a shipment to the repository, and thus the database
    @Transactional
    public boolean add(Shipment shipment) {
        boolean success = false;

        Shipment ifAlreadyExists = findByID(shipment.getShipment_id());
        if (ifAlreadyExists == null) {
            shipmentRepository.save(shipment);
            success = true;
        }
        return success;
    }
}
