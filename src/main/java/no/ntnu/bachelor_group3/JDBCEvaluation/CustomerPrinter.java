package no.ntnu.bachelor_group3.JDBCEvaluation;

import no.ntnu.bachelor_group3.JDBCEvaluation.data.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * Just used for testing purposes
 */
public class CustomerPrinter {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {

            // Retrieve all customers from the database
            List<Customer> customers = db.getAllCustomers();

            // Print the customers to the terminal
            System.out.println("Customers:");
            for (Customer customer : customers) {
                System.out.println(customer.getName() + "   " + customer.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error querying database: " + e.getMessage());
        }
    }

}