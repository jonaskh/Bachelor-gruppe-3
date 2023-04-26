package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckpointRepository extends CrudRepository<Checkpoint, Long> {

    //SQL query to select last checkpoint
    @Query(value = "SELECT * FROM checkpoint ORDER BY checkpoint_id DESC LIMIT 1", nativeQuery = true)
    Optional<Checkpoint> findLastEntryInCheckpoint();

    Optional<Checkpoint> findById(Long id);

    @Transactional
    @Override
    <S extends Checkpoint> S save(S entity);


    @Override
    <S extends Checkpoint> Iterable<S> saveAll(Iterable<S> entities);
}
