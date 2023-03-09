package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;
import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Just used for testing purposes
 */
public class CustomerDataGenerator {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            // Create a new Faker instance
            db.execute("DELETE FROM customer");
            Faker faker = new Faker(new Locale("nb-NO"));
            // Generate 10 random customers
            for (int i = 1; i <= 100; i++) {
                // Generate random name, address, zip code, and email
                String name = faker.name().fullName();
                String address = faker.address().streetAddress();
                String zipCode = faker.address().zipCode();

                // Create a new Customer object
                Customer customer = new Customer(0L, name, address, zipCode);

                // Save the customer to the database
                db.saveCustomer(customer);
            }

            // Commit the transaction to save the data to the database
            db.commit();

            System.out.println("Customers generated successfully!");
        } catch (SQLException e) {
            System.err.println("Error generating customers: " + e.getMessage());
        }
    }
}