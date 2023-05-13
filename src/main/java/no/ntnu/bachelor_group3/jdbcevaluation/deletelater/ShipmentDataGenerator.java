/*
package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class ShipmentDataGenerator {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            // Create a new Faker instance
            Faker faker = new Faker();

            // Get a list of all the customers in the database
            List<Customer> customers = db.getAllCustomers();

            // Generate 10 random shipments
            for (int i = 0; i <= 9; i++) {
                // Choose random sender, receiver, and payer from the list of customers
                int senderIndex = faker.random().nextInt(customers.size());
                int receiverIndex = faker.random().nextInt(customers.size());
                int payerIndex = faker.random().nextInt(customers.size());

                Customer sender = customers.get(senderIndex);
                Customer receiver = customers.get(receiverIndex);
                Customer payer = customers.get(payerIndex);

                // Generate a random total cost for the shipment
                float totalCost = faker.number().random(2, 10, 500);


                // Create a new Shipment object with the generated data
                Shipment shipment = new Shipment(0L, sender, receiver, payer, totalCost);
                // Save the shipment to the database
                db.saveShipment(shipment);
            }


            // Commit the transaction to save the data to the database
            db.commit();

            System.out.println("Shipments generated successfully!");
        } catch (SQLException e) {
            System.err.println("Error generating shipments: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


 */