package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO {

    private static final String GET_SHIPMENT_BY_ID_QUERY = "SELECT * FROM Shipment WHERE shipment_id = ?";
    private static final String GET_PARCELS_IN_SHIPMENT_BY_ID_QUERY = "SELECT * FROM Parcel WHERE shipment_id = ?";
    private static final String INSERT_SHIPMENT_QUERY = "INSERT INTO Shipment (sender_id, receiver_id, payer_id, delivered, start_terminal_id, end_terminal_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SHIPMENT_QUERY = "UPDATE Shipment SET sender_id = ?, receiver_id = ?, payer_id = ?, delivered = ?, start_terminal_id = ?, end_terminal_id = ? WHERE shipment_id = ?";
    private static final String DELETE_SHIPMENT_QUERY = "DELETE FROM Shipment WHERE id = ?";

    public static List<String> executionTimeList;

    public ShipmentDAO() {
        if (executionTimeList == null) {
            executionTimeList = new ArrayList<>();
        }
    }

    public Shipment getShipmentById(Long shipmentId, CustomerDAO customerDAO, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(GET_SHIPMENT_BY_ID_QUERY);
        stmt.setLong(1, shipmentId);

        var startTime = Instant.now();
        ResultSet rs = stmt.executeQuery();
        var executionTime = Duration.between(startTime, Instant.now()).toNanos();
        executionTimeList.add(executionTime + ", shipment, read");

        if (rs.next()) {
            Customer sender = customerDAO.getCustomerById(rs.getLong("sender_id"), conn);
            Customer receiver = customerDAO.getCustomerById(rs.getLong("receiver_id"), conn);
            Customer payer = customerDAO.getCustomerById(rs.getLong("payer_id"), conn);
            Shipment shipment = new Shipment(rs.getLong("shipment_id"), sender, receiver,
                    payer, 0);
            // Retrieve all parcels associated with this shipment
            stmt = conn.prepareStatement(GET_PARCELS_IN_SHIPMENT_BY_ID_QUERY);
            stmt.setLong(1, shipmentId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel(rs.getLong("parcel_id"), rs.getFloat("weight"));
                shipment.getParcels().add(parcel);
            }
            return shipment;
        }
        return null;
    }

    public Long save(Shipment shipment, ParcelDAO parcelDAO, TerminalDAO terminalDAO, Connection conn) throws SQLException {
        setFirstTerminal(shipment, terminalDAO, conn);
        setLastTerminal(shipment, terminalDAO, conn);
        PreparedStatement stmt;
        Long id = shipment.getId();
        List<Parcel> parcels = shipment.getParcels();
        if (id == 0) {
            // This is a new shipment, so insert it into the database
            stmt = conn.prepareStatement(INSERT_SHIPMENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            setShipmentInfo(shipment, stmt);
            var startTime = Instant.now();
            int rowsInserted = stmt.executeUpdate();
            var executionTime = Duration.between(startTime, Instant.now()).toNanos();
            executionTimeList.add(executionTime + ", shipment, create");
            if (rowsInserted > 0) {
                // Run a separate query to get the last inserted ID
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new SQLException("Failed to insert shipment, no rows affected.");
            }
        } else {
            // This is an existing shipment, so update it in the database
            stmt = conn.prepareStatement(UPDATE_SHIPMENT_QUERY);
            setShipmentInfo(shipment, stmt);
            stmt.setLong(7, id);
            var startTime = Instant.now();
            stmt.executeUpdate();
            var executionTime = Duration.between(startTime, Instant.now()).toNanos();
            executionTimeList.add(executionTime + ", shipment, update");
            // Update all parcels associated with this shipment
        }
        return id;
    }



    public void delete(Shipment shipment, ParcelDAO parcelDAO, Connection conn) throws SQLException {
        Long id = shipment.getId();
        List<Parcel> parcels = shipment.getParcels();
        if (id > 0) {
            // Delete all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcelDAO.delete(parcel, conn);
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
        stmt.setLong(2, shipment.getReceiver().getId());
        stmt.setLong(3, shipment.getPayer().getId());
        stmt.setBoolean(4, shipment.isDelivered());
        stmt.setLong(5, shipment.getStartTerminal().getId());
        stmt.setLong(6, shipment.getEndTerminal().getId());
    }

    public void setFirstTerminal(Shipment shipment, TerminalDAO terminalDAO, Connection conn) {
        String senderZipCode = shipment.getSender().getZipCode();
        Terminal terminal = terminalDAO.getTerminalByZip(senderZipCode, conn);
        shipment.setStartTerminal(terminal);
    }

    public void setLastTerminal(Shipment shipment, TerminalDAO terminalDAO, Connection conn) {
        String receiverZipCode = shipment.getReceiver().getZipCode();
        Terminal terminal = terminalDAO.getTerminalByZip(receiverZipCode, conn);
        shipment.setEndTerminal(terminal);
    }

    public List<String> getExecutionTimeList() {
        return executionTimeList;
    }
}
