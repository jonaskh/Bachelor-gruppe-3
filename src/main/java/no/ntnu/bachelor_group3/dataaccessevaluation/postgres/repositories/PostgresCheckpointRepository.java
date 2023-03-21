package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresCheckpointRepository extends CrudRepository<Checkpoint, Long> {

    //SQL query to select last checkpoint
    @Query(value = "SELECT * FROM checkpoint ORDER BY checkpoint_id DESC LIMIT 1", nativeQuery = true)
    Optional<Checkpoint> findLastEntryInCheckpoint();

    @Override
    Iterable<Checkpoint> findAll();
}
