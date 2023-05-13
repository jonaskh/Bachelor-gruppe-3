package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.ValidPostalCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ValidPostalCodeService {

    public ValidPostalCodeService() {}

    public ValidPostalCode getPostalCodeById(Long postalCodeId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM valid_postal_codes WHERE postal_code_id = ?");
        stmt.setLong(1, postalCodeId);
        ResultSet rs = stmt.executeQuery();

        return null;
    }


    public void save(ValidPostalCode postalCode, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = postalCode.getId();
        if (id == 0) {
            // This is a new postal code, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO valid_postal_codes (postal_code, terminal_id, county, municipality) VALUES (?, ?, ?, ?)");
            stmt.setString(1, postalCode.getPostalCode());
            stmt.setLong(2, postalCode.getTerminal().getId());
            stmt.setString(3, postalCode.getCounty());
            stmt.setString(4, postalCode.getMunicipality());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Run a separate query to get the last inserted ID
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs = stmt2.executeQuery("SELECT DBINFO('sqlca.sqlerrd1') FROM valid_postal_codes")) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        } else {
            // This is an existing postal code, so update it in the database
            stmt = conn.prepareStatement("UPDATE valid_postal_codes SET postal_code = ?, terminal_id = ?, county = ?, municipality = ? WHERE terminal_id = ?");
            stmt.setString(1, postalCode.getPostalCode());
            stmt.setLong(2, postalCode.getTerminal().getId());
            stmt.setString(3, postalCode.getCounty());
            stmt.setString(4, postalCode.getMunicipality());
            stmt.setLong(5, id);
            stmt.executeUpdate();
        }
    }

}
