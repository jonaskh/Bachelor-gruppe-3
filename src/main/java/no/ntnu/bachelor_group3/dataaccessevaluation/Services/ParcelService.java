package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {

    @Autowired
    CheckpointService checkpointService;

    @Autowired
    ParcelRepository parcelRepository;


    //returns the last checkpoint registered for tracking. This is the only way a customer can track the parcel.
    private Checkpoint getCurrentCheckpoint() {
        Optional<Checkpoint> current = checkpointService.getCurrent();
        return current.orElse(null);
    }

    public long findAllParcels() {
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
            parcelRepository.save(parcel);
            System.out.println("Parcel has been added to database");
        }
    }

    //TODO: Evaluate
    @Transactional
    public void saveAll(List<Parcel> parcels) {
        parcelRepository.saveAll(parcels);
        parcels.forEach((p) -> {
            System.out.println(p + " has been successfully added to the database");
        });
    }

    @Transactional
    public long count() {
        return parcelRepository.count();
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

