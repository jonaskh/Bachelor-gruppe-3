package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TerminalService {

    public TerminalService() {}

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

    public void save(Terminal terminal, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = terminal.getId();
        if (id == 0) {
            // This is a new customer, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO terminal (address) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, terminal.getLocation());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } else {
            // This is an existing customer, so update it in the database
            stmt = conn.prepareStatement("UPDATE terminal SET address = ? WHERE terminal_id = ?");
            stmt.setString(2, terminal.getLocation());
            stmt.setLong(1, id);
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
