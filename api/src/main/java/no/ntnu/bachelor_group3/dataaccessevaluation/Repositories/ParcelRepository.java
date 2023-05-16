package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParcelRepository extends CrudRepository<Parcel, Long> {

    Optional<Parcel> findById(Long id);

    @Override
    <S extends Parcel> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    long count();

    @Override
    <S extends Parcel> S save(S entity);
}
