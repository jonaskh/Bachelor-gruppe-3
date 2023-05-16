package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckpointRepository extends CrudRepository<Checkpoint, Long> {

    //SQL query to select last checkpoint
    @Query(value = "SELECT * FROM checkpoint ORDER BY checkpoint_id DESC LIMIT 1", nativeQuery = true)
    Optional<Checkpoint> findLastEntryInCheckpoint();

    //further methods excluded

//    long countCheckpointByparcel_id(Long id);

    @Query(value = "select count(checkpoint) where parcel_id = ?", nativeQuery = true)
    long checkpointsInParcel();


    Optional<Checkpoint> findById(Long id);

    @Override
    <S extends Checkpoint> S save(S entity);


    long findAllByLocation(String loc);


    @Override
    <S extends Checkpoint> Iterable<S> saveAll(Iterable<S> entities);
}
