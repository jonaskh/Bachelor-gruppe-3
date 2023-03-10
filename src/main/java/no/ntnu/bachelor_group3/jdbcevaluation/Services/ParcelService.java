package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParcelService {
    public ParcelService() {}

    public Parcel getParcelById(Long parcelId, CustomerService customerService,
                                       ShipmentService shipmentService, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Parcel WHERE parcel_id = ?");
        stmt.setLong(1, parcelId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Shipment shipment = shipmentService.getShipmentById(rs.getLong("shipment_id"), customerService, conn);
            Parcel parcel = new Parcel(rs.getLong("parcel_id"), rs.getDouble("weight"));
            parcel.setShipment(shipment);
            return parcel;
        }
        return null;
    }

    public Long save(Parcel parcel, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long parcel_id = parcel.getId();
        Shipment shipment = parcel.getShipment();
        Double weight = parcel.getWeight();
        int weight_class = parcel.getWeight_class();
        if (parcel_id == 0) {
            // This is a new parcel, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO Parcel (shipment_id, weight, weight_class) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, shipment.getId());
            stmt.setDouble(2, weight);
            stmt.setInt(3, weight_class);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } else {
            // This is an existing parcel, so update it in the database
            stmt = conn.prepareStatement("UPDATE Parcel SET shipment_id = ?, weight = ?, weight_class = ? WHERE parcel_id = ?");
            stmt.setLong(1, shipment.getId());
            stmt.setDouble(2, weight);
            stmt.setInt(3, weight_class);
            stmt.setLong(4, parcel_id);
            stmt.executeUpdate();
        }
        return null;
    }

    public void delete(Parcel parcel, Connection conn) throws SQLException {
        Long id = parcel.getId();
        if (id > 0) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Parcel WHERE parcel_id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }
}
