package no.ntnu.bachelor_group3.JDBCEvaluation.data;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parcel {
    private int id;
    private Shipment shipment;
    private double weight;

    // Constructor
    public Parcel(int id, double weight) {
        this.id = id;
        this.weight = weight;
    }

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Instance method to save this parcel to the database
    public void save(Connection conn) throws SQLException {
        PreparedStatement stmt;
        if (id == 0) {
            // This is a new parcel, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO Parcel (shipment_id, weight) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, shipment.getId());
            stmt.setDouble(2, weight);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } else {
            // This is an existing parcel, so update it in the database
            stmt = conn.prepareStatement("UPDATE Parcel SET shipment_id = ?, weight = ? WHERE id = ?");
            stmt.setInt(1, shipment.getId());
            stmt.setDouble(2, weight);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    // Instance method to delete this parcel from the database
    public void delete(Connection conn) throws SQLException {
        if (id > 0) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Parcel WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            id = 0;
        }
    }

    // Static method to retrieve a parcel from the database given an ID
    public static Parcel getParcelById(int parcelId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Parcel WHERE id = ?");
        stmt.setInt(1, parcelId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Shipment shipment = Shipment.getShipmentById(rs.getInt("shipment_id"), conn);
            Parcel parcel = new Parcel(rs.getInt("id"), rs.getDouble("weight"));
            parcel.setShipment(shipment);
            return parcel;
        }
        return null;
    }
}

