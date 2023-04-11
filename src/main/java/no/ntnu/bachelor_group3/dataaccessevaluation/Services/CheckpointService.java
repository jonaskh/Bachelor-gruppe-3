package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    private CustomerService

    public Optional<Checkpoint> getCurrent() {
        return checkpointRepository.findLastEntryInCheckpoint();
    }

    public Checkpoint findByID(Long id) {
        return checkpointRepository.findById(id).orElse(null);
    }

    public boolean addCheckpoint(Checkpoint checkpoint) {
        Optional<Checkpoint> existingCheckpoint = checkpointRepository.findById(checkpoint.getCheckpoint_id());
        if (existingCheckpoint.isPresent()) {
            return false;
        } else {
            checkpointRepository.save(checkpoint);
            return true;
        }
    }
}
