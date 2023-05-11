package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CheckpointService {

    private static final String GET_CHECKPOINT_BY_ID_QUERY = "SELECT * FROM checkpoint WHERE checkpoint_id = ?";
    private static final String INSERT_CHECKPOINT_QUERY = "INSERT INTO checkpoint (cost, location, time, parcel_id, terminal_id, type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CHECKPOINT_QUERY = "UPDATE checkpoint SET cost = ?, location = ?, time = ?, parcel_id = ?, terminal_id = ?, type = ? WHERE checkpoint_id = ?";
    private static final String DELETE_CHECKPOINT_QUERY = "DELETE FROM checkpoint WHERE checkpoint_id = ?";

    private List<long> executionTimeList;

    public CheckpointService() {}

    public Checkpoint getCheckpointById(int checkpointId, ParcelService parcelService, ShipmentService shipmentService,
                                        CustomerService customerService, TerminalService terminalService, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(GET_CHECKPOINT_BY_ID_QUERY);
        stmt.setInt(1, checkpointId);
        ResultSet rs = stmt.executeQuery();
        Checkpoint.CheckpointType type = Checkpoint.CheckpointType.valueOf(rs.getString("type"));
        Parcel parcel = parcelService.getParcelById(rs.getLong("parcel_id"),
                customerService, shipmentService, conn);
        Terminal terminal = terminalService.getTerminalById(rs.getInt("terminal_id"), conn);
        if (rs.next()) {
            Checkpoint checkpoint = new Checkpoint(rs.getLong("checkpoint_id"), rs.getDouble("cost"),
                    rs.getString("location"), parcel, terminal, type);
            return checkpoint;
        }
        return null;
    }

    public Long save(Checkpoint checkpoint, Connection conn) throws SQLException {
        long startTime = System.nanoTime();
        long executionTime = 0;
        long endTime;
        PreparedStatement stmt;
        Long id = 0L;
        if (id == 0) {
            // This is a new checkpoint, so insert it into the database
            stmt = conn.prepareStatement(INSERT_CHECKPOINT_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, checkpoint.getCost());
            stmt.setString(2, checkpoint.getLocation());
            stmt.setTimestamp(3, new Timestamp(checkpoint.getTime().getTime()));
            stmt.setLong(4, checkpoint.getParcel().getId());
            stmt.setLong(5, checkpoint.getTerminal().getId());
            stmt.setInt(6, checkpoint.getType().ordinal());
            int rowsInserted = stmt.executeUpdate();
            endTime = System.nanoTime();
            executionTime = endTime - startTime;
            executionTimeList.add(executionTime);
            //System.out.println(INSERT_CHECKPOINT_QUERY + " || Execution time: " + executionTime + " ns");
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(4);
                }
            }
        } else {
            // This is an existing checkpoint, so update it in the database
            stmt = conn.prepareStatement(UPDATE_CHECKPOINT_QUERY);
            stmt.setDouble(1, checkpoint.getCost());
            stmt.setString(2, checkpoint.getLocation());
            stmt.setTimestamp(3, new Timestamp(checkpoint.getTime().getTime()));
            stmt.setLong(4, checkpoint.getParcel().getId());
            stmt.setLong(5, checkpoint.getTerminal().getId());
            stmt.setInt(6, checkpoint.getType().ordinal());
            stmt.executeUpdate();
            endTime = System.nanoTime();
            executionTime = endTime - startTime;
            System.out.println(UPDATE_CHECKPOINT_QUERY + " || Execution time: " + executionTime + " ns");
        }
        return id;
    }

    public void delete(Checkpoint checkpoint, Connection conn) throws SQLException {
        Long id = checkpoint.getId();
        if (id > 0) {
            PreparedStatement stmt = conn.prepareStatement(DELETE_CHECKPOINT_QUERY);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }

    public List<long> getExecutionTimeList() {
        return executionTimeList;
    }
}
