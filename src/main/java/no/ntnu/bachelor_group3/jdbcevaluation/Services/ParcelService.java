package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParcelService {

    private static final String GET_PARCEL_BY_ID_QUERY = "SELECT * FROM Parcel WHERE parcel_id = ?";
    private static final String INSERT_PARCEL_QUERY = "INSERT INTO Parcel (shipment_id, weight, weight_class) VALUES (?, ?, ?)";
    private static final String UPDATE_PARCEL_QUERY = "UPDATE Parcel SET shipment_id = ?, weight = ?, weight_class = ? WHERE parcel_id = ?";
    private static final String DELETE_PARCEL_QUERY = "DELETE FROM Parcel WHERE parcel_id = ?";

    public ParcelService() {}

    /**
     * Returns a parcel from the parcel table if it exists
     *
     * @param parcelId the id of the parcel to find
     * @param customerService
     * @param shipmentService
     * @param conn the connection to the database
     * @return a parcel or null if it does not exist
     * @throws SQLException
     */
    public Parcel getParcelById(Long parcelId, CustomerService customerService,
                                       ShipmentService shipmentService, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(GET_PARCEL_BY_ID_QUERY);
        stmt.setLong(1, parcelId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Shipment shipment = shipmentService.getShipmentById(rs.getLong("shipment_id"), customerService, conn);
            Parcel parcel = new Parcel(rs.getLong("parcel_id"), rs.getFloat("weight"));
            parcel.setShipment(shipment);
            return parcel;
        }
        return null;
    }

    /**
     * Saves a parcel to the parcel table or updates an existing one
     *
     * @param parcel the parcel to save
     * @param conn the connection to the database
     * @return the id of the parcel saved or null
     * @throws SQLException
     */
    public Long save(Parcel parcel, Long shipmentId, Connection conn) throws SQLException {
        long startTime = System.nanoTime();
        long executionTime = 0;
        long endTime;
        if (parcelExists(parcel.getId(), conn)) {
            update(parcel, shipmentId, conn);
            endTime = System.nanoTime();
            executionTime = endTime - startTime;
            System.out.println(UPDATE_PARCEL_QUERY + " || Execution time: " + executionTime + " ns");

        } else {
            return insert(parcel, shipmentId, conn);
        }
        return null;
    }

    /**
     * Updates the parcel in the parcel table
     *
     * @param parcel the parcel to update
     * @param conn the connection to the database
     * @throws SQLException
     */
    private void update(Parcel parcel, Long shipmentId, Connection conn) throws SQLException {
        try (PreparedStatement stmt = createUpdateStatement(parcel, shipmentId, conn)) {
            stmt.executeUpdate();
        }
    }

    /**
     * Inserts the parcel in the parcel table
     *
     * @param parcel the parcel to insert
     * @param conn the connection to the database
     * @return the id of the inserted parcel
     * @throws SQLException
     */
    private Long insert(Parcel parcel, Long shipmentId, Connection conn) throws SQLException {
        long startTime = System.nanoTime();
        long executionTime = 0;
        long endTime;
        Long id = null;
        try (PreparedStatement stmt = createInsertStatement(parcel, shipmentId, conn)) {
            int rowsAffected = stmt.executeUpdate();
            endTime = System.nanoTime();
            executionTime = endTime - startTime;
            if (rowsAffected > 0) {
                // Run a separate query to get the last inserted ID
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs = stmt2.executeQuery("SELECT DBINFO('sqlca.sqlerrd1') FROM parcel")) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        }
        return id;
    }


    /**
     * Checks if the parcel exists in the parcel table
     *
     * @param id the id of the parcel
     * @param conn the connection to the database
     * @return
     * @throws SQLException
     */
    private boolean parcelExists(Long id, Connection conn) throws SQLException {
        if (id == null || id == 0) {
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(GET_PARCEL_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private PreparedStatement createInsertStatement(Parcel parcel, Long shipmentId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_PARCEL_QUERY, Statement.RETURN_GENERATED_KEYS);
        stmt.setLong(1, shipmentId);
        stmt.setFloat(2, parcel.getWeight());
        stmt.setInt(3, parcel.getWeight_class());
        return stmt;
    }

    private PreparedStatement createUpdateStatement(Parcel parcel, Long shipmentId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(UPDATE_PARCEL_QUERY);
        stmt.setLong(1, shipmentId);
        stmt.setFloat(2, parcel.getWeight());
        stmt.setInt(3, parcel.getWeight_class());
        stmt.setLong(4, parcel.getId());
        return stmt;
    }


    /**
     * Deletes a parcel from the parcel table
     *
     * @param parcel the parcel to delete
     * @param conn the connection to the database
     * @throws SQLException
     */
    public void delete(Parcel parcel, Connection conn) throws SQLException {
        Long id = parcel.getId();
        if (id > 0) {
            PreparedStatement stmt = conn.prepareStatement(DELETE_PARCEL_QUERY);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }
}
