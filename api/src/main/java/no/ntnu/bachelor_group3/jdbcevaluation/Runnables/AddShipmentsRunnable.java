package no.ntnu.bachelor_group3.jdbcevaluation.Runnables;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddShipmentsRunnable implements Runnable {
    private Shipment shipment;

    private DatabaseManager db;

    public AddShipmentsRunnable(Shipment shipment, DatabaseManager db) {
        this.shipment = shipment;
        this.db = db;
    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            System.out.println("Integrity constraint: " + e);
            e.printStackTrace();

        }
    }

    public void catchRun() throws SQLException {
        Parcel parcel = new Parcel(0L, 1.2f);
        Long shipmentId = db.saveShipment(shipment);
        Long parcelId = db.saveParcel(parcel, shipmentId);
        parcel.setId(parcelId);
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(parcel);
        shipment.setId(shipmentId);
        shipment.setParcels(parcels);
    }
}
