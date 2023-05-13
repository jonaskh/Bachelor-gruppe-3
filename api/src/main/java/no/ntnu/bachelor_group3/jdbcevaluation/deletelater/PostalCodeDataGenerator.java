package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;
import no.ntnu.bachelor_group3.jdbcevaluation.generators.PostalCodeCSVReader;

import java.io.File;
import java.sql.SQLException;

/**
 * Just used for testing purposes
 */
public class PostalCodeDataGenerator {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            String csvFilePath = new File("Postnummerregister.csv").getAbsolutePath();
            PostalCodeCSVReader csvReader = new PostalCodeCSVReader(csvFilePath);
            csvReader.readAndAddPostalCodesToDatabase(db);
        } catch (SQLException e) {
            System.err.println("Error generating customers: " + e.getMessage());
        }
    }
}