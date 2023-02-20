package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShipmentRepository extends CrudRepository<Shipment, String> {

    @Override
    Optional<Shipment> findById(String s);
}
