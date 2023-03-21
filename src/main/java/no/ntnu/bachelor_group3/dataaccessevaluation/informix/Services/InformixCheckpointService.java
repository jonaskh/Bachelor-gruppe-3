package no.ntnu.bachelor_group3.dataaccessevaluation.informix.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories.InformixCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InformixCheckpointService {

    @Autowired
    private InformixCheckpointRepository informixCheckpointRepository;

    public Optional<Checkpoint> getCurrent() {

        return informixCheckpointRepository.findLastEntryInCheckpoint();
    }
}
