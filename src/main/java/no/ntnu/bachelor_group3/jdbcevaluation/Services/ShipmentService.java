package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ShipmentService {

    private static final String GET_SHIPMENT_BY_ID_QUERY = "SELECT * FROM Shipment WHERE shipment_id = ?";
    private static final String GET_PARCELS_IN_SHIPMENT_BY_ID_QUERY = "SELECT * FROM Parcel WHERE shipment_id = ?";
    private static final String INSERT_SHIPMENT_QUERY = "INSERT INTO Shipment (customer_id, receiving_address, receiving_zip," +
            " receiver_name, payer_id, delivered) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SHIPMENT_QUERY = "UPDATE Shipment SET sender_id = ?, receiving_address = ?," +
            " receiving_zip = ?, receiver_name = ? payer_id = ? WHERE id = ?";
    private static final String DELETE_SHIPMENT_QUERY = "DELETE FROM Shipment WHERE id = ?";

    public ShipmentService() {}

    public Shipment getShipmentById(Long shipmentId, CustomerService customerService, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(GET_SHIPMENT_BY_ID_QUERY);
        stmt.setLong(1, shipmentId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Customer sender = customerService.getCustomerById(rs.getInt("customer_id"), conn);
            String address = rs.getString("receiving_address");
            String zip = rs.getString("receiving_zip");
            String name = rs.getString("receiver_name");
            Customer payer = customerService.getCustomerById(rs.getInt("payer_id"), conn);
            Shipment shipment = new Shipment(rs.getLong("shipment_id"), sender, address, zip, name,
                    payer, 0);
            // Retrieve all parcels associated with this shipment
            stmt = conn.prepareStatement(GET_PARCELS_IN_SHIPMENT_BY_ID_QUERY);
            stmt.setLong(1, shipmentId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel(rs.getLong("parcel_id"), rs.getDouble("weight"));
                shipment.getParcels().add(parcel);
            }
            return shipment;
        }
        return null;
    }

    public void save(Shipment shipment, ParcelService parcelService, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = shipment.getId();
        List<Parcel> parcels = shipment.getParcels();
        if (id == 0) {
            // This is a new shipment, so insert it into the database
            stmt = conn.prepareStatement(INSERT_SHIPMENT_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            setShipmentInfo(shipment, stmt);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(4);
                }
            }
            // Save all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcel.setShipment(shipment);
                parcelService.save(parcel, conn);
            }
        } else {
            // This is an existing shipment, so update it in the database
            stmt = conn.prepareStatement(UPDATE_SHIPMENT_QUERY);
            setShipmentInfo(shipment, stmt);
            stmt.setLong(7, id);
            stmt.executeUpdate();
            // Update all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcelService.save(parcel, conn);
            }
        }
    }

    public void delete(Shipment shipment, ParcelService parcelService, Connection conn) throws SQLException {
        Long id = shipment.getId();
        List<Parcel> parcels = shipment.getParcels();
        if (id > 0) {
            // Delete all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcelService.delete(parcel, conn);
            }
            // Delete the shipment itself
            PreparedStatement stmt = conn.prepareStatement(DELETE_SHIPMENT_QUERY);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }

    private void setShipmentInfo(Shipment shipment, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1, shipment.getSender().getId());
        stmt.setString(2, shipment.getReceiving_address());
        stmt.setString(3, shipment.getReceiving_zip());
        stmt.setString(4, shipment.getReceiver_name());
        stmt.setLong(5, shipment.getPayer().getId());
        stmt.setBoolean(6, shipment.isDelivered());
    }
}
