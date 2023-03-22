package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class CheckpointService {

    private static final String GET_CHECKPOINT_BY_ID_QUERY = "SELECT * FROM checkpoint WHERE checkpoint_id = ?";
    private static final String INSERT_CHECKPOINT_QUERY = "INSERT INTO checkpoint (cost, location, time) VALUES (?, ?, ?)";
    private static final String UPDATE_CHECKPOINT_QUERY = "UPDATE checkpoint SET cost = ?, location = ?, time = ? WHERE checkpoint_id = ?";
    private static final String DELETE_CHECKPOINT_QUERY = "DELETE FROM checkpoint WHERE checkpoint_id = ?";

    public CheckpointService() {}

    public Checkpoint getCheckpointById(int checkpointId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(GET_CHECKPOINT_BY_ID_QUERY);
        stmt.setInt(1, checkpointId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Checkpoint checkpoint = new Checkpoint(rs.getLong("checkpoint_id"), rs.getDouble("cost"),
                    rs.getString("location"), rs.getTimestamp("time"));
            return checkpoint;
        }
        return null;
    }

    public void save(Checkpoint checkpoint, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = checkpoint.getId();
        if (id == 0) {
            // This is a new checkpoint, so insert it into the database
            stmt = conn.prepareStatement(INSERT_CHECKPOINT_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, checkpoint.getCost());
            stmt.setString(2, checkpoint.getLocation());
            stmt.setTimestamp(3, new Timestamp(checkpoint.getTime().getTime()));
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } else {
            // This is an existing checkpoint, so update it in the database
            stmt = conn.prepareStatement(UPDATE_CHECKPOINT_QUERY);
            stmt.setDouble(1, checkpoint.getCost());
            stmt.setString(2, checkpoint.getLocation());
            stmt.setTimestamp(3, new Timestamp(checkpoint.getTime().getTime()));
            stmt.setLong(4, id);
            stmt.executeUpdate();
        }
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
}
