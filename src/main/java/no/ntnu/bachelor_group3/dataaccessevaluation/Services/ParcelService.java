package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParcelService {

    @Autowired
    CheckpointService checkpointService;


    private Checkpoint getCurrentCheckpoint() {
        Optional<Checkpoint> current = checkpointService.getCurrent();
        return current.orElse(null);
    }

}
