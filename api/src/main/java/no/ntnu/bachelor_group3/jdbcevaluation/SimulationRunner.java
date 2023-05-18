package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.FindShipmentRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.UpdateShipmentRunnable;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SimulationRunner {
    private static List<String> evals = new CopyOnWriteArrayList<>();
    private DatabaseManager db;

    private final int SHIPMENTS = 1000;
    private final int threads = 5;

    private final int findShipment = 100;
    private final int findShipmentDelay = 1000;
    private final int findShipmentPeriod = 2000;

    private final int countShipment = 10;
    private final int countDelay = 1000;
    private final int countPeriod = 5000;

    private final Random random = new Random();



    private ExecutorService executor1 = Executors.newFixedThreadPool(threads);
    private ExecutorService executor2 = Executors.newFixedThreadPool(threads);
    private ScheduledExecutorService findLocationOfShipmentExecutor = Executors.newScheduledThreadPool(threads);
    private ScheduledExecutorService shipmentCountExecutor = Executors.newScheduledThreadPool(threads);

    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(SHIPMENTS);


    public SimulationRunner(DatabaseManager db) {
        this.db = db;
    }

    public void simulate() throws SQLException {
            Customer sender = new Customer(0l, "Ålesund", "Jonas", "6008");
            Customer receiver = new Customer(0l, "Oslo", "Tarjei", "0021");
            db.saveCustomer(sender);
            db.saveCustomer(receiver);

            System.out.println("Starting simulation...");
            for (int i = 0; i < SHIPMENTS; i++) {
                Shipment shipment = new Shipment(0L, sender, sender, receiver, 1.2);
                AddShipmentsRunnable shipmentsRunnable = new AddShipmentsRunnable(shipment, db);
                try {
                    queue.put(shipment);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                executor1.execute(shipmentsRunnable);
            }

        int i = 0;
        var size = queue.size();



        for (int k = 0; k < findShipment; k++) {
            findLocationOfShipmentExecutor.scheduleAtFixedRate(new FindShipmentRunnable(db, db.getShipmentById(random.nextLong(size)+1)), findShipmentDelay, findShipmentPeriod, TimeUnit.MILLISECONDS);
        }

        for (int j = 0; j < countShipment; j++) {
            shipmentCountExecutor.scheduleAtFixedRate(() -> {
                try {
                    db.getShipmentCount();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }, countDelay, countPeriod, TimeUnit.MILLISECONDS);
        }

            while (i < size) {
                try {
                    executor2.execute(new UpdateShipmentRunnable(queue.take(), db));
                    i++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        try {
                //stops the executors from accepting new tasks


                shipmentCountExecutor.shutdown();
                findLocationOfShipmentExecutor.shutdown();
            executor1.shutdown();
            executor2.shutdown();


                executor1.awaitTermination(2, TimeUnit.MINUTES);
                System.out.println("Adding done");

                //stops the thread pools if no more tasks, an exception occurs or timeout.
                executor2.awaitTermination(2, TimeUnit.MINUTES);
                System.out.println("Update shipments done");

                findLocationOfShipmentExecutor.awaitTermination(2, TimeUnit.MINUTES);
                shipmentCountExecutor.awaitTermination(2, TimeUnit.MINUTES);



            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error in await termination");
            }

            db.commit();


        }

}








