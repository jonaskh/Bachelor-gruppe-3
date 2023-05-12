package no.ntnu.bachelor_group3.jdbcevaluation.Runnables;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;

public class TerminalShipmentsRunnable implements Runnable {

    private DatabaseManager db;



    public TerminalShipmentsRunnable(DatabaseManager db) {

        this.db = db;

    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void catchRun() {
        System.out.println(db.getTerminalByZip("6300"));
    }
}
