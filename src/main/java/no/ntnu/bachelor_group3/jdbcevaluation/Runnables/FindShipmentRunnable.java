package no.ntnu.bachelor_group3.jdbcevaluation.Runnables;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class FindShipmentRunnable implements Runnable {

    private Shipment shipment;
    private DatabaseManager db;


    public FindShipmentRunnable(DatabaseManager db, Shipment shipment) {
        this.db = db;
        this.shipment = shipment;
    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void catchRun() throws SQLException {
        if (shipment != null) {
            Long parcelId = db.getShipmentById(shipment.getId()).getParcels().get(0).getId();
            List<Checkpoint> checkpoints = db.getCheckpointsOnParcel(parcelId);
            if (checkpoints.size() > 0) {
                Checkpoint lastCheckpoint = checkpoints.get(checkpoints.size() - 1);
                System.out.println("Status: " + lastCheckpoint.getType().name() + "   Location: " + lastCheckpoint.getTerminal().getLocation());
            } else {
                System.out.println("No checkpoints on this shipment yet");
            }
        }
    }
}
