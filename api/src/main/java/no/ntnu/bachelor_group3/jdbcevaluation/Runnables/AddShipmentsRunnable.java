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

    /**
     * Adds a shipment and two parcels connected to that shipment in the database.
     *
     * @throws SQLException
     */
    public void catchRun() throws SQLException {
        Parcel parcel = new Parcel(0L, 1.2);
        Parcel parcel2 = new Parcel(0L, 2.1);
        Long shipmentId = db.saveShipment(shipment);
        Long parcelId = db.saveParcel(parcel, shipmentId);
        Long parcelId2 = db.saveParcel(parcel2, shipmentId);
        parcel.setId(parcelId);
        parcel2.setId(parcelId2);
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(parcel);
        parcels.add(parcel2);
        shipment.setId(shipmentId);
        shipment.setParcels(parcels);
    }
}
