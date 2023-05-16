package no.ntnu.bachelor_group3.jdbcevaluation.Runnables;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;

public class UpdateShipmentRunnable implements Runnable{

    private Shipment shipment;
    private DatabaseManager db;

    private Checkpoint cp1;
    private Checkpoint cp2;
    private Checkpoint cp3;
    private Checkpoint cp4;
    private Checkpoint cp5;
    private Checkpoint cp6;


    public UpdateShipmentRunnable(Shipment shipment, DatabaseManager db) {
        this.shipment = shipment;
        this.db = db;
        this.cp1 = new Checkpoint(shipment.getStartTerminal(), Checkpoint.CheckpointType.Collected);
        this.cp2 = new Checkpoint(shipment.getStartTerminal(), Checkpoint.CheckpointType.ReceivedFirstTerminal);
        this.cp3 = new Checkpoint(shipment.getStartTerminal(), Checkpoint.CheckpointType.LoadedOnCar);
        this.cp4 = new Checkpoint(shipment.getEndTerminal(), Checkpoint.CheckpointType.ReceivedFinalTerminal);
        this.cp5 = new Checkpoint(shipment.getEndTerminal(), Checkpoint.CheckpointType.LoadedOnCar);
        this.cp6 = new Checkpoint(shipment.getEndTerminal(), Checkpoint.CheckpointType.UnderDelivery);
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    //this method runs another in order to catch exceptions, else they are ignored.
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Simulates the lifecycle of a shipment, where it simulates traveling through different checkpoints
     * to the final terminal. The time is relative to real time.
     */
    public void catchRun() throws SQLException {
        db.setCheckpointOnParcels(shipment, cp1);
        db.setCheckpointOnParcels(shipment, cp2);
        db.setCheckpointOnParcels(shipment, cp3);
        db.setCheckpointOnParcels(shipment, cp4);
        db.setCheckpointOnParcels(shipment, cp5);
        db.setCheckpointOnParcels(shipment, cp6);
    }
}
