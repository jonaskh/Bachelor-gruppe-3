package no.ntnu.bachelor_group3.jdbcevaluation.generators;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.ValidPostalCode;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class PostalCodeCSVReader {
    private final String csvFilePath;

    public PostalCodeCSVReader(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public void readAndAddPostalCodesToDatabase(DatabaseManager db) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                String zipCode = fields[0].trim().replaceAll("^\"|\"$", "");
                int terminalId = Integer.parseInt(fields[2].trim().replaceAll("^\"|\"$", ""));
                Terminal terminal = db.getTerminalById(terminalId);
                String county = fields[1].trim().replaceAll("^\"|\"$", "");
                String municipality = fields[3].trim().replaceAll("^\"|\"$", "");

                ValidPostalCode postalCode = new ValidPostalCode(0L, zipCode, terminal, county, municipality);
                db.savePostalCode(postalCode);
            }
            db.commit();
        } catch (IOException | SQLException e) {
            try {
                db.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
