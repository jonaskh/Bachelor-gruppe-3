package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostgresParcelService {

    @Autowired
    PostgresCheckpointService postgresCheckpointService;


    private Checkpoint getCurrentCheckpoint() {
        Optional<Checkpoint> current = postgresCheckpointService.getCurrent();
        return current.orElse(null);
    }

}
