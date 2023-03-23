package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //Updates the current checkpoint and adds it to total checkpoint list.
    public Checkpoint addCheckpointToParcel(Checkpoint checkpoint, Parcel parcel) {
        parcel.setLastCheckpoint(checkpoint);
        return parcel.getLastCheckpoint();
    }

    public boolean save(Parcel parcel) {
        if (parcelRepository.findById(parcel.getParcel_id()).isPresent()) {
            return false;
        } else {
            parcelRepository.save(parcel);
            return true;
        }
    }

}
