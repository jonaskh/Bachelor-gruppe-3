package no.ntnu.bachelor_group3.dataaccessevaluation.informix.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InformixParcelService {

    @Autowired
    InformixCheckpointService informixCheckpointService;


    private Checkpoint getCurrentCheckpoint() {
        Optional<Checkpoint> current = informixCheckpointService.getCurrent();
        return current.orElse(null);
    }

}
