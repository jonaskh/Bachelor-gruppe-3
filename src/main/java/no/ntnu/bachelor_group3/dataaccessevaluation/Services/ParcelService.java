package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ParcelService {

    @Autowired
    CheckpointService checkpointService;

    @Autowired
    ParcelRepository parcelRepository;

    private static List<String> parcelEval = new ArrayList<>();

    public List<String> getParcelEvals() {
        return parcelEval;
    }




    public long findAllParcels() {

        var before = Instant.now();
        var count = parcelRepository.count();
        var duration = Duration.between(before, Instant.now()).toMillis();
        parcelEval.add(duration + " , parcel find all");
        return parcelRepository.count();
    }

    //Updates the current checkpoint and adds it to total checkpoint list.
    public Checkpoint addCheckpointToParcel(Checkpoint checkpoint, Parcel parcel) {
        parcel.setLastCheckpoint(checkpoint);
        return parcel.getLastCheckpoint();
    }

    @Transactional
    public void save(Parcel parcel) {
        if (parcelRepository.findById(parcel.getParcel_id()).isEmpty()) {
            var before = Instant.now();
            parcelRepository.save(parcel);
            var duration = Duration.between(before, Instant.now()).toMillis();
            parcelEval.add(duration + ", parcel create");
            System.out.println("Parcel has been added to database");
        }
    }

    //TODO: Evaluate
    @Transactional
    public void saveAll(List<Parcel> parcels) {
        var before = Instant.now();
        parcelRepository.saveAll(parcels);
        var duration = Duration.between(before, Instant.now());
        parcelEval.add(duration.get(ChronoUnit.NANOS) + ", customer create all");
        parcels.forEach((p) -> {
            System.out.println(p + " has been successfully added to the database");
        });
    }

    @Transactional
    public long count() {
        var before = Instant.now();
        var count = parcelRepository.count();
        var duration = Duration.between(before, Instant.now());
        parcelEval.add(duration.get(ChronoUnit.NANOS) + " , parcel read all");
        return count;
    }

    public Optional<Parcel> findByID(Long id) {
        return parcelRepository.findById(id);
    }

    @Transactional
    public List<Parcel> findAll() {
        List<Parcel> parcels = new ArrayList<>();
        parcelRepository.findAll().forEach(parcels::add);
        return parcels;
    }

    //for testing
    public void printParcelInfo(Parcel parcel) {
        parcel.toString();
    }
}

