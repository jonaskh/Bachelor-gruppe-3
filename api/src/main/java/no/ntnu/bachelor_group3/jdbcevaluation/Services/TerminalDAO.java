package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TerminalDAO {

    public TerminalDAO() {}

    public Terminal getTerminalById(int terminalId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM terminal WHERE terminal_id = ?");
        stmt.setInt(1, terminalId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Terminal terminal = new Terminal(rs.getLong("terminal_id"), rs.getString("address"));
            return terminal;
        }
        return null;
    }

    /**
     * Returns the terminal responsible for the given zip code.
     *
     * @param zipCode The zip code to use for finding the terminal
     * @param conn A connection to a database
     * @return The terminal found responsible for the zip, or null
     */
    public Terminal getTerminalByZip(String zipCode, Connection conn) {
        Terminal terminal = null;
        String query = "SELECT t.terminal_id, t.address " +
                "FROM terminal t " +
                "JOIN valid_postal_codes pc ON t.terminal_id = pc.terminal_id " +
                "WHERE pc.postal_code = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, zipCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("terminal_id");
                String address = rs.getString("address");
                terminal = new Terminal(id, address);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching terminal by zip code: " + e.getMessage());
        }

        return terminal;
    }

    /**
     * Saves a terminal to a database through the Connection conn
     * CREATE or UPDATE depending on if the terminal already exists.
     *
     * @param terminal The terminal to save
     * @param conn the connection to the database
     * @throws SQLException
     */
    public void save(Terminal terminal, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = terminal.getId();
        if (id == 0) {
            // This is a new terminal, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO terminal (address) VALUES (?)");
            stmt.setString(1, terminal.getLocation());
            int rowsInserted = stmt.executeUpdate();

        } else {
            // This is an existing terminal, so update it in the database
            stmt = conn.prepareStatement("UPDATE terminal SET address = ? WHERE terminal_id = ?");
            stmt.setString(1, terminal.getLocation());
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
    }


    public void delete(Terminal terminal, Connection conn) throws SQLException {
        Long id = terminal.getId();
        if (id > 0) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM terminal WHERE terminal_id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }
}
