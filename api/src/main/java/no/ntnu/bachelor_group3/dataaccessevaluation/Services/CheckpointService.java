package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    private static List<String> checkpointEvals = new ArrayList<>();

    public List<String> getCheckpointEvals() {
        return checkpointEvals;
    }

    @jakarta.transaction.Transactional
    public Checkpoint findByID(Long id) {
        var current = Instant.now();
        Checkpoint cp = checkpointRepository.findById(id).orElse(null);
        var duration = Duration.between(current, Instant.now()).toNanos() + ", checkpoint, read";
        checkpointEvals.add(duration);
        return cp;
    }


    public void addCheckpoint(Checkpoint checkpoint) {
        var before = Instant.now();
        checkpointRepository.save(checkpoint);
        var duration = Duration.between(before, Instant.now()).getNano() + ", checkpoint: " + checkpoint.getCheckpoint_id() + ", create";
        checkpointEvals.add(duration);
    }

    @jakarta.transaction.Transactional
    public long count() {
        var before = Instant.now();
        var count = checkpointRepository.count();
        var duration = Duration.between(before, Instant.now()).toNanos() + " , checkpoint read all";
        checkpointEvals.add(duration);
        return count;
    }


}
