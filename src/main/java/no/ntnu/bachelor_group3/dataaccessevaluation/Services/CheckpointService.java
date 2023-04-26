package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    private static List<String> checkpointEvals = new CopyOnWriteArrayList<>();

    public List<String> getCheckpointEvals() {
        return checkpointEvals;
    }

    @jakarta.transaction.Transactional
    public Checkpoint findByID(Long id) {
        var current = Instant.now();
        Checkpoint cp = checkpointRepository.findById(id).orElse(null);
        var duration = Duration.between(current, Instant.now()).toNanos() + " , checkpoint read";
        checkpointEvals.add(duration);
        return cp;
    }

    @Transactional
    public void addCheckpoint(Checkpoint checkpoint) {
        var before = Instant.now();
        checkpointRepository.save(checkpoint);
        var duration = Duration.between(before, Instant.now()).getNano() + " , checkpoint create";
        checkpointEvals.add(duration);
        System.out.println("Checkpoint has been saved to database");
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
