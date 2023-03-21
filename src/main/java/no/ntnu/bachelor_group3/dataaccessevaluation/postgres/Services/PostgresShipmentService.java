package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories.PostgresShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PostgresShipmentService {

    @Autowired
    private PostgresShipmentRepository shipmentRepository;


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