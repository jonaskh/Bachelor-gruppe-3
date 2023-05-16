package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;
import no.ntnu.bachelor_group3.jdbcevaluation.generators.CustomerCSVReader;

import java.io.File;
import java.sql.SQLException;

/**
 * Just used for testing purposes
 */
public class CustomerDataGenerator {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            String csvFilePath = new File("customers.csv").getAbsolutePath();
            CustomerCSVReader csvReader = new CustomerCSVReader(csvFilePath);
            csvReader.readAndAddCustomersToDatabase(db);
        } catch (SQLException e) {
            System.err.println("Error generating customers: " + e.getMessage());
        }
    }
}
