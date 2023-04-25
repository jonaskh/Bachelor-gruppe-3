package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.ValidPostalCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidPostalCodeService {

    public ValidPostalCodeService() {}

    public ValidPostalCode getPostalCodeById(Long postalCodeId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM valid_postal_codes WHERE postal_code_id = ?");
        stmt.setLong(1, postalCodeId);
        ResultSet rs = stmt.executeQuery();

        return null;
    }


}
