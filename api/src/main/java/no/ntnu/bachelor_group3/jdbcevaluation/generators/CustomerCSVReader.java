package no.ntnu.bachelor_group3.jdbcevaluation.generators;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CustomerCSVReader {

    private final String csvFilePath;

    public CustomerCSVReader(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public void readAndAddCustomersToDatabase(DatabaseManager db) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                String name = fields[0].trim().replaceAll("^\"|\"$", "");
                String address = fields[1].trim().replaceAll("^\"|\"$", "");
                String zipCode = fields[4].trim().replaceAll("^\"|\"$", "");

                Customer customer = new Customer(0L, name, address, zipCode);
                db.saveCustomer(customer);
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