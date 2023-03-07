package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    public Optional<Checkpoint> getCurrent() {

        return checkpointRepository.findlastCheckpoint();
    }
}
