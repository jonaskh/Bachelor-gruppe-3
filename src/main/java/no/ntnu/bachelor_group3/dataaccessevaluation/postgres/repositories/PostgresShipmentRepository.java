package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostgresShipmentRepository extends CrudRepository<Shipment, Long> {

    Optional<Shipment> findById(Long Long);
}