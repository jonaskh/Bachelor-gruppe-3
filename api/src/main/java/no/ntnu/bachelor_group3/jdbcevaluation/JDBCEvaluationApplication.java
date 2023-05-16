package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.generators.DataPopulator;

public class JDBCEvaluationApplication {


    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {
            DataPopulator dataPopulator = new DataPopulator(db);
            // Comment out the two lines below if already populated
            db.deleteRowsFromDB();
            dataPopulator.generateTerminals();
            dataPopulator.generateValidPostalCodes();

            SimulationRunner simulationRunner = new SimulationRunner(db);
            simulationRunner.simulate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
