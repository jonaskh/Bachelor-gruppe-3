package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class ParcelDataGenerator {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            // Create a new Faker instance
            Faker faker = new Faker();

            // Get a list of all the customers in the database
            List<Shipment> shipments = db.getAllShipments();

            // Generate 10 random shipments
            for (int i = 0; i <= 100; i++) {
                // Choose random sender, receiver, and payer from the list of customers
                int shipIndex = faker.random().nextInt(shipments.size());

                Shipment shipment = shipments.get(shipIndex);
                double weight = faker.number().randomDouble(2, 1, 50);


                // Create a new Shipment object with the generated data
                Parcel parcel = new Parcel(0L, weight);
                parcel.setShipment(shipment);
                // Save the shipment to the database
                db.saveParcel(parcel);



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
