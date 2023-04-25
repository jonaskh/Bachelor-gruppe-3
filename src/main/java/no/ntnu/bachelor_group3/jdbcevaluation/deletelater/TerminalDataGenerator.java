package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;
import java.util.Locale;

public class TerminalDataGenerator {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            db.execute("DELETE FROM terminal");
            Faker faker = new Faker(new Locale("nb-NO"));
            for (int i = 1; i <= 20; i++) {
                String location = faker.address().streetAddress();

                Terminal terminal = new Terminal(0L, location);

                db.saveTerminal(terminal);
            }

            db.commit();

            System.out.println("Terminals generated successfully!");
        } catch (SQLException e) {
            System.err.println("Error generating terminals: " + e.getMessage());
        }
    }
}
