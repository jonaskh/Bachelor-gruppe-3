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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CheckpointService {

    private static final String GET_CHECKPOINT_BY_ID_QUERY = "SELECT * FROM checkpoint WHERE checkpoint_id = ?";
    private static final String INSERT_CHECKPOINT_QUERY = "INSERT INTO checkpoint (cost, location, time, parcel_id, terminal_id, type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CHECKPOINT_QUERY = "UPDATE checkpoint SET cost = ?, location = ?, time = ?, parcel_id = ?, terminal_id = ?, type = ? WHERE checkpoint_id = ?";
    private static final String DELETE_CHECKPOINT_QUERY = "DELETE FROM checkpoint WHERE checkpoint_id = ?";
    private static final String GET_CHECKPOINTS_FROM_PARCEL = "SELECT * FROM checkpoint WHERE parcel_id = ?";

    public static List<String> executionTimeList;

    public CheckpointService() {
        if (executionTimeList == null) {
            executionTimeList = new ArrayList<>();
        }
    }

    public Checkpoint getCheckpointById(int checkpointId, ParcelService parcelService, ShipmentService shipmentService,
                                        CustomerService customerService, TerminalService terminalService, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(GET_CHECKPOINT_BY_ID_QUERY);
        stmt.setInt(1, checkpointId);
        ResultSet rs = stmt.executeQuery();
        int typeInt = rs.getInt("type");
        Checkpoint.CheckpointType type = Checkpoint.CheckpointType.fromInteger(typeInt);
        Parcel parcel = parcelService.getParcelById(rs.getLong("parcel_id"),
                customerService, shipmentService, conn);
        Terminal terminal = terminalService.getTerminalById(rs.getInt("terminal_id"), conn);
        if (rs.next()) {
            Checkpoint checkpoint = new Checkpoint(rs.getLong("checkpoint_id"), rs.getFloat("cost"),
                    rs.getString("location"), parcel, terminal, type);
            return checkpoint;
        }
        return null;
    }

    public List<Checkpoint> getCheckpointsByParcelId(Long parcelId, ParcelService parcelService, ShipmentService shipmentService,
                                                     CustomerService customerService, TerminalService terminalService, Connection conn) {
        List<Checkpoint> checkpoints = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_CHECKPOINTS_FROM_PARCEL)) {
            stmt.setLong(1, parcelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int typeInt = rs.getInt("type");
                Checkpoint.CheckpointType type = Checkpoint.CheckpointType.fromInteger(typeInt);
                Parcel parcel = parcelService.getParcelById(rs.getLong("parcel_id"),
                        customerService, shipmentService, conn);
                Terminal terminal = terminalService.getTerminalById(rs.getInt("terminal_id"), conn);

                Checkpoint checkpoint = new Checkpoint(rs.getLong("checkpoint_id"), rs.getFloat("cost"),
                        rs.getString("location"), parcel, terminal, type);

                checkpoints.add(checkpoint);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkpoints;
    }

    public Long save(Checkpoint checkpoint, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = checkpoint.getId();
        if (id == null || id == 0L) {
            // This is a new checkpoint, so insert it into the database
            stmt = conn.prepareStatement(INSERT_CHECKPOINT_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmt.setFloat(1, checkpoint.getCost());
            stmt.setString(2, checkpoint.getLocation());
            stmt.setTimestamp(3, new Timestamp(checkpoint.getTime().getTime()));
            stmt.setLong(4, checkpoint.getParcel().getId());
            if (checkpoint.getTerminal() != null) {
                stmt.setLong(5, checkpoint.getTerminal().getId());
            }
            stmt.setInt(6, checkpoint.getType().ordinal());
            var startTime = Instant.now();
            int rowsInserted = stmt.executeUpdate();
            var executionTime = Duration.between(startTime, Instant.now()).toNanos();
            executionTimeList.add(executionTime + ", checkpoint, create");
            if (rowsInserted > 0) {
                // Run a separate query to get the last inserted ID
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        } else {
            // This is an existing checkpoint, so update it in the database
            stmt = conn.prepareStatement(UPDATE_CHECKPOINT_QUERY);
            stmt.setFloat(1, checkpoint.getCost());
            stmt.setString(2, checkpoint.getLocation());
            stmt.setTimestamp(3, new Timestamp(checkpoint.getTime().getTime()));
            stmt.setLong(4, checkpoint.getParcel().getId());
            if (checkpoint.getTerminal() != null) {
                stmt.setLong(5, checkpoint.getTerminal().getId());
            }
            stmt.setInt(6, checkpoint.getType().ordinal());
            var startTime = Instant.now();
            stmt.executeUpdate();
            var executionTime = Duration.between(startTime, Instant.now()).toNanos();
            executionTimeList.add(executionTime + ", checkpoint, update");
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

    public List<String> getExecutionTimeList() {
        return executionTimeList;
    }
}
