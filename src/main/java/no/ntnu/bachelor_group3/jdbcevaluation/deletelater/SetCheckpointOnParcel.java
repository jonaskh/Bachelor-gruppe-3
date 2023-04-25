package no.ntnu.bachelor_group3.jdbcevaluation.deletelater;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

import java.sql.SQLException;
import java.util.Date;

public class SetCheckpointOnParcel {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManager()) {

            Parcel parcel = db.getParcelById(1L);
            Terminal terminal = db.getTerminalById(1);

            Checkpoint checkpoint = new Checkpoint(0L, 10, "Bøgata 6008", new Date(), parcel, terminal);

            db.setCheckpointOnParcel(parcel, checkpoint);
            db.commit();

            System.out.println("Successfully set a checkpoint on a package!!!");
        } catch (SQLException e) {
            System.err.println("Error setting checkpoint" + e.getMessage());
            e.printStackTrace();
        }
    }
}
