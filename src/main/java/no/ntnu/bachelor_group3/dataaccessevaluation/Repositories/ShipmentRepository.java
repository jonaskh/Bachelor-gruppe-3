package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;

public interface ShipmentRepository extends CrudRepository<Shipment, Long> {

    @Override
    Optional<Shipment> findById(Long id);


    @Override
    <S extends Shipment> S save(S entity);

    @Override
    long count();

    @Override
    <S extends Shipment> Iterable<S> saveAll(Iterable<S> entities);
}
