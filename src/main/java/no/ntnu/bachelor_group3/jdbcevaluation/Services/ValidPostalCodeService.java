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
            // This is a new customer, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO valid_postal_codes (postal_code, terminal_id, county, municipality) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, postalCode.getPostalCode());
            stmt.setLong(2, postalCode.getTerminal().getId());
            stmt.setString(3, postalCode.getCounty());
            stmt.setString(4, postalCode.getMunicipality());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } else {
            // This is an existing customer, so update it in the database
            stmt = conn.prepareStatement("UPDATE valid_postal_codes SET postal_code = ?, terminal_id = ?, county = ?, municipality = ? WHERE terminal_id = ?");
            stmt.setLong(5, id);
            stmt.setString(1, postalCode.getPostalCode());
            stmt.setLong(2, postalCode.getTerminal().getId());
            stmt.setString(3, postalCode.getCounty());
            stmt.setString(4, postalCode.getMunicipality());
            stmt.executeUpdate();
        }
    }
}
