package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.FindShipmentRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.TerminalShipmentsRunnable;
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


    private ExecutorService addShipmentsExecutor = Executors.newFixedThreadPool(threads);
    private ExecutorService updateShipmentExecutor = Executors.newFixedThreadPool(threads);
    private ScheduledExecutorService findLocationOfShipmentExecutor = Executors.newScheduledThreadPool(threads);
    private ScheduledExecutorService shipmentCountExecutor = Executors.newScheduledThreadPool(threads);


    private final Random random = new Random();

    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(SHIPMENTS);

    public SimulationRunner(DatabaseManager db) {
        this.db = db;
    }

    public void simulate() throws SQLException {
            Customer sender = new Customer(1l, "Ålesund", "Jonas", "6008");
            Customer receiver = new Customer(2l, "Oslo", "Tarjei", "0021");
            Long senderId = db.saveCustomer(sender);
            Long payerId = db.saveCustomer(receiver);

        System.out.println("Starting simulation...");
            for (int i = 0; i < SHIPMENTS; i++) {
                Shipment shipment = new Shipment(0L, sender, sender, receiver, 1.2f);
                AddShipmentsRunnable shipmentsRunnable = new AddShipmentsRunnable(shipment, db);
                try {
                    queue.put(shipment);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                addShipmentsExecutor.execute(shipmentsRunnable);
            }

        int i = 0;
        var size = queue.size();



            for (int k = 0; k < findShipment; k++) {
                findLocationOfShipmentExecutor.scheduleAtFixedRate(new FindShipmentRunnable(db, db.getShipmentById(random.nextLong(size-1)+1)), findShipmentDelay, findShipmentPeriod, TimeUnit.MILLISECONDS);
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
                updateShipmentExecutor.execute(new UpdateShipmentRunnable(queue.take(), db));
                i++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        try {
                //stops the executors from accepting new tasks


                shipmentCountExecutor.shutdown();
                findLocationOfShipmentExecutor.shutdown();
            addShipmentsExecutor.shutdown();
            updateShipmentExecutor.shutdown();


                addShipmentsExecutor.awaitTermination(2, TimeUnit.MINUTES);
                System.out.println("Adding done");

                //stops the thread pools if no more tasks, an exception occurs or timeout.
                updateShipmentExecutor.awaitTermination(2, TimeUnit.MINUTES);
                System.out.println("Update shipments done");

                findLocationOfShipmentExecutor.awaitTermination(2, TimeUnit.MINUTES);
                shipmentCountExecutor.awaitTermination(2, TimeUnit.MINUTES);



            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error in await termination");
            }

            db.commit();


            //evals.addAll(customerService.getCustomerEval());
            //evals.forEach(System.out::println);

            //System.out.println("shipments: " + sender.getShipments().size());



            //System.out.println("queue size: " + queue.size());
            //System.out.println("shipments: " + shipmentService.count());
            //System.out.println("parcels: " + parcelService.count());
            //System.out.println("checkpoints: " + checkpointService.count());

            //System.out.println("Number of shipments in terminal 14: " + terminalService.returnTerminalFromZip("6008").getShipmentNumber());
            //System.out.println("Number of checkpoints in terminal 14: " + terminalService.returnTerminalFromZip("0021").getCheckpointNumber());


        }
}





