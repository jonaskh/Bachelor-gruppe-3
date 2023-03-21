package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories.PostgresCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostgresCheckpointService {

    @Autowired
    private PostgresCheckpointRepository checkpointRepository;

    public Optional<Checkpoint> getCurrent() {

        return checkpointRepository.findLastEntryInCheckpoint();
    }
}
