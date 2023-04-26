package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;


    public Optional<Checkpoint> getCurrent() {
        return checkpointRepository.findLastEntryInCheckpoint();
    }

    public Checkpoint findByID(Long id) {
        return checkpointRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean addCheckpoint(Checkpoint checkpoint) {
        Optional<Checkpoint> existingCheckpoint = checkpointRepository.findById(checkpoint.getCheckpoint_id());
        if (existingCheckpoint.isPresent()) {
            return false;
        } else {
            checkpointRepository.save(checkpoint);
            System.out.println("Checkpoint has been saved to database");
            return true;
        }
    }
}
