package no.ntnu.bachelor_group3.jdbcevaluation.generators;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.io.File;
import java.sql.SQLException;
import java.util.Locale;

public class DataPopulator {
    private DatabaseManager db;
    private Faker faker;

    public DataPopulator(DatabaseManager db) {
        this.db = db;
        faker = new Faker(new Locale("nb-NO"));
    }

    public void generateTerminals() throws SQLException {
        for (int i = 1; i <= 19; i++) {
            String location = faker.address().streetAddress();

            Terminal terminal = new Terminal(0L, location);

            db.saveTerminal(terminal);
        }

        db.commit();

        System.out.println("Terminals generated successfully!");
    }

    public void generateValidPostalCodes() {
        String csvFilePath = new File("Postnummerregister.csv").getAbsolutePath();
        PostalCodeCSVReader csvReader = new PostalCodeCSVReader(csvFilePath);
        csvReader.readAndAddPostalCodesToDatabase(db);
    }


}
